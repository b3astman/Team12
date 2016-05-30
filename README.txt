PHASE 2
Team 12: Lachezar Dimov, Bethany Eastman
App: Ride On Time


PROJECT REQUIREMENTS MET
------------------------
1. Fix Bugs (see below)
2. Implemented 90% of use cases (see below)
3. Data storage with SQLite & SharedPrefences (see below)
4. Web Services (see below)
5. Content Sharing (see below)
6. Sign-in with custom accounts (use case 1 & 2)
7. Graphics - app logo
8. JUnit and Robotium tests
9. Meeting notes link (see below)


USE CASES IMPLEMENTED
---------------------
1. Logging in
- login with email: testing@uw.edu, password: testing

2. User registration.
- must register with a valid email address
- must register with a password length of 6 or more characters.

3. View all Metro King County stops (for phase 2, this will be replaced with a map, since the list of stops is quite lengthy)

4. View bus arrivals within the next 60 minutes for the stop that was selected

5. Search for a stop id (the stop id is usually on the actual physical stop sign)
- Try searching for 9138, 320 (valid bus stop ids). App displays arrivals within the next 60 minutes.
- Try searching for 9137, 9133 (invalid bus stop ids). App displays appropriate message.

6. View Favorites, the user can view their saved favorites
- In the menu bar, select "Favorite Stops" to view saved favorites

7. Add Favorites
- To add favorites, select the button on left side in "View Bus Arrivals", select
"Add to favorites" and a toast will pop up. The toast will either confirm the stop was added, otherwise the toast will state the stop was already in the user's list of favorites.

8. Remove Favorites
- To remove favorites, go to "Favorite Stops" by selecting it in the menu. Once viewing the favorites stops, select the button on the left side of the desired stop, and select the "Remove from favorites" text. A toast will appear confirming that the stop was removed.

9. Transit Alerts, the user can view RSS feed alerts for specific routes / stops.
- To view transit alerts, select "Transit alerts" from the menu


USE CASES NOT IMPLEMENTED
-------------------------
1. Viewing a Google Map with all stops. We actually did that by loading all of the 7k stops onto the map, however they were significantly clustered and
this caused the app to lag, and in order to make the loading of all those stops more efficient and less CPU intensive, we would have had to either use
an external library or make it more efficient ourselves. We attempted to optimize it, but we realized that it would take more time than we have on our disposal,
so we decided not to implement this feature. In that case, we have implemented the 90% of the use cases.


USE CASES ADDED
--------------
(none added yet)


DATA STORAGE
------------
1. SQLite, we store the user's favorite stops with SQLite. This data is used so that the user may be able to see a list of their saved favorite stops.
2. SharedPreferences, we use SharedPreferences in order to store the user's login information so that the user can stay logged in when returning to the app, until they log out.


WEB SERVICES
------------
 - We use web services to pull up to date information on stop / route information. We pull stop name and id. We also put the route name, head sign and arrival time.  This data is shown to users when they "View all stops", or "View Bus Arrivals". We chose to use web services for this data to provide the user with the most accurate information.


CONTENT SHARING
---------------
- When viewing bus arrivals for a stop, click on the the three dots next to any route to share. This will allow the user to share text about the route. 
- This sends text content in the format of "I'm catching the [Trip Description]. It departs at [Trip Departure Time] and goes to [Trip Destination]."


BUGS FIXED FROM PHASE 1
-----------------------
- We had a bug where moving the orientation of the app would populate multiple recycler views. We fixed this by adjusting the manifest to handle the change in orientation.


MEETINGS LINK
-------------
https://docs.google.com/document/d/1MhE_PGP8fLDAu67E8x0gVKbaSBTh3F96BJAqr6dfcLM/edit?usp=sharing