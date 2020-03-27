# Android Blackjack App

__This project was created in collaboration with Jacob Gilhaus as a project for CSE-438__

## The goal of this project was to:
* Create a scalable app utilizing Firebase Authentication and Cloud Firestore
* Use gestures for user input
* Learn to create basic animations
* Create a persistent login (users can leave and/or close the app and remain signed in)


## Login
* Enter email and password to sign in
* If user forgets their password they can request an email to be sent to them to update their password
LOGIN
PASSWORD

## Create Account
* Enter email, name and password
REGISTER

## Play
MAIN
* Enter a bet
BET
* After a bet is entered, user chips are updated and initial cards are dealt
INITIALDEAL
* Double tap to hit
* Swipe right or long press to stay
HIT PUSH WIN

* Click to rebet button to quickly bet your previous bet
* When a blackjack occurs, the game ends and user is notified
BLACKJACK

## Leaderboard
* Displays the top 20 users by chip count in a RecyclerView
LEADERBOARD


## Profile
* Displays user name, chips, wins, losses and email.
PROFILE
* Users can update their name, email and password but must be reauthenticated in order to do so

REAUTHENTICATE
* When an email is updated, a notification is sent to the users previous email to notify them of the change.
* Users can logout from here or from the main activity
* Users can delete their account (deletes from both Firebase Authentication and Firestore so they will be removed from the leaderboard.)
DELETED

* Firebase handles checking user passwords and emails by throwing an exception if the password doesn't meet their criteria or if the email isn't valid.
BAD PASSWORD

## Minor Features
* Implemented blackjack to the best of our knowledge

## Future Plans
* Add popup instructions and errors(currently uses toasts to display errors)
* Improve animations
* Add more sign-in methods (Google, Facebooke, Twitter)

