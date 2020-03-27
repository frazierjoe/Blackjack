package com.example.cse438.cse438_assignment4.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cse438.cse438_assignment4.activities.MainActivity
import com.example.cse438.cse438_assignment4.R
import kotlinx.android.synthetic.main.fragment_bet.*

var chipC : Int? = 0
var prevBet : Int? = 0

class BetFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_bet, container, false)
        val btn: Button = view.findViewById(R.id.submit_bet_btn)
        val rbtn : Button = view.findViewById(R.id.rebet_btn)
        btn.setOnClickListener(this)
        rbtn.setOnClickListener(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chipC = arguments?.getInt("chip count")
        prevBet = arguments?.getInt("previous bet")
    }

    //Submits bet and calls the betCallback in MainActivity
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_bet_btn -> {
                var bet = bet_input.text.toString()
                if (bet != "") {
                    var betNum = bet.toInt()
                    if (betNum <= chipC!!) {
                        activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
                        (activity as MainActivity?)!!.betCallback(betNum)
                    } else {
                        Toast.makeText(
                            getContext(),
                            "Please only bet as many chips as you have (or less)",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        getContext(),
                        "Please enter a valid number of chips.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            R.id.rebet_btn -> {
                if(prevBet!! <= chipC!!) {
                    activity!!.supportFragmentManager.beginTransaction().remove(this).commit()
                    (activity as MainActivity?)!!.betCallback(prevBet!!)
                }
                else{
                    Toast.makeText(
                        getContext(),
                        "Please only bet as many chips as you have (or less)",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}


