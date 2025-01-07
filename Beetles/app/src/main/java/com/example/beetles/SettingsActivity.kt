package com.example.beetles

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class SettingsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveSettingsButton = findViewById<Button>(R.id.saveSettingsButton)
        val cancelSettingsButton = findViewById<Button>(R.id.cancelSettingsButton)

        var inputPlayerName = findViewById<EditText>(R.id.inputPlayerName)
        var beetlesMax = findViewById<EditText>(R.id.beetlesMax)
        var musicVolume = findViewById<CheckBox>(R.id.musicVolume)
        var soundVolume = findViewById<CheckBox>(R.id.soundVolume)
        var beetlesMoveSpeed = findViewById<SeekBar>(R.id.beetlesMoveSpeedBar)
        var beetlesRespawnSpeed = findViewById<SeekBar>(R.id.beetlesRespawnSpeedBar)

        beetlesMoveSpeed.min = 10
        beetlesMoveSpeed.max = 200
        beetlesRespawnSpeed.min = 500
        beetlesRespawnSpeed.max = 3000

        val currentPlayerSettings = intent.getSerializableExtra("settings") as? Settings

        if (currentPlayerSettings != null) {
            inputPlayerName.setText(currentPlayerSettings.playerName)
            musicVolume.isChecked = currentPlayerSettings.musicVolume
            soundVolume.isChecked = currentPlayerSettings.soundVolume
            beetlesMoveSpeed.progress = currentPlayerSettings.beetlesMoveSpeed
            beetlesRespawnSpeed.progress = currentPlayerSettings.beetlesRespawnSpeed
            beetlesMoveSpeed.progress = currentPlayerSettings.beetlesMoveSpeed
            beetlesRespawnSpeed.progress = currentPlayerSettings.beetlesRespawnSpeed
            beetlesMax.setText(currentPlayerSettings.beetlesMax.toString())
        }

        saveSettingsButton.setOnClickListener {
            if (currentPlayerSettings != null) {
                currentPlayerSettings.playerName = inputPlayerName.text.toString()
                currentPlayerSettings.musicVolume = musicVolume.isChecked
                currentPlayerSettings.soundVolume = soundVolume.isChecked
                currentPlayerSettings.beetlesMoveSpeed = beetlesMoveSpeed.progress
                currentPlayerSettings.beetlesRespawnSpeed = beetlesRespawnSpeed.progress
                currentPlayerSettings.beetlesMax = beetlesMax.text.toString().toInt()
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("settings", currentPlayerSettings)
            startActivity(intent)
        }

        cancelSettingsButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("settings", currentPlayerSettings)
            startActivity(intent)
        }
    }
}