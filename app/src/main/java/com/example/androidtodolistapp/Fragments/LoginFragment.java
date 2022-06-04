package com.example.androidtodolistapp.Fragments;

import static com.example.androidtodolistapp.Fragments.RegisterFragment.USERS_REF;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.service.autofill.UserData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtodolistapp.Activities.MainActivity;
import com.example.androidtodolistapp.Model.UserBean;
import com.example.androidtodolistapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private EditText et_email, et_password;
    private TextView tv_registerNow;
    private Button btn_login;
    View v;
    FirebaseAuth firebaseAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make an instance of FirebaseAuth.
        firebaseAuth = FirebaseAuth.getInstance();
        // Initialize views
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);
        viewsInflate();

        tv_registerNow.setOnClickListener(view -> {
            RegisterFragment registerFragment = new RegisterFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fcv, registerFragment)
                    .addToBackStack(null)
                    .commit();
        });

        btn_login.setOnClickListener(view -> login());

        return v;
    }


    private void login() {
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        if (email.isEmpty()) {
            et_email.setError("Email can not be empty");
            Toast.makeText(getActivity(), "Email can not be empty", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(getActivity(), "Password can not be less than 6 digits", Toast.LENGTH_SHORT).show();
        } else {
            // Send request to Firebase Auth and check ...
            // if state success => Send all data to Firebase ...
            // else => Show error message to user.
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Login succeeded", Toast.LENGTH_SHORT).show();
                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                getDataFromFirebase(userId);
                            } else {
                                Toast.makeText(getActivity(), "Something Error! Check your email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void getDataFromFirebase(String userId) {
        FirebaseDatabase.getInstance().getReference().child(USERS_REF).child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.v("/////***", (dataSnapshot.getValue() != null) + "");
                        if (dataSnapshot.getValue() != null) {
                            UserBean userData = dataSnapshot.getValue(UserBean.class);
                            Log.v("/////***", userData.getEmail());
                            // save data in app shaerd referance
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), " Error! Cannot find user data in database", Toast.LENGTH_LONG).show();
                            FirebaseAuth.getInstance().signOut();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), " Error! Cannot find user data in database", Toast.LENGTH_LONG).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                });
    }

    private void viewsInflate() {
        et_password = v.findViewById(R.id.login_et_password);
        tv_registerNow = v.findViewById(R.id.tv_register);
        et_email = v.findViewById(R.id.login_et_email);
        btn_login = v.findViewById(R.id.btn_login);
    }
}