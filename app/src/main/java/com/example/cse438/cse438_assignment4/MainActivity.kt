package com.example.cse438.cse438_assignment4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

//        firestore = FirebaseFirestore.getInstance()
//
//        query = firestore.collection("players")
//            .orderBy("chipCount", Query.Direction.DESCENDING)
//            .limit(20)
    }

    public override fun onStart() {
        super.onStart()

        // Start sign in if necessary
        if (shouldStartSignIn()) {
            startSignIn()
            return
        }
    }

    private fun shouldStartSignIn(): Boolean {
        return !viewModel.isSigningIn && FirebaseAuth.getInstance().currentUser == null
    }

    private fun startSignIn() {
        val intent = AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders((listOf(AuthUI.IdpConfig.EmailBuilder().build())))
            .setIsSmartLockEnabled(false)
            .build()
        startActivityForResult(intent, RC_SIGN_IN)
        viewModel.isSigningIn = true
    }

    companion object {

        private const val TAG = "MainActivity"

        private const val RC_SIGN_IN = 9002

        private const val LIMIT = 20
    }
}
