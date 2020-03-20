package com.example.cse438.cse438_assignment4.util

import android.content.Context


data class Hand(var cardList: ArrayList<Int> = ArrayList(), var handValue: Int = 0){
    fun updateHandValue(context: Context){
        var tempHandValue=0
        for(id in cardList){
            tempHandValue += getCardValue(context,id)
        }
        handValue = tempHandValue
    }
}