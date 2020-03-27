package com.example.cse438.cse438_assignment4.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.util.User

class ScoreboardViewHolder(inflater: LayoutInflater, parent : ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_scoreboardentry, parent, false)){
    private val plName: TextView?
    private val plChips: TextView?
    private val plWins: TextView?
    private val plLosses: TextView?
    private val plContainer: LinearLayout?

    //show playlists in a list
    init {
        plName = itemView.findViewById(R.id.playerName)
        plChips = itemView.findViewById(R.id.playerChipCount)
        plWins = itemView.findViewById(R.id.playerWins)
        plLosses = itemView.findViewById(R.id.playerLosses)
        plContainer = itemView.findViewById(R.id.sbContainer)
    }

    fun bind(pl: User) {
        plName?.text = pl.name
        plChips?.text = pl.chips.toString()
        plWins?.text = pl.wins.toString()
        plLosses?.text = pl.losses.toString()
    }
}
/**
 * RecyclerView adapter for a list of Players.
 */
class ScoreboardAdapter(private val players: ArrayList<User>?) : RecyclerView.Adapter<ScoreboardViewHolder>(){
    private var listPlayers: ArrayList<User>? = players
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ScoreboardViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ScoreboardViewHolder, position: Int) {
        val event: User = listPlayers!!.get(position)
        holder.bind(event)
    }

    override fun getItemCount(): Int = players!!.size
}
