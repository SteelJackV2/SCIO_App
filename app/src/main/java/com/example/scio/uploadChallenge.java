package com.example.scio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.scio.MainActivity.databaseusers;

public class uploadChallenge extends AppCompatActivity {

    EditText name, description, author;
    Button button;
    DatabaseReference databaseChallenges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_challenge);

        name = findViewById(R.id.challengeUploadNameView);
        description = findViewById(R.id.challengeIploadIntentDescriptionView);
        button = findViewById(R.id.challengeUploadButton);

        databaseChallenges = FirebaseDatabase.getInstance().getReference("Challenges");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ntent i = new Intent();

                i.putExtra("name", name.getText().toString());
                i.putExtra("desciption", description.getText().toString());*/
                final Challenge newChallenge = new Challenge(name.getText().toString(), "", description.getText().toString(), (long) 0,(long)0);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String s = user.getUid();

                databaseusers.addValueEventListener(new ValueEventListener() {


                    public User u;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(s))
                            u = dataSnapshot.child(s).getValue(User.class);
                            newChallenge.setAuthor(u.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
                String key = databaseChallenges.push().getKey();
                databaseChallenges.child(key).setValue(newChallenge);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });


    }
}
