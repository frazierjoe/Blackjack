package com.google.firebase.example.fireeats.kotlin.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.adapters.FirestoreAdapter
import com.example.cse438.cse438_assignment4.util.Player
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_scoreboardentry.view.*

/**
 * RecyclerView adapter for a list of Players.
 */
open class ScoreboardAdapter(query: Query, private val listener: OnPlayerSelectedListener) :
    FirestoreAdapter<ScoreboardAdapter.ViewHolder>(query) {

    interface OnPlayerSelectedListener {

        fun onPlayerSelected(player: DocumentSnapshot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_scoreboardentry, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getSnapshot(position), listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            snapshot: DocumentSnapshot,
            listener: OnPlayerSelectedListener?
        ) {

            val player = snapshot.toObject(Player::class.java)
            if (player == null) {
                return
            }

            itemView.playerName.text = player.name
            itemView.playerChipCount.text = player.chips.toString()
            itemView.playerWins.text = player.wins.toString()
            itemView.playerLosses.text = player.losses.toString()

            // Click listener
            itemView.setOnClickListener {
                listener?.onPlayerSelected(snapshot)
            }
        }
    }
}
