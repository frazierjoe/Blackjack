package com.example.cse438.cse438_assignment4.util

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Player(
    var name: String? = null,
    var chips: Int? = 0,
    var wins: Int? = 0,
    var losses: Int? = 0,
    var pushes: Int? = 0
) {

    companion object {

        const val FIELD_NAME = "name"
        const val FIELD_CHIPS = "chips"
        const val FIELD_WINS = "wins"
        const val FIELD_LOSSES = "losses"
        const val FIELD_PUSHES = "pushes"
    }
}
