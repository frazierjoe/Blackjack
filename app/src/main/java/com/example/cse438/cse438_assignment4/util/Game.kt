package com.example.cse438.cse438_assignment4.util

import android.content.Context
import android.widget.Toast
import com.example.cse438.cse438_assignment4.MainActivity
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.fragments.BetFragment


data class Game(var deck: Deck = Deck(), var dealerHand: Hand = Hand(), var playerHand: Hand = Hand()){
    //Get new decks, deal initial cards
    fun newGame(context: Context, numberOfDecks: Int){
        deck.newDecks(context, numberOfDecks)
    }

    //Takes a card from the deck, placed in players hand, updates player's hand value
    //Return: Card
    fun dealCard(context: Context, player: Hand): Int{
            var card = deck.getCard()
            player.cardList.add(card)
            player.updateHandValue(context)
            return card
    }

}