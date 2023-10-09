package com.example.reto3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Rect


class BoardView(context: Context, attrs: AttributeSet?, defStyle: Int) : View(context, attrs, defStyle) {
    init {
        initialize()
    }

    companion object {
        const val GRID_WIDTH = 6
    }
    private lateinit var mHumanBitmap: Bitmap
    private lateinit var mComputerBitmap: Bitmap
    private lateinit var mPaint: Paint

    private lateinit var mGame: TicTacToe
    fun setGame(game: TicTacToe) {
        mGame = game
    }

    constructor(context: Context) : this(context, null) {
        // Aquí puedes realizar inicializaciones específicas del constructor
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        // Aquí puedes realizar inicializaciones específicas del constructor
    }

    fun initialize() {
        // planet: O, rocket: X
        mHumanBitmap = BitmapFactory.decodeResource(resources, R.drawable.rocket)
        mComputerBitmap = BitmapFactory.decodeResource(resources, R.drawable.mars)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG);
    }

    fun getBoardCellWidth() : Int {
        return (getWidth() / 3)
    }
    fun getBoardCellHeight() : Int {
        return (getHeight() / 3)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Determine the width and height of the View
        val boardWidth = width
        val boardHeight = height
        // Make thick, light gray lines
        mPaint!!.color = Color.LTGRAY
        mPaint!!.strokeWidth = GRID_WIDTH.toFloat()

        val cellWidth = boardWidth / 3.0
        val cellHeight = boardHeight / 3.0
        val cellWidth_f = cellWidth.toFloat()
        val cellHeight_f = cellHeight.toFloat()
        val origen = 0.toFloat()

        // Draw the two vertical board lines
        canvas.drawLine(cellWidth_f, origen, cellWidth_f, boardHeight.toFloat(), mPaint)
        canvas.drawLine(cellWidth_f * 2, origen, cellWidth_f * 2, boardHeight.toFloat(), mPaint)

        // Draw the two horizontal board lines
        canvas.drawLine(origen, cellHeight_f ,boardWidth.toFloat(), cellHeight_f, mPaint)
        canvas.drawLine(origen, cellHeight_f * 2, boardWidth.toFloat(), cellHeight_f * 2, mPaint)

        // Dibuja todas las imágenes de X y O
        for (i in 0 until mGame.BOARD_SIZE) {
            val col = i % 3
            val row = i / 3
            // Define los límites de un rectángulo de destino para la imagen

            val left: Int = cellWidth.toInt()*col
            val top: Int = cellHeight.toInt()*row
            val right: Int = left + cellWidth.toInt()
            val bottom: Int = top + cellHeight.toInt()

            if (mGame != null && mGame.getBoardOccupant(i) == TicTacToe.HUMAN_PLAYER) {
                canvas.drawBitmap(
                    mHumanBitmap,
                    null, // src
                    Rect(left, top, right, bottom), // dest
                    null
                )
            } else if (mGame != null && mGame.getBoardOccupant(i) == TicTacToe.COMPUTER_PLAYER) {
                canvas.drawBitmap(
                    mComputerBitmap,
                    null, // src
                    Rect(left, top, right, bottom), // dest
                    null
                )
            }
        }
    }
}
