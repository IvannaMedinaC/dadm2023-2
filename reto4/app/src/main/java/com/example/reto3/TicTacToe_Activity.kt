package com.example.reto3

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.app.AlertDialog;
import android.widget.Toast
import android.content.DialogInterface


public class TicTacToe_Activity :AppCompatActivity() {

    private lateinit var mGame: TicTacToe

    // Buttons making up the board
    private lateinit var mBoardButtons: ArrayList<Button>

    // Various text displayed
    private lateinit var mInfoTextView: TextView
    private lateinit var newButton: Button
    val DIALOG_DIFFICULTY_ID = 0
    val DIALOG_QUIT_ID = 1

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_tic_tac_toe)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        /*newButton.setOnClickListener { mGame.clearBoard()
            for (i in 0 until mBoardButtons.size) {
            mBoardButtons[i]?.text = ""
            mBoardButtons[i]?.isEnabled = true
            mBoardButtons[i]?.setOnClickListener(ButtonClickListener(i))

        }
        }*/
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
                    for (i in 0 until mBoardButtons.size) {
                        mBoardButtons[i].isEnabled = false
                    }
                } else if (winner == 2) {
                    mInfoTextView.setText("You won!")
                    for (i in 0 until mBoardButtons.size) {
                        mBoardButtons[i].isEnabled = false
                    }
                } else if (winner == 3) {
                    mInfoTextView.setText("Android won!")
                    for (i in 0 until mBoardButtons.size) {
                        mBoardButtons[i].isEnabled = false
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


