package com.example.prrateekk.firebasepractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button idSignUpBtn;
    private EditText idEmail;
    private EditText idPassword;
    private TextView idSignIn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null) {
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        idSignUpBtn = (Button) findViewById(R.id.idSignUpBtn);
        idEmail = (EditText) findViewById(R.id.idEmail);
        idPassword = (EditText) findViewById(R.id.idPassword);
        idSignIn = (TextView) findViewById(R.id.idSignIn);

        idSignIn.setOnClickListener(this);
        idSignUpBtn.setOnClickListener(this);
    }

    private void registerUser() {
        String email = idEmail.getText().toString();
        String password = idPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Successfully Registered User!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                            Log.i("FAILED", task.toString());
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view==idSignUpBtn) {
            registerUser();
        }
        else if (view==idSignIn) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
