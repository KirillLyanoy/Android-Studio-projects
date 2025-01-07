package com.example.beetles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.view.MotionEvent
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
    var beetlesKilled: Int = 0
    lateinit var mediaPlayer: MediaPlayer

    lateinit var soundPool: SoundPool

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val screenWidth = this.resources.displayMetrics.widthPixels
        val screenHeight = this.resources.displayMetrics.heightPixels

        val currentPlayerSettings = intent.getSerializableExtra("settings") as? Settings

        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        if (currentPlayerSettings!!.musicVolume) {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                mediaPlayer.setLooping(true)
            }
        }
        soundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        var sound_press1 = soundPool.load(this, R.raw.sound_1, 1)
        var sound_press2 = soundPool.load(this, R.raw.sound_2, 1)
        var sound_press3 = soundPool.load(this, R.raw.sound_3, 1)

        drawView = Draw(this, insects, beetlesKilled, screenHeight)
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

        drawView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                if (motionEvent?.action == MotionEvent.ACTION_DOWN) {

                    val x = motionEvent.rawX
                    val y = motionEvent.rawY


                    for (insect in insects) {
                        if (x > insect.coordCenterX - 25 && x < insect.coordCenterX + 75 &&
                            y > insect.coordCenterY - 25 && y < insect.coordCenterY + 75)
                            if (insect.isLiving) {
                                Thread({ insect.isKilled() }).start()
                                if (currentPlayerSettings!!.soundVolume) {
                                    when ((1..3).random()) {
                                        1 -> soundPool.play(sound_press1, 1f, 1f, 1, 0, 1f)
                                        2 -> soundPool.play(sound_press2, 1f, 1f, 1, 0, 1f)
                                        3 -> soundPool.play(sound_press3, 1f, 1f, 1, 0, 1f)
                                    }
                                }
                                beetlesKilled++
                            }
                    }
                }
                return true
            }
        })
    }

    private fun startDrawingLoop() {
        drawView.post(object : Runnable {
            override fun run() {
                if (isRunning) {
                    drawView.beetlesKilled = beetlesKilled
                    drawView.invalidate()
                    drawView.postDelayed(this, 16)
                }
            }
        })
    }

    private fun startSpawnBeetles(respawnSpeed: Int, insects: MutableList<Insect>,
                                  currentPlayerSettings: Settings, screenWidth: Int, screenHeight: Int) {
        while (isRunning) {
            if (checkBeetlesNumber(insects, currentPlayerSettings)) {
                var insect: Insect
                when ((0..2).random()) {
                    0 -> {
                        insect = Cockroach(
                            true,
                            0.0,
                            (0..screenHeight).random().toDouble(),
                            (0..360).random().toFloat(),
                            10,
                            screenWidth,
                            screenHeight,
                            currentPlayerSettings!!.beetlesMoveSpeed
                        )
                        addBug(insect, 3001 - respawnSpeed)
                    }

                    1 -> {
                        insect = Spider(
                            true,
                            0.0,
                            (0..screenHeight).random().toDouble(),
                            (0..360).random().toFloat(),
                            10,
                            screenWidth,
                            screenHeight,
                            currentPlayerSettings!!.beetlesMoveSpeed
                        )
                        addBug(insect, 3001 - respawnSpeed)
                    }

                    2 -> {
                        insect = Centipede(
                            true,
                            0.0,
                            (0..screenHeight).random().toDouble(),
                            (0..360).random().toFloat(),
                            10,
                            screenWidth,
                            screenHeight,
                            currentPlayerSettings!!.beetlesMoveSpeed
                        )
                        addBug(insect, 3001 - respawnSpeed)
                    }
                }
            }
        }
    }

    private fun checkBeetlesNumber(insects: MutableList<Insect>, currentPlayerSettings: Settings): Boolean {
        var insectsClone = insects.toMutableList()

        var countBeetles = 0
        for (insect in insectsClone) {
            if (insect.isLiving) countBeetles++
        }

        if (countBeetles < currentPlayerSettings.beetlesMax) return true
        else return false
    }

    private fun addBug (insect: Insect, respawnSpeed: Int)
    {
        insect.loadImages(this)
        insects.add(insect)
        var thread = Thread({ insect.moveInsect() })
        thread.start()
        Thread.sleep(respawnSpeed.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        for (insect in insects) insect.isLiving = false
        soundPool?.release()
        mediaPlayer.stop()
    }
    class Draw(context: Context, val insects: MutableList<Insect>, var beetlesKilled: Int, val screenHeight: Int) : View(context) {
        override fun onDraw(canvas: Canvas) {
            var insectsClone = insects.toMutableList()
            var paint = Paint()
            paint.textSize = 36F
            canvas.drawText("Раздавлено жуков: $beetlesKilled", 40F,
                (screenHeight - 10).toFloat(), paint)
            for (insect in insectsClone) {
                if (!insect.isDecomposing) {
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