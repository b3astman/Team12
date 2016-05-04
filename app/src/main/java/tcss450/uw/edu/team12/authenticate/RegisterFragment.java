package tcss450.uw.edu.team12.authenticate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tcss450.uw.edu.team12.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


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
                    return;
                }

                // TODO: register user, login to main activity
                Toast.makeText(v.getContext(), "Should register u",
                        Toast.LENGTH_SHORT)
                        .show();
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

//    @Override
//    public View onDestroyView(LayoutInflater inflater, ViewGroup container,
//                              Bundle savedInstanceState) {
//
//    }

}
