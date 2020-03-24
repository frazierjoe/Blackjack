package com.example.cse438.cse438_assignment4

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.ViewModelProviders
import com.example.cse438.cse438_assignment4.fragments.BetFragment
import com.example.cse438.cse438_assignment4.util.Game
import com.example.cse438.cse438_assignment4.util.formatHandValues
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query



private const val DEBUG_TAG = "Gestures"
class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    private var betPlaced = false
    private lateinit var viewModel: MainActivityViewModel
    lateinit var firestore: FirebaseFirestore
    lateinit var query: Query
    var game = Game()
    private lateinit var mDetector: GestureDetectorCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)


//        firestore = FirebaseFirestore.getInstance()
//
//        query = firestore.collection("players")
//            .orderBy("chipCount", Query.Direction.DESCENDING)
//            .limit(20)
        mDetector = GestureDetectorCompat(this, this)

        mDetector.setOnDoubleTapListener(this)
//        mDetector.setOnDoubleTapListener(this)

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
        startActivityForResult(intent, RC_SIGN_IN)
        viewModel.isSigningIn = true
    }

    companion object {

        private const val TAG = "MainActivity"

        private const val RC_SIGN_IN = 9002

        private const val LIMIT = 20
    }

    fun startGame(){
        clearGame()
        betPlaced=false
        game = Game()
        game.newGame(this, 8)

        dealInitialCards(game)

        requestBet(game)

        updatePlayerValue()
    }

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
        if(dealerFirstCard){
            imageView.setBackgroundResource(R.drawable.back)
        }
        else {
            imageView.setBackgroundResource(cardId)
        }
    }

    fun clearGame(){
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

    fun dealInitialCards(game: Game){
        //Deal initial cards
        var isPlayer:Boolean
        for(x in 0..3){
            if (x%2 == 0) {
                isPlayer = true
                dealCard(isPlayer,false)
                //TODO animate card movement
            }
            else {
                //TODO animate card movement
                isPlayer = false
                dealCard(isPlayer, false)
            }
        }
    }
    fun dealerTurn(){
        game.dealerHand.updateHandValue(this)
        var isPlayerTurn = false
        var isDealerTurn = true
        //flip card
        flipDealerCard()
        if(game.dealerHand.handValues[0] == 21){ //If dealer dealt 21 they win
            playerLost()
        }
        while(game.dealerHand.handValues[0] <= 17){//If under 18 hit
            dealCard(isPlayerTurn, isDealerTurn)
        }
        findWinner()
    }
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
        if(maxDealer >= maxPlayer){
            playerLost()
        }
        else{
            playerWon()
        }
    }
    fun flipDealerCard(){
        var parent: LinearLayout = findViewById(R.id.dealer)
        var cardId = game.dealerHand.cardList[1]
        var imageView = parent.getChildAt(1)
        imageView.setBackgroundResource(cardId)
    }
    fun requestBet(game: Game): Int{
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = BetFragment()
        fragmentTransaction.add(R.id.bet_fragment_container, fragment)
        fragmentTransaction.commit()
        var bet = 0
        return bet
    }

    fun betCallback(bet: Int){
        betPlaced=true
        //TODO UPDATE CHIPS
    }

    fun updatePlayerValue(){
        var textBox = findViewById<TextView>(R.id.value)
        game.playerHand.updateHandValue(this)
        var playerValue = formatHandValues(game.playerHand)
        textBox.text = playerValue
        //TODO CHECK VALUES
        checkIfPlayerBust()
    }
    fun checkIfPlayerBust(){
        if(game.playerHand.handValues[0] > 21){ //If player lost
            playerLost()
        }
    }

    fun playerLost(){
        Toast.makeText(this, "You lost, new game starts in 5 seconds", Toast.LENGTH_LONG).show() //Alert them
        //TODO add loss to player stats, update the stats bar (Chips should already be removed from when they bet)
        var playerLost = true
        flipDealerCard()
        Handler().postDelayed(this::startGame, 5000)
    }
    fun playerWon(){
        Toast.makeText(this, "You won! New game starts in 5 seconds", Toast.LENGTH_LONG).show() //Alert them
        //TODO add win to player stats, update the stats bar, add chips
        Handler().postDelayed(this::startGame, 5000)
    }

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
        dealerTurn()
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
