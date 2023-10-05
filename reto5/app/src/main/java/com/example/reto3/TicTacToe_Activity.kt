package com.example.reto3

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


public class TicTacToe_Activity :AppCompatActivity() {

    private lateinit var mGame: TicTacToe
    private lateinit var mBoardView: BoardView

    // Buttons making up the board
    private lateinit var mBoardButtons: ArrayList<Button>

    // Various text displayed
    private lateinit var mInfoTextView: TextView
    private lateinit var newButton: Button
    val DIALOG_DIFFICULTY_ID = 0
    val DIALOG_QUIT_ID = 1

    @SuppressLint("ClickableViewAccessibility")
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        mGame = TicTacToe()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_tic_tac_toe)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mBoardView = findViewById<BoardView>(R.id.board)
        mBoardView.setOnTouchListener(mTouchListener)
        mBoardView.setGame(mGame)

        mInfoTextView = findViewById<TextView>(R.id.information)

        startNewGame()
    }

    private fun startNewGame() {
        mGame.clearBoard()
        // Reset view
        mBoardView.isClickable = true
        mBoardView.invalidate()  //Redraw the board
        // Human goes first
        mInfoTextView.setText("You go first.");
    }

    @SuppressLint("ClickableViewAccessibility")
    val mTouchListener = View.OnTouchListener { v, event ->
        // Determina qué celda se tocó
        val col = (event.x / mBoardView.getBoardCellWidth()).toInt()
        val row = (event.y / mBoardView.getBoardCellHeight()).toInt()
        val pos = row * 3 + col

        if (mGame.verifyMove(pos)) {
            setMove(TicTacToe.HUMAN_PLAYER, pos)
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
                mBoardView.isClickable = false
            } else if (winner == 2) {
                mInfoTextView.setText("You won!")
                mBoardView.isClickable = false
            } else if (winner == 3) {
                mInfoTextView.setText("Android won!")
                mBoardView.isClickable = false
            }
        }
        false
    }

    private fun setMove(player: Char, location: Int): Boolean {
        if (mGame.verifyMove(location)) {
            mGame.setMove(player, location)
            mBoardView.invalidate() // Vuelve a dibujar el tablero
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.tictactoe_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_game -> {
                startNewGame()
                return true
            }

            R.id.ai_difficulty -> {
                showDialog(DIALOG_DIFFICULTY_ID)
                return true
            }

            R.id.quit -> {
                showDialog(DIALOG_QUIT_ID)
                return true
            }
        }
        return false
    }

    protected override fun onCreateDialog(id: Int): Dialog? {
        var dialog: Dialog? = null
        val builder = AlertDialog.Builder(this)

        when (id) {
            DIALOG_DIFFICULTY_ID -> {
                builder.setTitle(R.string.difficulty_choose)
                val levels = arrayOf(
                    getString(R.string.difficulty_easy),
                    getString(R.string.difficulty_harder),
                    getString(R.string.difficulty_expert)
                )
                var selected: Int = 2
                builder.setSingleChoiceItems(levels, selected) { dialog, item ->
                    dialog.dismiss()
                    if (item == 0) {
                        mGame.setDifficultyLevel(TicTacToe.DifficultyLevel.Easy)
                    } else if (item == 1) {
                        mGame.setDifficultyLevel(TicTacToe.DifficultyLevel.Harder)
                    } else if (item == 2) {
                        mGame.setDifficultyLevel(TicTacToe.DifficultyLevel.Expert)
                    }

                    Toast.makeText(applicationContext, levels[item], Toast.LENGTH_SHORT).show()
                }

                dialog = builder.create()
            }

            DIALOG_QUIT_ID ->{
                builder.setMessage(R.string.quit_question)
                builder.setCancelable(false)
                builder.setPositiveButton(R.string.yes) { dialog, id ->
                    this@TicTacToe_Activity.finish()
                }
                builder.setNegativeButton(R.string.no, null)
                dialog = builder.create()
            }
        }
        return dialog!!
    }

}


