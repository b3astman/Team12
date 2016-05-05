package tcss450.uw.edu.team12.authenticate;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import tcss450.uw.edu.team12.R;

/**
 * A fragment to handle registration by a user, and input their
 * registration information into a database of Users.
 */
public class RegisterFragment extends Fragment {

    /* User ID */
    private EditText mUserID;
    /* User Password */
    private EditText mUserPassword;

    private RegisterUserListener mListener;


    private static final String USERS_URL =
            "http://cssgate.insttech.washington.edu/~ldimov/users.php";
    private final static String USER_ADD_URL =
            "http://cssgate.insttech.washington.edu/~ldimov/addUser.php?";

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Attaches listeners to registration buttons to allow the user to register
     * their information to a database of users, otherwise, allows a user
     * to go back to the login page.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_register, container, false);
        Button registerButton = (Button) v.findViewById(R.id.register_user);
        getActivity().setTitle(R.string.register);
        final EditText userIdText = (EditText) v.findViewById(R.id.register_user_id);
        final EditText pwdText = (EditText) v.findViewById(R.id.register_pwd);
        final EditText confirmText = (EditText) v.findViewById(R.id.register_confirm_pwd);

        mUserID = (EditText) v.findViewById(R.id.register_user_id);
        mUserPassword = (EditText) v.findViewById(R.id.register_pwd);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdText.getText().toString();
                String pwd = pwdText.getText().toString();
                String confirm = confirmText.getText().toString();
                if (TextUtils.isEmpty(userId))  {
                    Toast.makeText(v.getContext(), "Enter userid"
                            , Toast.LENGTH_SHORT)
                            .show();
                    userIdText.requestFocus();
                    return;
                }
                if (!userId.contains("@")) {
                    Toast.makeText(v.getContext(), "Enter a valid email address"
                            , Toast.LENGTH_SHORT)
                            .show();
                    userIdText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pwd))  {
                    Toast.makeText(v.getContext(), "Enter password"
                            , Toast.LENGTH_SHORT)
                            .show();
                    pwdText.requestFocus();
                    return;
                }
                if (pwd.length() < 6) {
                    Toast.makeText(v.getContext(), "Enter password of at least 6 characters"
                            , Toast.LENGTH_SHORT)
                            .show();
                    pwdText.requestFocus();
                    return;
                }
                if (!confirm.equals(pwd)) {
                    Toast.makeText(v.getContext(), "Passwords do not match"
                            , Toast.LENGTH_SHORT)
                            .show();
                    pwdText.requestFocus();
                    return;
                }


                // User is okay to register
                String url = buildUserURL(v);
                mListener.addUser(url);

                return;
            }
        });

        Button backButton = (Button) v.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().setContentView(R.layout.activity_sign_in);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, new LoginFragment())
                        .commit();
            }
        });
        return v;
    }

    /**
     * Attaches the listener to the fragment.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterUserListener) {
            mListener = (RegisterUserListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement RegisterUserListener");
        }
    }

    /**
     * Builds the URL for User Registration.
     *
     * @param v
     * @return a string representatio of the URL
     */
    private String buildUserURL(View v) {
        StringBuilder sb = new StringBuilder(USER_ADD_URL);
        try {
            String userId = mUserID.getText().toString();
            sb.append("email=");
            sb.append(userId);
            String userPassword = mUserPassword.getText().toString();
            sb.append("&pwd=");
            sb.append(URLEncoder.encode(userPassword, "UTF-8"));

            Log.i("RegisterFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "There is no network connection. Please connect to the Internet", Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Interface to allow a user to be added to a database of users.
     */
    public interface RegisterUserListener {
        public void addUser(String url);
    }




}
