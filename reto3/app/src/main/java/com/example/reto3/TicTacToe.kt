package com.example.reto3

import java.util.InputMismatchException
import java.util.Random
import java.util.Scanner

public class TicTacToe{
    private val mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    private var testBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    private val BOARD_SIZE = 9
    private val mRand: Random
    init {
        // Seed the random number generator
        mRand = Random()
        var turn = HUMAN_PLAYER // Human starts first
        var win = 0 // Set to 1, 2, or 3 when game is over

        // Keep looping until someone wins or a tie
    }

    //Borra el tablero
    public fun ClearBoard() {
        for (i in 0 until BOARD_SIZE){
            mBoard[i]= OPEN_SPOT
        }
    }

    //Pone la x o la o en la casilla dada
    public fun setMove (player: Char, location: Int) {
        if(mBoard[location]== OPEN_SPOT){
            mBoard[location] = player
        }
    }

    //Determina el movimiento del computador (o)
    public fun getComputerMove(): Int{
        var move: Int
        testBoard = mBoard
        // First see if there's a move O can make to win
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                testBoard[i] = COMPUTER_PLAYER
                if (checkForWinner(testBoard) == 3) {
                    //println("Computer is moving to " + (i + 1))
                    return i
                }
            }
        }

        // See if there's a move O can make to block X from winning
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                testBoard[i] = HUMAN_PLAYER
                if (checkForWinner(testBoard) == 2) {
                    //println("Computer is moving to " + (i + 1))
                    return i
                }
            }
        }

        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE)
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER)
        return move
    }

    public fun checkForWinner(Board: CharArray): Int {

        // Check horizontal wins
        for (i in 0..6 step 3) {
            if (Board[i] == HUMAN_PLAYER && Board[i + 1] == HUMAN_PLAYER && Board[i + 2] == HUMAN_PLAYER) return 2
            if (Board[i] == COMPUTER_PLAYER && Board[i + 1] == COMPUTER_PLAYER && Board[i + 2] == COMPUTER_PLAYER) return 3
        }

        // Check vertical wins
        for (i in 0..2) {
            if (Board[i] == HUMAN_PLAYER && Board[i + 3] == HUMAN_PLAYER && Board[i + 6] == HUMAN_PLAYER) return 2
            if (Board[i] == COMPUTER_PLAYER && Board[i + 3] == COMPUTER_PLAYER && Board[i + 6] == COMPUTER_PLAYER) return 3
        }

        // Check for diagonal wins
        if (Board[0] == HUMAN_PLAYER && Board[4] == HUMAN_PLAYER && Board[8] == HUMAN_PLAYER || Board[2] == HUMAN_PLAYER && Board[4] == HUMAN_PLAYER && Board[6] == HUMAN_PLAYER) return 2
        if (Board[0] == COMPUTER_PLAYER && Board[4] == COMPUTER_PLAYER && Board[8] == COMPUTER_PLAYER || Board[2] == COMPUTER_PLAYER && Board[4] == COMPUTER_PLAYER && Board[6] == COMPUTER_PLAYER) return 3

        // Check for tie
        for (i in 0 until BOARD_SIZE) {
            // If we find a number, then no one has won yet
            if (Board[i] != HUMAN_PLAYER && Board[i] != COMPUTER_PLAYER) return 0
        }

        // If we make it through the previous loop, all places are taken, so it's a tie
        return 1
    }

    private fun computerMove(){
        var move: Int
        // First see if there's a move O can make to win
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                val curr = mBoard[i]
                mBoard[i] = COMPUTER_PLAYER
                if (checkForWinner() == 3) {
                    println("Computer is moving to " + (i + 1))
                    return
                } else mBoard[i] = curr
            }
        }

        // See if there's a move O can make to block X from winning
        for (i in 0 until BOARD_SIZE) {
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != COMPUTER_PLAYER) {
                val curr = mBoard[i] // Save the current number
                mBoard[i] = HUMAN_PLAYER
                if (checkForWinner() == 2) {
                    mBoard[i] = COMPUTER_PLAYER
                    println("Computer is moving to " + (i + 1))
                    return
                } else mBoard[i] = curr
            }
        }

        // Generate random move
        do {
            move = mRand.nextInt(BOARD_SIZE)
        } while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == COMPUTER_PLAYER)
        println("Computer is moving to " + (move + 1))
        mBoard[move] = COMPUTER_PLAYER
    }





    companion object {
        const val HUMAN_PLAYER = 'X'
        const val COMPUTER_PLAYER = 'O'
        const val OPEN_SPOT = ' '
        /**
         * @param args
         */
        @JvmStatic
        fun main(args: Array<String>) {
            TicTacToe()
        }
    }
}