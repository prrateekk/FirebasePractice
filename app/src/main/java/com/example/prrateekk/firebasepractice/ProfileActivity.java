package com.example.prrateekk.firebasepractice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    private EditText idName;
    private EditText idAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        idName = (EditText) findViewById(R.id.idName);
        idAge = (EditText) findViewById(R.id.idAge);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if (firebaseAuth==null) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                Log.i("NAME", userInformation.name);
                Log.i("AGE", Integer.toString(userInformation.age));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void logOutUser(View view) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void saveUserInformation(View view) {
        String name = idName.getText().toString();
        int age = Integer.parseInt(idAge.getText().toString());
        UserInformation userInformation= new UserInformation(name, age);
        databaseReference.child(firebaseUser.getUid()).setValue(userInformation)
        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProfileActivity.this, "Information Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ProfileActivity.this, "Failed to save information", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
