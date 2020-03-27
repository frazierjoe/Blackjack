package com.example.cse438.cse438_assignment4.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse438.cse438_assignment4.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity: AppCompatActivity(){
    private val RC_SIGN_IN = 123
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        if(currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        else{
            showSignInOptions()
        }
    }

    fun showSignInOptions(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            var response = IdpResponse.fromResultIntent(data)
            if (resultCode == RESULT_OK) {
                var currentUser = FirebaseAuth.getInstance().currentUser
                var userId = currentUser!!.uid
                var userName = currentUser!!.displayName
                val db = Firebase.firestore
                val user = hashMapOf("id" to userId, "name" to userName, "chips" to 5000, "wins" to 0, "losses" to 0)

                val userRef = db.collection("users").document(userId)
                userRef.get()
                    .addOnSuccessListener { document ->
                        if(document.data != null){
                            Log.d("TAG", "USER ALREADY EXISTS: ${document.data}")
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        } else{
                            userRef.set(user)
                                .addOnSuccessListener {  Log.d("TAG", "Success")
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                                .addOnFailureListener{e -> Log.w("TAG", "ERROR", e)}
                        }
                    }.addOnFailureListener{e -> Log.w("TAG", "Failed to get doc", e)}

            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_LONG).show()
                showSignInOptions()
            }

        }

    }
}