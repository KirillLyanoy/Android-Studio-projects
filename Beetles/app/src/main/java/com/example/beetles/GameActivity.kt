package com.example.beetles

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {
    private lateinit var drawView: Draw
    private var isRunning = true
    var insects: MutableList<Insect> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val screenWidth = this.resources.displayMetrics.widthPixels
        val screenHeight = this.resources.displayMetrics.heightPixels

        val currentPlayerSettings = intent.getSerializableExtra("settings") as? Settings

        drawView = Draw(this, insects)
        drawView.setBackgroundColor(ContextCompat.getColor(this, R.color.game_bg))
        setContentView(drawView)
        ViewCompat.setOnApplyWindowInsetsListener(drawView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startDrawingLoop()

        Thread({ startSpawnBeetles(currentPlayerSettings!!.beetlesRespawnSpeed, insects, currentPlayerSettings,
            screenWidth, screenHeight) }).start()
    }

    private fun startDrawingLoop() {
        drawView.post(object : Runnable {
            override fun run() {
                if (isRunning) {
                    drawView.invalidate()
                    drawView.postDelayed(this, 16)
                }
            }
        })
    }

    private fun startSpawnBeetles(respawnSpeed: Int, insects: MutableList<Insect>,
                                  currentPlayerSettings: Settings, screenWidth: Int, screenHeight: Int) {
        while (isRunning) {
            var bug: Cockroach = Cockroach(
                true,
                0.0,
                (0..screenHeight).random().toDouble(),
                (0..360).random().toFloat(),
                15,
                screenWidth,
                screenHeight,
                301 - currentPlayerSettings!!.beetlesMoveSpeed)
            bug.loadImages(this)
            insects.add(bug)
            var thread = Thread({ bug.moveInsect() })
            thread.start()
            Thread.sleep(3000 - respawnSpeed.toLong())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        for (insect in insects) insect.isLiving = false
    }

    class Draw(context: Context, val insects: MutableList<Insect>) : View(context) {
        override fun onDraw(canvas: Canvas) {
            var insectsClone = insects.toMutableList()

            for (insect in insectsClone) {
                if (insect.isLiving) {
                    val rotatedBitmap: Bitmap =
                        rotateBitmap(insect.currentMoveImage, insect.directionAngle)
                    canvas.drawBitmap(
                        rotatedBitmap,
                        insect.coordX.toFloat(),
                        insect.coordY.toFloat(),
                        null
                    )
                } else {
                    insects.remove(insect)
                }
            }
            super.onDraw(canvas)
        }

        private fun rotateBitmap(bitmap: Bitmap, directionAngle: Float): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(directionAngle.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
    }
}