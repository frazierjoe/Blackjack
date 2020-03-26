package com.example.cse438.cse438_assignment4.adapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import com.example.cse438.cse438_assignment4.R
//import androidx.recyclerview.widget.RecyclerView
//import java.util.zip.Inflater
//
//class ScoreboardViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
//    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_scoreboardentry, parent, false)){
//
//    fun bind(player : Player){
//
//    }
//}
//
//class ScoreboardAdapter (private val player: Player) : RecyclerView.Adapter<ScoreboardViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreboardViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        return ScoreboardViewHolder(inflater, parent)
//    }
//
//    //bind the object
//    override fun onBindViewHolder(holder: ScoreboardViewHolder, position: Int) {
//        val event: Playlist = listEvents!!.get(position)
//        holder.bind(event)
//    }
//}