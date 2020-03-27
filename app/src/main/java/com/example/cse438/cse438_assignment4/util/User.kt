package com.example.cse438.cse438_assignment4.util

import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class User(var wins: Int = 0, var chips: Int = 0, var name: String = "", var id: String = "", var losses: Int = 0){
    fun updatDatabase(){
        val db = Firebase.firestore
        val userRef = db.collection("users").document(this.id)
        userRef.set(this, SetOptions.merge())
            .addOnSuccessListener {  Log.d("TAG", "User Updated") }
            .addOnFailureListener{e -> Log.w("TAG", "ERROR", e)}
    }
}
