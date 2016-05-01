package tcss450.uw.edu.team12.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lachezar Dimov on 5/1/2016.
 */
public class Stop implements Serializable {
    public static final String STOP_ID = "stop_id";
    public static final String STOP_NAME = "stop_name";

    String mStopId;
    String mStopName;

    public Stop(String stopId, String stopName) {
        mStopId = stopId;
        mStopName = stopName;
    }

    public String getStopId() {
        return mStopId;
    }
    public String getStopName() {
        return mStopName;
    }

    /**  Parses the json string, returns an error message if unsuccessful.
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
                    Stop stop = new Stop(obj.getString(Stop.STOP_ID), obj.getString(Stop.STOP_NAME));
                    stopList.add(stop);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data. Reason: " + e.getMessage();
            }
        }
        return reason;
    }

}
