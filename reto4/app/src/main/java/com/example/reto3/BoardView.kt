package com.example.reto3

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View


class BoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    companion object {

        const val GRID_WIDTH = 6
        private val mHumanBitmap: Bitmap? = null
        private val mComputerBitmap: Bitmap? = null
    }
}