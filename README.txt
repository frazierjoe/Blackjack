In this file you should include:

Any information you think we should know about your submission
* Is there anything that doesn't work? Why?
* Is there anything that you did that you feel might be unclear? Explain it here.
Everything should work.

Instructions:
Double tap to hit.
Longhold to stay.

A description of the creative portion of the assignment
* Describe your feature
* Why did you choose this feature?
* How did you implement it?

Features
* User chip count is updated in database after they bet. Limits the user from quitting the app to not lose chips. Implemented by updateing the database when the user clicks the bet button
* Profile Page. User can click the profile button to view their stats, edit their name, email, password, logout or delete their account. We decided to create this feature to learn more about what FirebaseAuth and Firestore can do, and to allow the user to have more control over their account. To implement this feature, we first created a profile activity, which gets  user data via an intent from main activity and and FirebaseAuth. It then displays the profile data on the page and gets the player email from FirebaseAuth. The user then has the option to edit their display name, email and set a new password they can save these changes by pressing the save changes button. When clicked this button prompts the user to reauthorize by entering their password. If the password is correct the information will be updated and this new information will be displayed. The user also has the option to delete their account which prompts the user to reauthorize then deletes the user from both the database and authorization. Finally, the user can logout by clicking the logout button.


