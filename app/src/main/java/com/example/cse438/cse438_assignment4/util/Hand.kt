package com.example.cse438.cse438_assignment4.util

import android.content.Context


data class Hand(var cardList: ArrayList<Int> = ArrayList(), var handValues: ArrayList<Int> = ArrayList()){

    var numAces : Int = 0
    var bestHand : Int = 0
    var playableHands : Int = 0

    //Calculates possible values and updates the handValues arrayList
    fun updateHandValue(context: Context){
        var tempHandValue=0
        var tempArray: ArrayList<Int> = ArrayList()
        for(id in cardList){ //Loop through cards in the hand
            if(getCardValue(context, id)==1){ //If card is an aces, increase num aces
                ++numAces
            }
            tempHandValue += getCardValue(context,id) //Add up the total value of cards
        }

        for(x in 0..numAces){ //For each ace, add the aces high value to the array of hand values
            var newValue = tempHandValue + (x*10)
            tempArray.add(newValue)
        }

        //find the best hand for dealer logic, along with playable hands for aces
        bestHand = 0
        playableHands = 0
        for (x in 0..tempArray.size - 1) {
            if (tempArray[x] < 22){
                ++playableHands
            }
            if (tempArray[x] < 22 && tempArray[x] > bestHand) {
                bestHand = tempArray[x]
            }
        }
        //reset the hand to the array of new values since new card is dealt
        handValues = tempArray
    }
}