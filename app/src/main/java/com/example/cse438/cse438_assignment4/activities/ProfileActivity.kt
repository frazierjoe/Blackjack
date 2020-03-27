package com.example.cse438.cse438_assignment4.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.util.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_profile.*



class ProfileActivity: AppCompatActivity(){
    lateinit var userObj: User
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
        userObj = User(userWins, userChips, userName, userId, userLosses)
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
        getCredentials(true)
    }
    fun deleteProfileCallback(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.delete()
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    userObj.deleteUser()
                    Toast.makeText(this, "Account Deleted", Toast.LENGTH_LONG).show()
                    intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                }
                else{
                    Toast.makeText(this, "Error "+task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
    fun updateProfile(view:View){
        getCredentials(false)
    }


    fun updateProfileCallback(){
        var email =""
        val currentUser =FirebaseAuth.getInstance().currentUser
        currentUser?.let{
            email = currentUser.email.toString()
        }
        var newName = profileName.text.toString()
        if (newName != ""){
            updateName(newName)
            Log.d("BLAH", profileName.text.toString())
        }
        var newPassword = profileNewPassword.text.toString()
        if ( newPassword != ""){
            updatePassword(newPassword)
            Log.d("blah", profileNewPassword.text.toString())
        }
        var newEmail =profileEmail.text.toString()
        if(newEmail != ""){
                updateEmail(newEmail)
                Log.d("blah", profileEmail.text.toString())
        }
    }

    fun getCredentials(isDeleting: Boolean){
        var email = ""
        var currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let{
            email = currentUser.email.toString()
        }
        val li = LayoutInflater.from(applicationContext)
        val promptsView: View = li.inflate(R.layout.dialog_reauth, null)
        val credentialAlertDialog = AlertDialog.Builder(this)
        credentialAlertDialog.setView(promptsView)
        var passwordTextView = promptsView.findViewById<EditText>(R.id.reauthPassword)
        credentialAlertDialog.setTitle("Reauthorize to continue")
        credentialAlertDialog.setPositiveButton("Continue"){dialog, which ->
            var password = passwordTextView.text.toString()

            Log.d("BLAH", password)
            if(password != "") {
                val credential = EmailAuthProvider
                    .getCredential(email, password!!)
                currentUser?.reauthenticate(credential)
                    ?.addOnCompleteListener {task ->
                        if(task.isSuccessful) {
                            if (isDeleting) {
                                deleteProfileCallback()
                            } else {
                                updateProfileCallback()
                            }
                            Toast.makeText(this, "Reauthorized", Toast.LENGTH_LONG).show()
                        }
                        else{
                            Toast.makeText(this, "Errpr"+task.exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                    ?.addOnFailureListener {e ->
                        Toast.makeText(this, "Error "+e.toString(), Toast.LENGTH_LONG).show()
                    }
            }
            else{
                Toast.makeText(this,"No password entered", Toast.LENGTH_LONG).show()
            }

        }
        credentialAlertDialog.setNegativeButton("Cancel"){dialog, which ->
            Toast.makeText(applicationContext, "Update Canceled", Toast.LENGTH_LONG).show()
        }
        val dialog: AlertDialog = credentialAlertDialog.create()
        dialog.show()
    }

    fun updateName(newName: String){
        val user = FirebaseAuth.getInstance().currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newName)
            .build()
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Updated Name", Toast.LENGTH_LONG).show()
                    userObj.name = newName
                    userObj.updatDatabase()
                    profileName.text.clear()
                    profileName.hint =newName

                }
                else{
                    Toast.makeText(this, "Update failed "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }



    }
    fun updateEmail(newEmail: String){
        var user  = FirebaseAuth.getInstance().currentUser
        user?.updateEmail(newEmail)
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Updated Email", Toast.LENGTH_LONG).show()
                    profileEmail.text.clear()
                    profileEmail.hint = newEmail
                }
                else{
                    Toast.makeText(this, "Update failed "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }

    }
    fun updatePassword(newPassword: String){
        val user = FirebaseAuth.getInstance().currentUser
        user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Updated Password", Toast.LENGTH_LONG).show()
                    profileNewPassword.text.clear()
                }
                else{
                    Toast.makeText(this, "Update failed "+ task.exception.toString(), Toast.LENGTH_LONG).show()
                    profileNewPassword.text.clear()
                }
            }
            ?.addOnFailureListener { e ->
                Toast.makeText(this, "Update failed " + e, Toast.LENGTH_LONG).show()
                profileNewPassword.text.clear()
            }

    }
}