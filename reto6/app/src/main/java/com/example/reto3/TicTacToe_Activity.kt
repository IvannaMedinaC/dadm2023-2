package com.example.reto3

import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
    var winner: Int = 0
    private var mGameOver: Boolean = false
    private var mGoFirst: Char = 'H'
    var handler: Handler = Handler()
    private lateinit var mPrefs: SharedPreferences


    lateinit var mHumanMediaPlayer: MediaPlayer
    lateinit var mComputerMediaPlayer: MediaPlayer

    // Various text displayed
    private lateinit var mInfoTextView: TextView
    private lateinit var Human: TextView
    private lateinit var Computer: TextView
    private lateinit var Ties: TextView

    private lateinit var newButton: Button
    val DIALOG_DIFFICULTY_ID = 0
    val DIALOG_RESET_ID = 1

    var mHumanWins: Int = 0
    var mComputerWins: Int = 0
    var mTies: Int = 0

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        mGame = TicTacToe()
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE)
        mHumanWins = mPrefs.getInt("mHumanWins", 0);
        mComputerWins = mPrefs.getInt("mComputerWins", 0);
        mTies = mPrefs.getInt("mTies", 0);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_tic_tac_toe)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mBoardView = findViewById<BoardView>(R.id.board)
        mBoardView.setOnTouchListener(mTouchListener)
        mBoardView.setGame(mGame)

        mInfoTextView = findViewById<TextView>(R.id.information)
        Human = findViewById<TextView>(R.id.Human)
        Computer = findViewById<TextView>(R.id.Computer)
        Ties = findViewById<TextView>(R.id.Ties)

        if(savedInstanceState == null){
            startNewGame()
        }
        else{
            // Restore the game's state
            mGame.setBoardState(savedInstanceState.getCharArray("board")!!)
            mGameOver = savedInstanceState.getBoolean("mGameOver")
            mInfoTextView.setText(savedInstanceState.getCharSequence("info"))
            mHumanWins = savedInstanceState.getInt("mHumanWins")
            mComputerWins = savedInstanceState.getInt("mComputerWins")
            mTies = savedInstanceState.getInt("mTies")
            mGoFirst = savedInstanceState.getChar("mGoFirst")
        }
        displayScores()

    }

    private fun displayScores() {
        Human.setText("Person: $mHumanWins")
        Computer.setText("Computer: $mComputerWins")
        Ties.setText("Ties: $mTies")
    }

    private fun startNewGame() {
        mGame.clearBoard()
        // Reset view
        mGameOver = false
        mBoardView.isEnabled = true
        mBoardView.isClickable = true
        mBoardView.invalidate()  //Redraw the board
        // Human goes first

        Human.setText("Person: $mHumanWins")
        Computer.setText("Computer: $mComputerWins")
        Ties.setText("Ties: $mTies")
        if(mGoFirst == 'H'){
            mInfoTextView.setText("You go first")
        }
    }

    val mTouchListener = View.OnTouchListener { v, event ->
        // Determina qué celda se tocó
        val col = (event.x / mBoardView.getBoardCellWidth()).toInt()
        val row = (event.y / mBoardView.getBoardCellHeight()).toInt()
        val pos = row * 3 + col

        if (mGame.verifyMove(pos)){
            setMove(TicTacToe.HUMAN_PLAYER, pos)
            mHumanMediaPlayer.start()
            winner = mGame.checkForWinner()

            if (winner == 0) {
                handler.postDelayed({Log.v("Log", "Hello")},10000)
                mGoFirst = 'A'
                mInfoTextView.setText("It's Android's turn.")
                mBoardView.isEnabled = true
                val move: Int = mGame.getcomputerMove()
                setMove(TicTacToe.COMPUTER_PLAYER, move)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    mComputerMediaPlayer.start()
                },1000)
                winner = mGame.checkForWinner()
            }
            if (winner == 0) {
                mGoFirst = 'H'
                mInfoTextView.setText("It's your turn.")
                mBoardView.isEnabled = true
            } else if (winner == 1) {
                mInfoTextView.setText("It's a tie!")
                mBoardView.isClickable = false
                mBoardView.isEnabled = false
                mGameOver = true
                mTies ++

            } else if (winner == 2) {
                mInfoTextView.setText("You won!")
                mBoardView.isClickable = false
                mBoardView.isEnabled = false
                mGameOver = true
                mHumanWins ++
            } else if (winner == 3) {
                mInfoTextView.setText("Android won!")
                mBoardView.isClickable = false
                mBoardView.isEnabled = false
                mGameOver = true
                mComputerWins++
            }
        }
        Human.setText("Person: $mHumanWins")
        Computer.setText("Computer: $mComputerWins")
        Ties.setText("Ties: $mTies")
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

            R.id.Reset -> {
                showDialog(DIALOG_RESET_ID)
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

            DIALOG_RESET_ID ->{
                builder.setMessage(R.string.reset_question)
                builder.setCancelable(false)
                builder.setPositiveButton(R.string.yes) { dialog, id ->
                    mHumanWins = 0
                    mComputerWins = 0
                    mTies = 0
                    displayScores()
                }
                builder.setNegativeButton(R.string.no, null)
                dialog = builder.create()
            }
        }
        return dialog!!
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharArray("board", mGame.getBoardState())
        outState.putBoolean("mGameOver", mGameOver)
        outState.putInt("mHumanWins", Integer.valueOf(mHumanWins))
        outState.putInt("mComputerWins", Integer.valueOf(mComputerWins))
        outState.putInt("mTies", Integer.valueOf(mTies))
        outState.putCharSequence("info", mInfoTextView.text)
        outState.putChar("mGoFirst", mGoFirst)
    }
    override fun onResume() {
        super.onResume()
        mHumanMediaPlayer = MediaPlayer.create(applicationContext, R.raw.planeta)
        mComputerMediaPlayer = MediaPlayer.create(applicationContext, R.raw.planeta)
    }

    override fun onPause() {
        super.onPause()
        mHumanMediaPlayer.release()
        mComputerMediaPlayer.release()
    }

    override fun onStop() {
        super.onStop()
        // Save the current scores
        val ed = mPrefs!!.edit()
        ed.putInt("mHumanWins", mHumanWins)
        ed.putInt("mComputerWins", mComputerWins)
        ed.putInt("mTies", mTies)
        ed.commit()
    }
}


