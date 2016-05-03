package tcss450.uw.edu.team12.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.OutputStreamWriter;

import tcss450.uw.edu.team12.MainActivity;
import tcss450.uw.edu.team12.R;

public class SignInActivity extends AppCompatActivity implements LoginFragment.LoginInteractionListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_in);
//
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container, new LoginFragment())
//                .commit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        setTitle("Sign in");

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Check if the login and password are valid
            //new LoginTask().execute(url);
        }
        else {
            Toast.makeText(this, "No network connection available. Cannot authenticate user",
                    Toast.LENGTH_SHORT) .show();
            return;
        }


        mSharedPreferences = getSharedPreferences(getString(R.string.LOGIN_PREFS)
                , Context.MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(getString(R.string.LOGGEDIN), false)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, new LoginFragment())
                    .commit();
        } else {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    @Override
    public void login(String userId, String pwd) {
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
//        finish();
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Check if the login and password are valid
            //new LoginTask().execute(url);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        openFileOutput(getString(R.string.LOGIN_FILE)
                                , Context.MODE_PRIVATE));
                outputStreamWriter.write("email = " + userId + ";");
                outputStreamWriter.write("password = " + pwd);
                outputStreamWriter.close();
                Toast.makeText(this,"Stored in File Successfully!", Toast.LENGTH_LONG)
                        .show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        mSharedPreferences
                .edit()
                .putBoolean(getString(R.string.LOGGEDIN), true)
                .commit();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    // TODO: register user and login
    public void register(String userId, String pwd) {
        // take user to registration
        setContentView(R.layout.activity_sign_in);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new RegisterFragment())
                .commit();

    }

}
