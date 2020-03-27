package com.example.cse438.cse438_assignment4.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.util.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var email: String
        val currentUser =FirebaseAuth.getInstance().currentUser
        currentUser?.let{
            email = currentUser.email.toString()
            profileEmail.hint = email

        }



        var userName = intent.getStringExtra("userName")
        var userId = intent.getStringExtra("userId")
        var userChips = intent.getStringExtra("userChips").toInt()
        var userWins = intent.getStringExtra("userWins").toInt()
        var userLosses = intent.getStringExtra("userLosses").toInt()
        var user = User(userWins, userChips, userName, userId, userLosses)
        profileName.hint = userName.toString()
        profileWins.text = userWins.toString()
        profileLosses.text = userLosses.toString()
        profileChips.text = userChips.toString()
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance()
            .signOut()
        intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun deleteProfile(view: View){
        var email =""
        val currentUser =FirebaseAuth.getInstance().currentUser
        currentUser?.let{
            email = currentUser.email.toString()
        }
        var password = profileNewPassword.text.toString()
        if(password != ""){
            val user = FirebaseAuth.getInstance().currentUser
            val credential = EmailAuthProvider
                .getCredential(email, password)
            user?.reauthenticate(credential)
                ?.addOnCompleteListener {
                    user?.delete()
                        ?.addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                intent = Intent(this, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                            else{
                                Log.d("blah", "error")
                            }
                        }

                }
                ?.addOnFailureListener{
                    Toast.makeText(this, "Incorrect password.", Toast.LENGTH_LONG).show()
                }

        }
        else{
            passwordText.text = "Password"
            Toast.makeText(this,"Enter your password in the password field to delete your account", Toast.LENGTH_LONG).show()
        }

    }

    fun updateProfile(view: View){
        var email =""
        val currentUser =FirebaseAuth.getInstance().currentUser
        currentUser?.let{
            email = currentUser.email.toString()
        }
        if (profileName.text.toString() != ""){
            Log.d("BLAH", profileName.text.toString())
        }
        if (profileNewPassword.text.toString() != ""){
            Log.d("blah", profileNewPassword.text.toString())
        }
        if(profileEmail.text.toString() != ""){
                Log.d("blah", profileEmail.text.toString())
        }
    }

//    fun updateName(){
//
//    }
//    fun updateEmail(){
//
//    }
//    fun updatePassword(){
//
//    }
}