package com.example.cse438.cse438_assignment4.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.adapters.ScoreboardAdapter
import com.example.cse438.cse438_assignment4.util.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.activity_scoreboard.*


class ScoreboardActivity : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query
    lateinit var adapter : ScoreboardAdapter
    lateinit var recycler : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        firestore = FirebaseFirestore.getInstance()
        query = firestore.collection("users")
            .orderBy("chips", Query.Direction.DESCENDING)
            .limit(20)
        var playerList: ArrayList<User> = ArrayList()
        firestore.collection("users").get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d("BLAH", document.toString())
                playerList.add(document.toObject<User>())
                recycler = playersRecyclerView
                adapter = ScoreboardAdapter(playerList)
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(this)
            }
        }.addOnFailureListener { exception -> Log.w("TAG", "ERROR", exception) }
    }
}