package com.example.cse438.cse438_assignment4.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cse438.cse438_assignment4.MainActivity
import com.example.cse438.cse438_assignment4.R
import kotlinx.android.synthetic.main.fragment_bet.*



class BetFragment : Fragment(), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_bet, container, false)
        val btn: Button = view.findViewById(R.id.submit_bet_btn)
        btn.setOnClickListener(this)
        return view

    }

    //Submits bet and calls the betCallback in MainActivity
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submit_bet_btn -> {
                var bet = bet_input.text.toString()
                if (bet != "") {
                    var betNum = bet.toInt()
                    (activity as MainActivity?)!!.betCallback(betNum)
                    activity!!.supportFragmentManager.beginTransaction().remove(this).commit()

                } else {
                    Toast.makeText(
                        getContext(),
                        "Enter a valid number of chips.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

}


