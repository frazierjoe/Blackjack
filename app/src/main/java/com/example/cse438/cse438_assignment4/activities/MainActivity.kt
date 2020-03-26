package com.example.cse438.cse438_assignment4.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProviders
import com.example.cse438.cse438_assignment4.viewmodels.MainActivityViewModel
import com.example.cse438.cse438_assignment4.R
import com.example.cse438.cse438_assignment4.fragments.BetFragment
import com.example.cse438.cse438_assignment4.util.Game
import com.example.cse438.cse438_assignment4.util.formatHandValues
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private var betPlaced = false
    private var bet : Int = 0
    private var curBet : Int = 0
    private var chipCount : Int = 1000 //if this is changed, also update strings file for chip_placeholder
    private var Ws : Int = 0 // same as above
    private var Ls : Int = 0 // same as above
    private lateinit var viewModel: MainActivityViewModel
    var game = Game()
    private lateinit var mDetector: GestureDetectorCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        //Gestures
        mDetector = GestureDetectorCompat(this, this)
        mDetector.setOnDoubleTapListener(this)

        startGame()
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
        startActivityForResult(intent,
            RC_SIGN_IN
        )
        viewModel.isSigningIn = true
    }

    companion object {

        private const val TAG = "MainActivity"

        private const val RC_SIGN_IN = 9002

        private const val LIMIT = 20

        private const val DEBUG_TAG = "Gestures"
    }


    //Set initial game conditions
    fun startGame(){
        resetImages()
        betPlaced=false
        game = Game()
        game.newGame(this, 8) //Generate deck
        requestBet(game)
    }

    //Initialize cards to be transparent
    fun resetImages(){
        var parent: LinearLayout = findViewById(R.id.player)
        for(x in 0..13){
            var imageView = parent.getChildAt(x)
            imageView.setBackgroundResource(android.R.color.transparent)
        }
        parent = findViewById(R.id.dealer)
        for(x in 0..13){
            var imageView = parent.getChildAt(x)
            imageView.setBackgroundResource(android.R.color.transparent)
        }
    }

    //Deal the initial 4 cards
    fun dealInitialCards(){
        dealCard(true,false)
        dealCard(false, false)
        dealCard(true,false)
        dealCard(false, false)
    }

    //Start bet activity so user can input a bet
    fun requestBet(game: Game): Int{
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = BetFragment()
        var bundle = Bundle()
        bundle.putInt("chip count", chipCount)
        fragment.arguments = bundle
        fragmentTransaction.add(R.id.bet_fragment_container, fragment)
        fragmentTransaction.commit()
        return bet
    }

    //Display the players hand value
    fun updatePlayerValue(){
        var textBox = findViewById<TextView>(R.id.value)
        var playerValue = formatHandValues(game.playerHand)
        textBox.text = playerValue
        checkIfPlayerBust()
    }

    //Adds card to player hand object and calls setBackgroundImage() to update hand view
    fun dealCard(isPlayer: Boolean, isDealerTurn: Boolean){
        var cardId: Int
        if(isPlayer){
            cardId = game.dealCard(this, game.playerHand)
        }
        else{
            cardId = game.dealCard(this, game.dealerHand)
        }
        if(!isDealerTurn) {
            setBackgroundImage(isPlayer, game, cardId, false)
        }
        else setBackgroundImage(isPlayer, game, cardId, true)
    }

    //Get card id and update the hand display
    fun setBackgroundImage(isPlayer: Boolean, game: Game, cardId: Int, isDealerTurn: Boolean){
        var viewId: Int
        var parent: LinearLayout
        var dealerFirstCard = false
        if(isPlayer){
            viewId = game.playerHand.cardList.size-1
            parent = findViewById(R.id.player)
        }
        else{
            viewId = game.dealerHand.cardList.size-1
            parent = findViewById(R.id.dealer)
            if(viewId == 1 && !isDealerTurn) {
                dealerFirstCard = true
            }
        }
        var imageView = parent.getChildAt(viewId)

        var animatedView = ImageView(this)
        var layout = findViewById<ConstraintLayout>(R.id.body)
        layout.addView(animatedView)
        animatedView.layoutParams.height = 205
        animatedView.layoutParams.width = 150
        animatedView.x = 465F
        animatedView.y = 420F
        animatedView.setBackgroundResource(R.drawable.back)

        animatedView.bringToFront()

        var moveX : Float = 0.0f
        var moveY : Float = 0.0f

        if(isPlayer){
            moveX = 150f
            moveY = 1000f
        }else{
            moveX = 150f
            moveY = 700f
        }


        var animation1 = ObjectAnimator.ofFloat(animatedView, "translationX", moveX).apply{
            duration = 500
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if(dealerFirstCard){
                        imageView.setBackgroundResource(R.drawable.back)
                    }
                    else {
                        imageView.setBackgroundResource(cardId)
                    }
                }
            })
        }
        var animation2 = ObjectAnimator.ofFloat(animatedView, "translationY", moveY).apply{ duration=500}
        var hide = ObjectAnimator.ofFloat(animatedView, "alpha", 1f, 0f).apply{duration=20}
        AnimatorSet().apply{
            play(animation1).with(animation2)
            play(hide).after(animation1)
            start()
        }
        onEnterAnimationComplete()
    }

    //Logic for the dealer's turn
    fun dealerTurn(){
        var isPlayerTurn = false
        var isDealerTurn = true
        //flip card
        flipDealerCard()

        // if no condition is met, dealer has no need to hit
        // if dealer has ace (as 11), must hit 17, stays at 18 and up
        // with no ace, dealer hits until 17 or higher
        //check that dealer still has playable hand to avoid inf loop
        while(game.dealerHand.playableHands > 0 && (game.dealerHand.bestHand < 17 || (game.dealerHand.bestHand == 17 && game.dealerHand.playableHands > 1))) {
            if (game.dealerHand.numAces > 0) {
                if (game.dealerHand.bestHand < 17) {
                    dealCard(isPlayerTurn, isDealerTurn)
                } else if (game.dealerHand.bestHand == 17 && game.dealerHand.playableHands > 1) {
                    dealCard(isPlayerTurn, isDealerTurn)
                } else {
                    break
                }
            } else {
                dealCard(isPlayerTurn, isDealerTurn)
            }
        }
        //dealer is done
        betPlaced = false
        findWinner()
    }

    //Compute who won
    fun findWinner(){
        var maxDealer = 0
        var maxPlayer = 0
        for(x in 0..game.playerHand.handValues.size-1){
            if(game.playerHand.handValues[x] <= 21) {
                maxPlayer = game.playerHand.handValues[x]
            }
        }
        for(y in 0..game.dealerHand.handValues.size-1){
            if(game.dealerHand.handValues[y]<=21){
                maxDealer = game.dealerHand.handValues[y]
            }
        }

        if(maxDealer > maxPlayer){
            playerLost()
        }
        else if(maxDealer < maxPlayer){
            playerWon()
        }
        else{
            playerPush()
        }
    }

    //Flip the dealers card that was initially face down
    fun flipDealerCard(){
        var parent: LinearLayout = findViewById(R.id.dealer)
        var cardId = game.dealerHand.cardList[1]
        var imageView = parent.getChildAt(1)
        imageView.setBackgroundResource(cardId)
    }

    //gets called when the bet fragment is closed (when a bet is placed)
    fun betCallback(bet2: Int){
        betPlaced=true
        curBet = bet2
        chipCount = chipCount - bet2
        chips.text = chipCount.toString()
        dealInitialCards()
        updatePlayerValue()

        //if either has blackjack, hand over
        if(game.dealerHand.bestHand == 21 || game.playerHand.bestHand == 21){
            betPlaced = false
            findWinner()
        }
        //TODO UPDATE CHIPS
    }

    //Checks if player is over 21
    fun checkIfPlayerBust(){
        if(game.playerHand.handValues[0] > 21){ //If player lost
            playerLost()
        }
    }

    //Alert the player that they won, show dealer's cards
    fun playerLost(){
        if(game.dealerHand.cardList.size == 2 && game.dealerHand.bestHand == 21){
            Toast.makeText(this, "Dealer Blackjack, new hand in 5 seconds", Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this, "You lost, new hand starts in 5 seconds", Toast.LENGTH_LONG).show() //Alert them
        }

        //TODO add loss to player stats, update the stats bar (Chips should already be removed from when they bet)
        ++Ls
        losses.text = Ls.toString()
        flipDealerCard()
        Handler().postDelayed(this::startGame, 5000)
    }

    //Alert the player that they won
    fun playerWon(){
        if(game.playerHand.cardList.size == 2 && game.playerHand.bestHand == 21){
            Toast.makeText(this, "BLACKJACK! Winner winner, chicken dinner!", Toast.LENGTH_LONG).show()
            chipCount= (chipCount + 2*curBet + curBet/2)
        }
        else {
            Toast.makeText(this, "You won! New hand starts in 5 seconds", Toast.LENGTH_LONG).show() //Alert them
            chipCount= (chipCount + 2*curBet)
        }

        //TODO add win to player stats, update the stats bar, add chips
        ++Ws
        wins.text = Ws.toString()
        chips.text = chipCount.toString()
        Handler().postDelayed(this::startGame, 5000)
    }

    //Alert the player that they pushed, had same value as dealer
    fun playerPush(){
        Toast.makeText(this, "Push. New hand starts in 5 seconds", Toast.LENGTH_LONG).show() //Alert them
        //TODO add push to player stats, update the stats bar, add chips
        chipCount= (chipCount + curBet)
        chips.text = chipCount.toString()
        Handler().postDelayed(this::startGame, 5000)
    }

    fun leaderboard(view: View){
        var leadIntent = Intent(this, ScoreboardActivity::class.java)
        startActivity(leadIntent)
    }


    //Motion Events below
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        return true
    }

    override fun onLongPress(event: MotionEvent) {
        if(betPlaced) {
            dealerTurn()
        }
        else{
            Toast.makeText(this, "Must place a bet before you can proceed", Toast.LENGTH_LONG).show()
        }
        Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        if(betPlaced) {
            if (game.playerHand.handValues[0] < 22) {
                dealCard(true, false)
                updatePlayerValue()
            }
        }
        else{
            Toast.makeText(this, "Must place a bet before you can proceed", Toast.LENGTH_LONG).show()
        }
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }

}
