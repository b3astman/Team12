package tcss450.uw.edu.team12.authenticate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by bethany on 5/3/16.
 */
public class User {


    public static final String ID_NAME = "email";
    public static final String PASSWORD = "pwd";

    String mEmail;
    String mPassword;

    public User(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }
    public String getPassword() {
        return mPassword;
    }

    /**  Parses the json string, returns an error message if unsuccessful.
     *
     * Returns stops list if successful.
     * @param courseJSON  * @return reason or null if successful.
     */
    public static String parseCourseJSON(String courseJSON, List<User> routesList) {
        String reason = null;
        if (courseJSON != null) {
            try {
                JSONArray arr = new JSONArray(courseJSON);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    User route = new User(obj.getString(User.ID_NAME), obj.getString(User.PASSWORD));
                    routesList.add(route);
                }
            } catch (JSONException e) {
                reason =  "Unable to parse data. Reason: " + e.getMessage();
            }
        }
        return reason;
    }

}
