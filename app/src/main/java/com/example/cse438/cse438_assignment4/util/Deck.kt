package com.example.cse438.cse438_assignment4.util

import android.content.Context
import kotlin.collections.ArrayList

data class Deck(var cardList: ArrayList<Int> = ArrayList()){

    //Recreated the cardList with numberOfDecks decks
    fun newDecks(context: Context, numberOfDecks: Int){
        val randomizer = CardRandomizer()
        cardList = randomizer.getIDs(context)
        for(x in 2..numberOfDecks){
            var newDeck = randomizer.getIDs(context)
            cardList.addAll(newDeck)
        }
    }

    //Return a random card from the deck
    fun getCard(): Int{
        var numberOfCardsLeft = cardList.size
        var randomCardIndex = (0..numberOfCardsLeft-1).random()
        val nextCard = cardList[randomCardIndex]
        cardList.removeAt(randomCardIndex)
        return nextCard
    }


}

