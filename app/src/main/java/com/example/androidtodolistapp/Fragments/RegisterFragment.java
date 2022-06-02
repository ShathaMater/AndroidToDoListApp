package com.example.androidtodolistapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtodolistapp.Activities.MainActivity;
import com.example.androidtodolistapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private TextView et_firstName, et_lastName,tv_loginNow;
    private EditText et_email , et_password;
    private Button btn_register;
    View v;
    FirebaseAuth firebaseAuth;

    public RegisterFragment() {
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
        v = inflater.inflate(R.layout.fragment_register, container, false);
        viewsInflate();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

         tv_loginNow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 LoginFragment loginFragment = new LoginFragment();
                 getActivity().getSupportFragmentManager().beginTransaction()
                         .replace(R.id.fcv,loginFragment).commit();
             }
         });

        return v;
    }

    private void register(){
        String firstName = et_firstName.getText().toString();
        String lastName = et_lastName.getText().toString();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();
        if(firstName.isEmpty()){
            et_firstName.setError("First name can not be empty");
//            Toast.makeText(getActivity(), "First name can not be empty", Toast.LENGTH_SHORT).show();
        }else if(lastName.isEmpty()){
            et_lastName.setError("Last name can not be empty");
        }else if(email.isEmpty()){
            et_email.setError("Email can not be empty");
        }else if(password.isEmpty() || password.length() < 6){
            et_password.setError("Password can not be less than 6 digits");
        }else{
            // Send request to Firebase Auth and check ...
            // if state success => Send all data to Firebase ...
            // else => Show error message to user.
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getActivity(), "User hase been created successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }else{
                                Toast.makeText(getActivity(), " Error! User hase not been created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        }

    }

    private void viewsInflate(){
        et_password = v.findViewById(R.id.register_et_password);
        et_firstName = v.findViewById(R.id.register_et_firstName);
        et_lastName = v.findViewById(R.id.register_et_lastName);
        et_email = v.findViewById(R.id.register_et_email);
        btn_register = v.findViewById(R.id.btn_register);
        tv_loginNow = v.findViewById(R.id.tv_login);
    }
}