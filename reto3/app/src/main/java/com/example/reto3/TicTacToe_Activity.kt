package com.example.reto3

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe_Activity :Activity(){

    private var mGame = TicTacToe()
    // Buttons making up the board
    private lateinit var mBoardButtons: Array<Button>

    // Various text displayed
    private lateinit var mInfoTextView: TextView

    @Override
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_tic_tac_toe)

        val mBoardButtons = arrayOfNulls<Button>(9)
        mBoardButtons[0] = findViewById<Button>(R.id.one)
        mBoardButtons[1] = findViewById<Button>(R.id.two)
        mBoardButtons[2] = findViewById<Button>(R.id.three)
        mBoardButtons[3] = findViewById<Button>(R.id.four)
        mBoardButtons[4] = findViewById<Button>(R.id.five)
        mBoardButtons[5] = findViewById<Button>(R.id.six)
        mBoardButtons[6] = findViewById<Button>(R.id.seven)
        mBoardButtons[7] = findViewById<Button>(R.id.eigth)
        mBoardButtons[8] = findViewById<Button>(R.id.nine)
        mInfoTextView = findViewById(R.id.information) as TextView
        var mGame = TicTacToe()
        startNewGame()
    }

    private fun startNewGame() {
        mGame.clearBoard()
        // Reset all buttons
        for (i in 0 until mBoardButtons.size) {
            mBoardButtons[i].text = ""
            mBoardButtons[i].isEnabled = true
            mBoardButtons[i].setOnClickListener(ButtonClickListener(i))

        }
        // Human goes first
        mInfoTextView.setText("You go first.");
    }

    inner private class ButtonClickListener(var location: Int) : View.OnClickListener {
        override fun onClick(view: View) {
            if (mBoardButtons.get(location).isEnabled()) {
                setMove(TicTacToe.HUMAN_PLAYER, location)
                // If no winner yet, let the computer make a move
                var winner: Int = mGame.checkForWinner(mGame.mBoard)
                if (winner == 0) {
                    mInfoTextView.setText("It's Android's turn.")
                    val move: Int = mGame.getComputerMove()
                    setMove(TicTacToe.COMPUTER_PLAYER, move)
                    winner = mGame.checkForWinner(mGame.mBoard)
                }
                if (winner == 0) mInfoTextView.setText("It's your turn.") else if (winner == 1) mInfoTextView.setText(
                    "It's a tie!"
                ) else if (winner == 2) mInfoTextView.setText("You won!") else mInfoTextView.setText(
                    "Android won!"
                )
            }
        }
    }
    private fun setMove(player: Char, location: Int) {
        mGame.setMove(player, location)
        mBoardButtons[location].isEnabled = false
        mBoardButtons[location].text = player.toString()
        if (player == TicTacToe.HUMAN_PLAYER) mBoardButtons[location].setTextColor(
            Color.rgb(
                0,
                200,
                0
            )
        ) else mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0))
    }
}

