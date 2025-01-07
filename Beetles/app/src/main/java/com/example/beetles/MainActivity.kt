package com.example.beetles

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)


        var currentPlayerSettings: Settings


        if (intent.getSerializableExtra("settings") == null)
            if (sharedPreferences.contains("playerName") && sharedPreferences.contains("musicVolume") &&
                sharedPreferences.contains("soundVolume") && sharedPreferences.contains("beetlesMoveSpeed") &&
                sharedPreferences.contains("beetlesRespawnSpeed") && sharedPreferences.contains("beetlesMax")
            ) {
                currentPlayerSettings = Settings(
                    sharedPreferences.getString("playerName", "") ?: "",
                    sharedPreferences.getBoolean("musicVolume", true),
                    sharedPreferences.getBoolean("soundVolume", true),
                    sharedPreferences.getInt("beetlesMoveSpeed", 100),
                    sharedPreferences.getInt("beetlesRespawnSpeed", 1000),
                    sharedPreferences.getInt("beetlesMax", 20)
                )
            } else currentPlayerSettings = Settings()
        else currentPlayerSettings = (intent.getSerializableExtra("settings") as? Settings)!!

        val playerName = findViewById<TextView>(R.id.player_name)
        val start_button = findViewById<Button>(R.id.start_button)
        val settings_button = findViewById<Button>(R.id.settings_button)
        val stats_button = findViewById<Button>(R.id.stats_button)
        val exit_button = findViewById<Button>(R.id.exit_button)


        playerName.text = currentPlayerSettings.playerName

        start_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("settings", currentPlayerSettings)
            startActivity(intent)
        }

        settings_button.setOnClickListener {
           val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("settings", currentPlayerSettings)
            startActivity(intent)
        }

        stats_button.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            intent.putExtra("settings", currentPlayerSettings)
            startActivity(intent)
        }

        exit_button.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }

        val editor = sharedPreferences.edit()
        editor.putString("playerName", currentPlayerSettings.playerName)
        editor.putBoolean("musicVolume", currentPlayerSettings.musicVolume)
        editor.putBoolean("soundVolume", currentPlayerSettings.soundVolume)
        editor.putInt("beetlesMoveSpeed", currentPlayerSettings.beetlesMoveSpeed)
        editor.putInt("beetlesRespawnSpeed", currentPlayerSettings.beetlesRespawnSpeed)
        editor.putInt("beetlesMax", currentPlayerSettings.beetlesMax)
        editor.apply()
    }
}