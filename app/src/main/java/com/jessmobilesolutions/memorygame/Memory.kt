package com.jessmobilesolutions.memorygame

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

class Memory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = Screen(this)
        setContentView(screen)
        screen.setOnTouchListener(screen)
    }

    inner class Screen(context: Context) : View(context), View.OnTouchListener {

        private var paint = Paint()
        private val bart = context.assets.open("bart.png")
        private val bart1 = context.assets.open("bart1.png")
        private val homer = context.assets.open("homer.png")
        private val homer1 = context.assets.open("homer1.png")
        private val lisa = context.assets.open("lisa.png")
        private val lisa1 = context.assets.open("lisa1.png")
        private var previousImageName: String? = null
        private var flippedSquare: Pair<Int, RectF>? = null
        private var matchedPairs = mutableListOf<Pair<Int, RectF>>()
        private var hits = 0
        private val cont = context


        private val images = arrayOf(
            Image(BitmapFactory.decodeStream(bart), "bart"),
            Image(BitmapFactory.decodeStream(homer1), "homer"),
            Image(BitmapFactory.decodeStream(homer), "homer"),
            Image(BitmapFactory.decodeStream(lisa1), "lisa"),
            Image(BitmapFactory.decodeStream(lisa), "lisa"),
            Image(BitmapFactory.decodeStream(bart1), "bart"),
        )

        override fun draw(canvas: Canvas?) {
            super.draw(canvas)
            val squareSize = 300f
            val gap = 100f
            val totalWidth = 2 * squareSize + gap
            val totalHeight = 3 * squareSize + 2 * gap
            val startX = (width - totalWidth) / 2
            val startY = (height - totalHeight) / 2

            val text = "Jogo da Memoria"
            paint.textSize = 120f
            paint.typeface = Typeface.createFromAsset(context.assets, "FromCartoonBlocks.ttf")
            paint.color = Color.BLACK
            val textWidth = paint.measureText(text)
            val textX = (width - textWidth) / 2
            val textY = startY - gap
            canvas?.drawText(text, textX, textY, paint)

            for (i in 0 until 3) {
                for (j in 0 until 2) {
                    val left = startX + j * (squareSize + gap)
                    val top = startY + i * (squareSize + gap)
                    val right = left + squareSize
                    val bottom = top + squareSize
                    val rect = RectF(left, top, right, bottom)

                    val matchedPair = matchedPairs.find { it.second == rect }
                    if (flippedSquare?.second == rect || matchedPair != null) {
                        val imageIndex = matchedPair?.first ?: flippedSquare!!.first
                        canvas?.drawBitmap(images[imageIndex].bitmap, null, rect, paint)
                    } else {
                        canvas?.drawRect(rect, paint)
                    }
                }
            }
        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x
                val y = event.y
                var index = 0
                val squareSize = 300f
                val gap = 100f
                val totalWidth = 2 * squareSize + gap
                val totalHeight = 3 * squareSize + 2 * gap
                val startX = (width - totalWidth) / 2
                val startY = (height - totalHeight) / 2

                for (i in 0 until 3) {
                    for (j in 0 until 2) {
                        val left = startX + j * (squareSize + gap)
                        val top = startY + i * (squareSize + gap)
                        val right = left + squareSize
                        val bottom = top + squareSize

                        val rect = RectF(left, top, right, bottom)

                        if (rect.contains(x, y)) {
                            flipSquare(index, rect)

                            if (hits == 6) {
                                Intent(cont, Winner::class.java).run {
                                    startActivity(this)
                                }
                            }
                            return true
                        }
                        index++
                    }
                }
            }

            return super.onTouchEvent(event)
        }

        private fun flipSquare(index: Int, rect: RectF) {
            val currentImageName = images[index].name
            flippedSquare = if (previousImageName == currentImageName) {
                matchedPairs.add(Pair(index, rect))
                hits++
                null
            } else {
                Pair(index, rect)
            }
            previousImageName = currentImageName
            invalidate()
        }
    }
}