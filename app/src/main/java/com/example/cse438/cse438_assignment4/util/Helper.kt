package com.example.cse438.cse438_assignment4.util

import android.content.Context


//Returns the card name ex: clubs10, spades_queen
fun getCardName(context: Context, id: Int):String{
    val name = context.resources.getResourceEntryName(id)
    return name
}

//Returns a formatted card name ex: 10 of Clubs, Queen of Spades
fun getFormattedCardName(context: Context, id: Int):String{
    var unformattedName = getCardName(context, id)

    var suitRegex = "[a-z]+".toRegex()
    var value: String
    var suitTemp = suitRegex.find(unformattedName)!!
    var suit = suitTemp.value.capitalize()

    if(unformattedName.contains("_")){
        value = suitTemp.next()!!.value.capitalize()
    }
    else{
        val valueRegex = "[0-9]+".toRegex()
        var valueTemp = valueRegex.find(unformattedName)!!
        value = valueTemp.value
    }

    var name= value + " of " + suit

    return name
}

//Maps cards to values and returns the card value
fun getCardValue(context: Context, id: Int): Int{

    var unformattedName = getCardName(context, id)

    var suitRegex = "[a-z]+".toRegex()
    var value: String
    var suitTemp = suitRegex.find(unformattedName)!!
    var suit = suitTemp.value.capitalize()

    if(unformattedName.contains("_")){
        value = suitTemp.next()!!.value.capitalize()
    }
    else{
        val valueRegex = "[0-9]+".toRegex()
        var valueTemp = valueRegex.find(unformattedName)!!
        value = valueTemp.value
    }

    when(value){
        "Ace" -> return 1
        "2" -> return 2
        "3" -> return 3
        "4" -> return 4
        "5" -> return 5
        "6" -> return 6
        "7" -> return 7
        "8" -> return 8
        "9" -> return 9
        "10" -> return 10
        "Jack" -> return 10
        "Queen" -> return 10
        "King" -> return 10

    }
    return 0
}

//Returns the hand values under 21 sepearated by a "/" ex.) 21, 9/20
fun formatHandValues(hand: Hand): String{
    var str=""
    var max = hand.handValues.size-1
    for(x in 0..max){//For each handValue
        var temp = hand.handValues[x]
        if(x == 0){//if first value in the hand, add value to string
            str += temp.toString()
        }
        else if(temp < 22) {//Otherwise add to string with slash if under 22
            str += "/" +temp.toString()
        }

    }
    return str
}
