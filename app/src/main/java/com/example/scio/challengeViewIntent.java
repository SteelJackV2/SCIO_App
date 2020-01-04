package com.example.scio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scio.ui.home.HomeFragment;

import org.w3c.dom.Text;

public class challengeViewIntent extends AppCompatActivity {

    TextView title, author, description;
    Button upload, donate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_view_intent);

        title = findViewById(R.id.challengeViewIntentTitle);
        author = findViewById(R.id.challengeViewIntentAuthor);
        description = findViewById(R.id.challengeViewIntentDescription);
        upload = findViewById(R.id.challengeViewIntentUpload);
        donate = findViewById(R.id.challengeViewIntentDonate);

        Challenge c = HomeFragment.challengeId;
        title.setText(c.getName());
        author.setText(c.getAuthor());
        description.setText(c.getDescription());
        upload.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Coming Soon",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });

        donate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Coming Soon",
                        Toast.LENGTH_SHORT);

                toast.show();
            }
        });
    }
}
