package com.example.cse438.cse438_assignment4.util

import android.content.Context


data class Hand(var cardList: ArrayList<Int> = ArrayList(), var handValues: ArrayList<Int> = ArrayList()){

    //Calculates possible values and updates the handValues arrayList
    fun updateHandValue(context: Context){
        var tempHandValue=0
        var numAces= 0
        var tempArray: ArrayList<Int> = ArrayList()
        for(id in cardList){ //Loop through cards in the hand
            if(getCardValue(context, id)==1){ //If card is an aces, increase num aces
                ++numAces
            }
            tempHandValue += getCardValue(context,id) //Add up the total value of cards

        }
        for(x in 0..numAces){ //For each ace, add the aces highvalue to the array of hand values
            var newValue = tempHandValue + (x*10)
            tempArray.add(newValue)
        }
        handValues = tempArray
    }
}