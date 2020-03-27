# Android Blackjack App

## The goal of this project was to:
* Create a scalable app utilizing Firebase Authentication and Cloud Firestore.
* Learn to use gestures for user input.
* Learn to create basic animations.
* Create a persistent login (users can leave and/or close the app and remain signed in.)

<img src="/BlackjackPics/InitialDeal.png" alt="Home" width="200px"/>

## Login
* Enter email and password to sign in.
* If user forgets their password they can request an email to be sent to them to update their password.

<img src="/BlackjackPics/Login.png" alt="Login" width="200px"/>
<img src="/BlackjackPics/Password.png" alt="Password" width="200px"/>


## Create Account
* Enter email, name and password.

<img src="/BlackjackPics/Login.gif" alt="Register" width="200px"/>

## Play

<img src="/BlackjackPics/Main.png" alt="Main" width="200px"/>

* Enter a bet.

<img src="/BlackjackPics/EnterBet.png" alt="Login" width="200px"/>

* After a bet is entered, user chips are updated and initial cards are dealt.

<img src="/BlackjackPics/InitialDeal.png" alt="Initial Deal" width="200px"/>

* Double tap to hit.
* Swipe right or long press to stay.

<img src="/BlackjackPics/Hit.png" alt="User hit" width="200px"/>
<img src="/BlackjackPics/Push.png" alt="Push" width="200px"/>
<img src="/BlackjackPics/Win.png" alt="User won" width="200px"/>

* Click to rebet button to quickly bet your previous bet.
* When a blackjack occurs, the game ends and user is notified.

<img src="/BlackjackPics/Blackjack.png" alt="User blackjack" width="200px"/>

## Leaderboard
* Displays the top 20 users by chip count in a RecyclerView.

<img src="/BlackjackPics/Leaderboard.png" alt="Leaderboard" width="200px"/>


## Profile
* Displays user name, chips, wins, losses and email.

<img src="/BlackjackPics/Profile.png" alt="Profile page" width="200px"/>

* Users can update their name, email and password but must be reauthenticated in order to do so.

<img src="/BlackjackPics/Reauthorize.png" alt="ReAuth" width="200px"/>

* When an email is updated, a notification is sent to the users previous email to notify them of the change.

<img src="/BlackjackPics/ProfileUpdated.png" alt="Updated" width="200px"/>

* Users can logout from here or from the main activity.

* Users can delete their account (deletes from both Firebase Authentication and Firestore so they will be removed from the leaderboard.) After being deleted, the user is taken back to the login page.

<img src="/BlackjackPics/Deleted.png" alt="Deleted account" width="200px"/>

* Firebase handles checking user passwords and emails by throwing an exception if the password doesn't meet their criteria or if the email isn't valid.

<img src="/BlackjackPics/BadPassword.png" alt="Bad Password" width="200px"/>

## Future Plans

* Add popup instructions and errors(currently uses toasts to display errors.)
* Improve animations.
* Add more sign-in methods (Google, Facebooke, Twitter.)

_This project was created in collaboration with Jacob Gilhaus as a project for CSE-438._

