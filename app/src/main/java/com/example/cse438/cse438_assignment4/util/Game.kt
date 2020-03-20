package com.example.cse438.cse438_assignment4.util

import android.content.Context
import android.widget.Toast
import com.example.cse438.cse438_assignment4.MainActivity


data class Game(var deck: Deck = Deck(), var dealerHand: Hand = Hand(), var playerHand: Hand = Hand()){



    //Get new decks, deal initial cards
    fun newGame(context: Context, numberOfDecks: Int){
        deck.newDecks(context, numberOfDecks)
        for(x in 0..3){
            if (x%2 == 0) dealCard(context, playerHand)
            else dealCard(context, dealerHand)
        }

    }


    fun dealCard(context: Context, player: Hand){
            var card = deck.getCard()
            player.cardList.add(card)
            player.updateHandValue(context)
            if (player == playerHand){
                //Move card to player hand

            }
            else{
                //Move card to dealer hand
            }
            //TODO Update dislay and animations
    }


}