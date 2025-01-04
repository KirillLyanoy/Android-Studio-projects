package com.example.beetles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }

        val saveSettingsButton = findViewById<Button>(R.id.saveSettingsButton)
        val cancelSettingsButton = findViewById<Button>(R.id.cancelSettingsButton)

        var inputPlayerName = findViewById<EditText>(R.id.inputPlayerName)
        var musicVolume = findViewById<CheckBox>(R.id.musicVolume)
        var soundVolume = findViewById<CheckBox>(R.id.soundVolume)
        var beetlesMoveSpeed = findViewById<SeekBar>(R.id.beetlesMoveSpeedBar)
        var beetlesRespawnSpeed = findViewById<SeekBar>(R.id.beetlesRespawnSpeedBar)

        val currentPlayerSettings = intent.getSerializableExtra("settings") as? Settings

        if (currentPlayerSettings != null) {
            inputPlayerName.setText(currentPlayerSettings.playerName)
            musicVolume.isChecked = currentPlayerSettings.musicVolume
            soundVolume.isChecked = currentPlayerSettings.soundVolume
            beetlesMoveSpeed.progress = currentPlayerSettings.beetlesMoveSpeed
            beetlesRespawnSpeed.progress = currentPlayerSettings.beetlesRespawnSpeed
        }

        saveSettingsButton.setOnClickListener {
            if (currentPlayerSettings != null) {
                currentPlayerSettings.playerName = inputPlayerName.text.toString()
                currentPlayerSettings.musicVolume = musicVolume.isChecked
                currentPlayerSettings.soundVolume = soundVolume.isChecked
                currentPlayerSettings.beetlesMoveSpeed = beetlesMoveSpeed.progress
                currentPlayerSettings.beetlesRespawnSpeed = beetlesRespawnSpeed.progress
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