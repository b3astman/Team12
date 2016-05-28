package tcss450.uw.edu.team12.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * A class represnting a bus stop. Contains the Stop ID as well
 * as the name of the stop.
 *
 * Created by Lachezar Dimov on 5/1/2016.
 */
public class Stop implements Serializable {

    /** Stop ID label used in the JSON object for a Stop */
    public static final String STOP_ID = "stop_id";
    /** Stop name label used in JSON object for a Stop */
    public static final String STOP_NAME = "stop_name";

    String mStopId;
    String mStopName;

    /**
     * Constructs a stop with the ID and name.
     * @param stopId
     * @param stopName
     */
    public Stop(String stopId, String stopName) {
        mStopId = stopId;
        mStopName = stopName;
    }

    /**
     * Getter for the Stop ID.
     * @return a string with the stop id.
     */
    public String getStopId() {
        return mStopId;
    }

    /**
     * Getter for the Stop name.
     * @return
     */
    public String getStopName() {
        return mStopName;
    }

    /**
     * Setter for the Stop id.
     *
     * @param id
     */
    public void setStopId(String id) {
        if (id == null) throw new IllegalArgumentException();
        mStopId = id;
    }

    /**
     * Setter for the Stop name.
     *
     * @param stopName
     */
    public void setStopName(String stopName) {
        if (stopName == null) throw new IllegalArgumentException();
        mStopName = stopName;
    }



    /**
     * Parses the json string, returns an error message if unsuccessful.
     *
     * Returns stops list if successful.
     * @param courseJSON  * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<Stop> stopList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String stopName = obj.getString(Stop.STOP_NAME);
                    Stop stop = new Stop(obj.getString(Stop.STOP_ID), stopName.substring(1, stopName.length() - 1));
                    stopList.add(stop);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data. Reason: " + e.getMessage();
            }
        }
        return reason;
    }

}