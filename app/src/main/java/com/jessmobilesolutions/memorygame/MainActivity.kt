package com.jessmobilesolutions.memorygame

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = Screen(this)
        setContentView(screen)
        screen.setOnTouchListener(screen)
    }

    inner class Screen(context: Context) : View(context), View.OnTouchListener {
        private var paint = Paint()
        private var rect = Rect()
        private val rnd = Random
        override fun draw(canvas: Canvas) {
            super.draw(canvas)
            val screenWidth = width
            val screenHeight = height
            val left = (screenWidth - 500) / 2
            val top = (screenHeight - 500) / 2
            val right = left + 500
            val bottom = top + 500

            paint.color = Color.rgb(135, 206, 250)

            rect.set(left, top, right, bottom)
            canvas.drawRect(rect, paint)

            paint.textSize = 100F
            paint.typeface = Typeface.createFromAsset(context.assets, "FromCartoonBlocks.ttf")
            paint.color = Color.BLACK
            paint.textAlign = Paint.Align.CENTER

            val xPosStart = (left + right) / 2f
            val yPosStart = (top + bottom) / 2f - ((paint.descent() + paint.ascent()) / 2)

            canvas.drawText("START", xPosStart, yPosStart, paint)

            paint.textSize = 70F
            paint.color = Color.BLACK

            val xPosClickStart = (canvas.width / 2).toFloat()
            val yPosClickStart =
                (bottom + screenHeight) / 2 - ((paint.descent() + paint.ascent()) / 2)

            canvas.drawText("CLIQUE NO START PARA INICIAR", xPosClickStart, yPosClickStart, paint)

            invalidate()
        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x.toInt()
                val y = event.y.toInt()
                if (rect.contains(x, y)) {
                    val intent = Intent(this@MainActivity, Memory::class.java)
                    startActivity(intent)
                }
            }

            return super.onTouchEvent(event)
        }

    }
}