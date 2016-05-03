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

import java.net.URLEncoder;

import tcss450.uw.edu.team12.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    /* User ID and password */
    private EditText mUserID;
    private EditText mUserPassword;

    private RegisterUserListener mListener;


    private static final String USERS_URL =
            "http://cssgate.insttech.washington.edu/~ldimov/users.php";
    private final static String USER_ADD_URL =
            "http://cssgate.insttech.washington.edu/~ldimov/addUser.php?";

    public RegisterFragment() {
        // Required empty public constructor
    }

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
                Toast.makeText(v.getContext(), "Should register u",
                        Toast.LENGTH_SHORT)
                        .show();
                // TODO: register user, login to main activity

//                RegisterUserTask task = new RegisterUserTask();
//                task.execute(new String[]{USER_URL});

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

            Toast.makeText(v.getContext(), "Registered", Toast.LENGTH_LONG)
                    .show();
            Log.i("RegisterFragment", sb.toString());

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    /**
     * Attempt to register the user.
     */
//    private class RegisterUserTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected void
//        onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//
//            String user = "";
//
//
//            return "";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//        }
//
//    }

    public interface RegisterUserListener {
        public void addUser(String url);
    }




}
