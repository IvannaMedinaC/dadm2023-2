package com.example.reto3

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import edu.harding.tictactoe.BoardView

class BoardView(context: Context, attrs: AttributeSet?, defStyle: Int) : View(context, attrs, defStyle) {
    init {
        initialize()
    }

    companion object {
        const val GRID_WIDTH = 6
    }
    private var mHumanBitmap: Bitmap? = null
    private var mComputerBitmap: Bitmap? = null
    private val mPaint: Paint? = null

    // planet: O, rocket: X
    constructor(context: Context) : this(context, null) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        initialize()
    }

    fun initialize() {
        mHumanBitmap = BitmapFactory.decodeResource(resources, R.drawable.rocket)
        mComputerBitmap = BitmapFactory.decodeResource(resources, R.drawable.planet)
        val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Determine the width and height of the View
        val boardWidth = width
        val boardHeight = height
        // Make thick, light gray lines
        mPaint!!.color = Color.LTGRAY
        mPaint.strokeWidth = GRID_WIDTH.toFloat()
        // Draw the two vertical board lines
        val cellWidth = boardWidth / 3.0
        val cellHeight = boardHeight / 3.0
        val origen = 0
        val cellWidth_f = cellWidth.toFloat()
        val cellHeight_f = cellHeight.toFloat()

        //vertical
        canvas.drawLine(cellWidth_f, origen.toFloat(), cellWidth_f, boardHeight.toFloat(), mPaint)
        canvas.drawLine(cellWidth_f * 2, origen.toFloat(), cellWidth_f * 2, boardHeight.toFloat(), mPaint)
        //horizontal
        canvas.drawLine(origen.toFloat(), cellHeight_f, boardWidth.toFloat(), cellHeight_f,mPaint)
        canvas.drawLine(origen.toFloat(), cellHeight_f*2, boardWidth.toFloat(), cellHeight_f*2,mPaint)
    }

}