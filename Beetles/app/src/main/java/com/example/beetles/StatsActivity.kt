package com.example.beetles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class StatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stats)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val returnButton = findViewById<Button>(R.id.return_button)
        val recyclerView = findViewById<RecyclerView>(R.id.players_stats)

        var file = File(filesDir,"playersScore.txt")
        var players: List<Stats> = listOf()

        if (file.exists()) {
            players = file.readLines().map { line ->
                val parts = line.split(" ")
                val playerName = parts[0]
                val score = parts[1].toInt()
                Stats(playerName, score)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = StatsAdapter(players.sortedByDescending { it.score }, this)

        returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}