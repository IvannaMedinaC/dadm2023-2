package com.example.reto3
import java.util.Random


public class TicTacToe{
    var mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    private var testBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    val BOARD_SIZE = 9
    var mov = false
    private val mRand: Random
    // The computer's difficulty levels
    enum class DifficultyLevel { Easy, Harder, Expert };
    // Current difficulty level
    private var mDifficultyLevel = DifficultyLevel.Expert

    init {
        // Seed the random number generator
        mRand = Random()
        var turn = HUMAN_PLAYER // Human starts first
        var win = 0 // Set to 1, 2, or 3 when game is over

        // Keep looping until someone wins or a tie
    }

    fun getDifficultyLevel(): DifficultyLevel? {
        return mDifficultyLevel
    }
    fun setDifficultyLevel(difficultyLevel: DifficultyLevel) {
        mDifficultyLevel = difficultyLevel
    }

    public fun clearBoard() {
        for (i in 0 until BOARD_SIZE){
            mBoard[i]= OPEN_SPOT
        }
    }

    //Pone la x o la o en la casilla dada
    public fun setMove (player: Char, location: Int){
        if(mBoard[location]== OPEN_SPOT){
            mBoard[location] = player
            mov = true
        }else{
            mov = false
        }
    }

    public fun verifyMove (location: Int): Boolean{
        return mBoard[location]== OPEN_SPOT
    }
    public fun checkForWinner(): Int {

        // Check horizontal wins
        for (i in 0..6 step 3) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER) return 2
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER) return 3
        }

        // Check vertical wins
        for (i in 0..2) {
            if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 3] == HUMAN_PLAYER && mBoard[i + 6] == HUMAN_PLAYER) return 2
            if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 3] == COMPUTER_PLAYER && mBoard[i + 6] == COMPUTER_PLAYER) return 3
        }

        // Check for diagonal wins
        if (mBoard[0] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[8] == HUMAN_PLAYER || mBoard[2] == HUMAN_PLAYER && mBoard[4] == HUMAN_PLAYER && mBoard[6] == HUMAN_PLAYER) return 2
        if (mBoard[0] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[8] == COMPUTER_PLAYER || mBoard[2] == COMPUTER_PLAYER && mBoard[4] == COMPUTER_PLAYER && mBoard[6] == COMPUTER_PLAYER) return 3

        // Check for tie
        for (i in 0 until BOARD_SIZE) {
            // If we find a number, then no one has won yet
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) return 0
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1
    }

    fun getcomputerMove(): Int{
        var move: Int
        move = -1

        if(mDifficultyLevel == DifficultyLevel.Easy)
            move = getRandomMove();

        else if(mDifficultyLevel == DifficultyLevel.Harder){
            move = getWinningMove()
            if(move == -1){
                move = getRandomMove()
            }
        }
        else if(mDifficultyLevel == DifficultyLevel.Expert){
            move = getWinningMove()
            if(move == -1){
                move = getBlockingMove()
            }
            if(move == -1){
                move = getRandomMove()
            }
        }
        return move
    }
    fun getRandomMove():Int{
        // Generate random move
        var move: Int
        do {
            move = mRand.nextInt(BOARD_SIZE)
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER)
        mBoard[move] = COMPUTER_PLAYER
        return move
    }
    fun getWinningMove():Int{
        // First see if there's a move O can make to win
        var move: Int
        move = -1
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                val curr = mBoard[i]
                mBoard[i] = COMPUTER_PLAYER
                if (checkForWinner() == 3) {
                    move = i
                    break
                }
                else mBoard[i] = curr
            }
        }
        return move
    }
    fun getBlockingMove():Int{
        var move:Int
        move = -1
        // See if there's a move O can make to block X from winning
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                val curr = mBoard[i] // Save the current number
                mBoard[i] = HUMAN_PLAYER
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER
                    move = i
                    break
                } else mBoard[i] = curr
            }
        }
        return move
    }

    fun getBoardOccupant(i : Int) : Char{
        return mBoard[i]
    }

    fun getBoardState() : CharArray{
        return mBoard
    }

    fun setBoardState(newB : CharArray){
        mBoard = newB
    }
    companion object {
        const val HUMAN_PLAYER = 'X'
        const val COMPUTER_PLAYER = 'O'
        const val OPEN_SPOT = ' '
        const val SIZE_B = 9
        /**
         * @param args
         */
        @JvmStatic
        fun main(args: Array<String>) {
            TicTacToe()
        }
    }
}