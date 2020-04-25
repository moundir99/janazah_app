package com.example.janazahapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {
    TextView name_,description_,nbParticipants_,mosque_,prayer_;
    RelativeLayout card;
    Button pariticipate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);


        ActionBar actionBar = getSupportActionBar();

        card = findViewById(R.id.layout2);
        name_ = findViewById(R.id.nameDead);
        description_ = findViewById(R.id.cardJanazahDescription);
        nbParticipants_ = findViewById(R.id.NbParticipants);
        mosque_ = findViewById(R.id.mosqueLocalisation);
        prayer_ = findViewById(R.id.prayerDay);
        pariticipate = findViewById(R.id.participatePrayer);


        Intent intent  = getIntent();

        String nameDead = intent.getStringExtra("nameDead");
        String mosque = intent.getStringExtra("mosque");
        String prayerDay = intent.getStringExtra("prayerDay");
        String nbParticipants = intent.getStringExtra("nbParticipants");
        String description = intent.getStringExtra("description");

        // android:background="#afeeee"


        name_.setText(nameDead);
        nbParticipants_.setText(nbParticipants);
        mosque_.setText(mosque);
        description_.setText(description);
        prayer_.setText(prayerDay);

        pariticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Toast.makeText(getApplicationContext()," CLICK ON BUTTON  " ,Toast.LENGTH_SHORT).show();


                if(pariticipate.getText().equals("I GO")){
                    pariticipate.setText("I DON'T GO");
                    //   card.setBackgroundColor(Color.RED);
                }else{
                    pariticipate.setText("I GO");
                    //  card.setBackgroundColor(Color.GREEN);
                }

            }
        });


    }
}
