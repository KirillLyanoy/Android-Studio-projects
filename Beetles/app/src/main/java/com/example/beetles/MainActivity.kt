package com.example.beetles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            return@setOnApplyWindowInsetsListener insets
        }
        val currentPlayerSettings: Settings
        if (intent.getSerializableExtra("settings") == null)
            currentPlayerSettings = Settings()
        else
            currentPlayerSettings = (intent.getSerializableExtra("settings") as? Settings)!!

        val playerName = findViewById<TextView>(R.id.player_name)
        val start_button = findViewById<Button>(R.id.start_button)
        val settings_button = findViewById<Button>(R.id.settings_button)
        val exit_button = findViewById<Button>(R.id.exit_button)

        playerName.text = currentPlayerSettings.playerName

        start_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        settings_button.setOnClickListener {
           val intent = Intent(this, SettingsActivity::class.java)
            intent.putExtra("settings", currentPlayerSettings)
            startActivity(intent)
        }

        exit_button.setOnClickListener {
        }
    }
}