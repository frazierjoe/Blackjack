package com.example.cse438.cse438_assignment4.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.viewmodels.MainActivityViewModel
import com.google.firebase.example.fireeats.kotlin.adapter.ScoreboardAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_scoreboard.*

class ScoreboardActivity : AppCompatActivity(),
    ScoreboardAdapter.OnPlayerSelectedListener {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_scoreboard)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        firestore = FirebaseFirestore.getInstance()

        query = firestore.collection("players")
            .orderBy("chipCount", Query.Direction.DESCENDING)
            .limit(20)

        // RecyclerView
        adapter = object : ScoreboardAdapter(query, this@ScoreboardActivity) {
            override fun onDataChanged() {
                // Show/hide content if the query returns empty.
                if (itemCount == 0) {
                    playersRecyclerView.visibility = View.GONE
                    viewEmpty.visibility = View.VISIBLE
                } else {
                    playersRecyclerView.visibility = View.VISIBLE
                    viewEmpty.visibility = View.GONE
                }
            }

            override fun onError(e: FirebaseFirestoreException) {
                // Show a snackbar on errors
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error: check logs for info.", Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

}