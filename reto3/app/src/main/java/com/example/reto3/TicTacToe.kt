package com.example.reto3

import java.util.InputMismatchException
import java.util.Random
import java.util.Scanner


class TicTacToe{
    private val mBoard = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9')
    private val BOARD_SIZE = 9
    private val mRand: Random

    init {

        // Seed the random number generator
        mRand = Random()
        var turn = HUMAN_PLAYER // Human starts first
        var win = 0 // Set to 1, 2, or 3 when game is over

        // Keep looping until someone wins or a tie
        while (win == 0) {
            displayBoard()
            turn = if (turn == HUMAN_PLAYER) {
                userMove
                COMPUTER_PLAYER
            } else {
                computerMove
                HUMAN_PLAYER
            }
            win = checkForWinner()
        }
        displayBoard()

        // Report the winner
        println()
        if (win == 1) println("It's a tie.") else if (win == 2) println(HUMAN_PLAYER.toString() + " wins!") else if (win == 3) println(
            COMPUTER_PLAYER.toString() + " wins!"
        ) else println("There is a logic problem!")
    }

    private fun displayBoard() {

    }

    // Check for a winner.  Return
    //  0 if no winner or tie yet
    //  1 if it's a tie
    //  2 if X won
    //  3 if O won
    private fun checkForWinner(): Int {

        // Check horizontal wins
        run {
            var i = 0
            while (i <= 6) {
                if (mBoard[i] == HUMAN_PLAYER && mBoard[i + 1] == HUMAN_PLAYER && mBoard[i + 2] == HUMAN_PLAYER
                ) return 2
                if (mBoard[i] == COMPUTER_PLAYER && mBoard[i + 1] == COMPUTER_PLAYER && mBoard[i + 2] == COMPUTER_PLAYER
                ) return 3
                i += 3
            }
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

    val userMove: Unit
        get() {
            // Eclipse throws a NullPointerException with Console.readLine
            // Known bug: https://bugs.eclipse.org/bugs/show_bug.cgi?id=122429
            //Console console = System.console();
            val s = Scanner(System.`in`)
            var move = -1
            while (move == -1) {
                try {
                    print("Enter your move: ")
                    move = s.nextInt()
                    while (move < 1 || move > BOARD_SIZE || mBoard[move - 1] == HUMAN_PLAYER || mBoard[move - 1] == COMPUTER_PLAYER) {
                        if (move < 1 || move > BOARD_SIZE) println("Please enter a move between 1 and $BOARD_SIZE.") else println(
                            "That space is occupied.  Please choose another space."
                        )
                        print("Enter your move: ")
                        move = s.nextInt()
                    }
                } catch (ex: InputMismatchException) {
                    println("Please enter a number between 1 and $BOARD_SIZE.")
                    s.next() // Get next line so we start fresh
                    move = -1
                }
            }
            mBoard[move - 1] = HUMAN_PLAYER
        }
    private val computerMove: Unit
        private get() {
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
        const val OPEN_SPOT = 'p'
        /**
         * @param args
         */
        @JvmStatic
        fun main(args: Array<String>) {
            TicTacToe()
        }
    }
}