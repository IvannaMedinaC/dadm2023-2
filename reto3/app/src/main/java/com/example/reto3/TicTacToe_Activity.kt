package com.example.reto3

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


public class TicTacToe_Activity :Activity(){

    private lateinit var mGame: TicTacToe
    // Buttons making up the board
    private lateinit var mBoardButtons: ArrayList<Button>

    // Various text displayed
    private lateinit var mInfoTextView: TextView
    private lateinit var newButton: Button
    @Override
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_tic_tac_toe)
        mGame = TicTacToe()
        newButton = findViewById(R.id.button)
        mBoardButtons = arrayListOf<Button>()
        mBoardButtons.add(findViewById<Button>(R.id.one))
        mBoardButtons.add(findViewById<Button>(R.id.two))
        mBoardButtons.add(findViewById<Button>(R.id.three))
        mBoardButtons.add(findViewById<Button>(R.id.four))
        mBoardButtons.add(findViewById<Button>(R.id.five))
        mBoardButtons.add(findViewById<Button>(R.id.six))
        mBoardButtons.add(findViewById<Button>(R.id.seven))
        mBoardButtons.add(findViewById<Button>(R.id.eigth))
        mBoardButtons.add(findViewById<Button>(R.id.nine))
        mInfoTextView = findViewById(R.id.information) as TextView

        startNewGame()
    }

    private fun startNewGame() {
        mGame.clearBoard()
        // Reset all buttons
        for (i in 0 until mBoardButtons.size) {
            mBoardButtons[i]?.text = ""
            mBoardButtons[i]?.isEnabled = true
            mBoardButtons[i]?.setOnClickListener(ButtonClickListener(i))

        }
        // Human goes first
        mInfoTextView.setText("You go first.");
        newButton.setOnClickListener { mGame.clearBoard()
            for (i in 0 until mBoardButtons.size) {
            mBoardButtons[i]?.text = ""
            mBoardButtons[i]?.isEnabled = true
            mBoardButtons[i]?.setOnClickListener(ButtonClickListener(i))

        }}
    }

    inner private class ButtonClickListener(var location: Int) : View.OnClickListener {
        override fun onClick(view: View) {
            if (mBoardButtons!!.get(location)!!.isEnabled()) {
                setMove(TicTacToe.HUMAN_PLAYER, location)
                // If no winner yet, let the computer make a move
                var winner: Int = 0
                winner = mGame.checkForWinner()
                if (winner == 0) {
                    mInfoTextView.setText("It's Android's turn.")
                    val move: Int = mGame.getcomputerMove()
                    setMove(TicTacToe.COMPUTER_PLAYER, move)
                    winner = mGame.checkForWinner()
                }
                if (winner == 0) {
                    mInfoTextView.setText("It's your turn.")
                } else if (winner == 1) {
                    mInfoTextView.setText("It's a tie!")
                    for(i in 0 until mBoardButtons.size){
                        mBoardButtons[i].isEnabled =false
                    }
                } else if(winner == 2) {
                     mInfoTextView.setText("You won!")
                    for(i in 0 until mBoardButtons.size){
                        mBoardButtons[i].isEnabled =false
                    }
                } else if (winner == 3) {
                    mInfoTextView.setText("Android won!")
                    for(i in 0 until mBoardButtons.size){
                        mBoardButtons[i].isEnabled =false
                    }
                }
            }
        }
    }
    private fun setMove(player: Char, location: Int) {
        mGame.setMove(player, location)
        mBoardButtons[location]?.isEnabled = false
        mBoardButtons[location]?.text = player.toString()
        if (player == TicTacToe.HUMAN_PLAYER) mBoardButtons[location]?.setTextColor(
            Color.rgb(
                0,
                200,
                0
            )
        ) else mBoardButtons[location]?.setTextColor(Color.rgb(200, 0, 0))
    }

}

