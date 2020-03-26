package com.example.cse438.cse438_assignment4.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse438.cse438_assignment4.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


//abstract class ScoreboardActivity : AppCompatActivity(), ScoreboardAdapter.OnPlayerSelectedListener {
class ScoreboardActivity : AppCompatActivity() {


    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query
    //    private lateinit var viewModel: MainActivityViewModel
//    lateinit var adapter : ScoreboardAdapter

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_scoreboard)

//        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        firestore = FirebaseFirestore.getInstance()
//
//        query = firestore.collection("players")
//            .orderBy("chipCount", Query.Direction.DESCENDING)
//            .limit(20)

        firestore.collection("players").get().addOnSuccessListener { result ->
            for (document in result){
                Log.d("TAG", "${document.id} -> ${document.data}")
            }
        }.addOnFailureListener{exception -> Log.w("TAG", "ERROR", exception) }


        Toast.makeText(this, query.toString(), Toast.LENGTH_LONG).show()



        // RecyclerView
//        adapter = object : ScoreboardAdapter(query, this@ScoreboardActivity) {
//            override fun onDataChanged() {
//                // Show/hide content if the query returns empty.
//                if (getItemCount() == 0) {
//                    playersRecyclerView.visibility = View.GONE
//                } else {
//                    playersRecyclerView.visibility = View.VISIBLE
//                }
//            }
//
//            override fun onError(e: FirebaseFirestoreException) {
//                // Show a snackbar on errors
//                Toast.makeText(applicationContext, "Error: check logs for info", Toast.LENGTH_LONG).show()
////                Snackbar.make(
////                    findViewById(android.R.id.content),
////                    "Error: check logs for info.", Snackbar.LENGTH_LONG
////                ).show()
//            }
//        }
    }

}