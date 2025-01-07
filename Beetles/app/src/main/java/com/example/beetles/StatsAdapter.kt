package com.example.beetles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatsAdapter(var players: List<Stats>, var context: Context) : RecyclerView.Adapter<StatsAdapter.MyViewHolder>() {
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val playerName = view.findViewById<TextView>(R.id.stats_list_player_name)
        val score = view.findViewById<TextView>(R.id.stats_list_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stats_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return players.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.playerName.text = players[position].playerName
        holder.score.text = players[position].score.toString()
    }
}