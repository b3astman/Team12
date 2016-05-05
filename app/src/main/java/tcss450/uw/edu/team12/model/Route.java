package tcss450.uw.edu.team12.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * A class representing a Bus Route. Stores information about a specific
 * route such as the name, departure time, headsign, and minutes until arrival.
 *
 * Created by Lachezar Dimov on 5/1/2016.
 */
public class Route implements Serializable {

    public static final String ROUTE_NAME = "route_short_name";
    public static final String DEPARTURE_TIME = "departure_time";
    public static final String TRIP_HEADSIGN = "trip_headsign";
    public static final String MINUTES = "minutes";

    String mRouteName;
    String mDepartureTime;
    String mTripHeadSign;
    String mMinutes;

    /**
     * Constructs a route with the name, departure time, headsign, and minutes until arrival.
     *
     * @param routeName name of the route.
     * @param departureTime time of departure.
     * @param tripHeadSign head sign of the route.
     * @param minutes minutes until the route arrives.
     */
    public Route(String routeName, String departureTime, String tripHeadSign, String minutes) {
        mRouteName = routeName;
        mDepartureTime = departureTime;
        mTripHeadSign = tripHeadSign;
        mMinutes = minutes;
    }

    /**
     * Getter for the name of the route.
     *
     * @return the name of the route.
     */
    public String getRouteName() {
        return mRouteName;
    }

    /**
     * Getter for the departure time of the route.
     *
     * @return the departure time of the route.
     */
    public String getDepartureTime() {
        return mDepartureTime;
    }

    /**
     * Getter for the minutes until arrival of the route.
     *
     * @return the minutes until arrival.
     */
    public String getMinutes() {
        return mMinutes;
    }

    /**
     * Getter for the head sign of the route.
     *
     * @return the head sign of the route.
     */
    public String getTripHeadSign() { return mTripHeadSign; }


    /**
     * Parses the json string for a Route, returns an error message if unsuccessful.
     *
     * Returns stops list if successful.
     * @param courseJSON  * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<Route> routesList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Route route = new Route(obj.getString(Route.ROUTE_NAME), obj.getString(Route.DEPARTURE_TIME)
                            ,obj.getString(Route.TRIP_HEADSIGN), obj.getString(Route.MINUTES));
                    routesList.add(route);
                }
            } catch (JSONException e) {
                reason =  "There are no bus arrivals within the next 60 minutes.";
            }
        }
        return reason;
    }
}
