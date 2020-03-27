In this file you should include:

Any information you think we should know about your submission
* Is there anything that doesn't work? Why?
* Is there anything that you did that you feel might be unclear? Explain it here.

Everything should work. We also made some minor changes to the rules described in the document to make the game more like blackjack, described below.

Instructions:
Double tap to hit.
Swipe right OR Longhold to stay.

A description of the creative portion of the assignment
* Describe your feature
* Why did you choose this feature?
* How did you implement it?

Features
* User chip count is updated in database after they bet. Limits the user from quitting the app to not lose chips. Implemented by updating the database when the user clicks the bet button. Also, users can only play until chips run out as in a real blackjack game. Potentially implementing a buyback option was considered but shelved in favor of other features for now. Best option is to delete account and make a new one as explained below.
* Profile Page. User can click the profile button to view their stats, edit their name, email, password, logout or delete their account. We decided to create this feature to learn more about what FirebaseAuth and Firestore can do, and to allow the user to have more control over their account. To implement this feature, we first created a profile activity, which gets user data via an intent from main activity and and FirebaseAuth. It then displays the profile data on the page and gets the player email from FirebaseAuth. The user then has the option to edit their display name, email and set a new password they can save these changes by pressing the save changes button. When clicked this button prompts the user to reauthorize by entering their old password. If the password is correct the information will be updated and this new information will be displayed. The user also has the option to delete their account which prompts the user to reauthorize then deletes the user from both the database and authorization. Finally, the user can logout by clicking the logout button.
* Rebet button added to make the game easier for the user. Now, instead on needing to input 100 chips each time the user wants to play, they can simply choose rebet and the app will rebet the most recent amount. The app still checks to make sure that the user has enough chips to do so, and calls the same function as the bet button with a new button id and a different sequence of checks.
* Additional blackjack functionality was added to give the app a feel much closer to a true game of blackjack. The original requirements had the dealer stop at 17, but in most blackjack games the dealer will still hit on soft 17 (that is with an ace acting as an 11). This actually gives the casino slightly more of an advantage, so we implemented this rule as well in the dealer logic function. Also, in the event of the dealer and user both having the same value, pushes are returned as an additional outcome. Pushes are not displayed in the leaderboard (they could be, but they don't add much). In addition, blackjack is called right off the deal. If the user or dealer gets blackjack on the initial deal, the hand stops and alerts the user to which player has blackjack with an appropriate message. These could also be recorded as a stat but are mostly luck, so they are not. Finally, getting blackjack as a user usually returns a higher value than what was bet to move the odds back towards the player. We made blackjacks payout 3:2 (so for a bet of 10, the user gets 25 back, a net gain of 15 chips). Also, in most blackjack games, bets are placed before the deal, so we operated on that condition as well. We did not implement doubling down and splitting, but hope to do so in the future to improve the app even more.
Have fun and good luck! I know I (Jacob) played this quite a bit!
