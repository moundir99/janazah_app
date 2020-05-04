package com.example.janazahapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnotherProfileActivity extends AppCompatActivity{
    TextView name_,description_,nbParticipants_,mosque_,prayer_;
    RelativeLayout card;
    Button pariticipate;

    //DATABASE
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_profile_activity);


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
        final String clickedId=intent.getStringExtra("id");
        Log.d("anotherProfile", "id: "+ clickedId);
        // android:background="#afeeee"


        name_.setText(nameDead);
        nbParticipants_.setText(nbParticipants);
        mosque_.setText(mosque);
        description_.setText(description);
        prayer_.setText(prayerDay);

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("moundax","value: ");
                if(which==DialogInterface.BUTTON_POSITIVE){
                    reff= FirebaseDatabase.getInstance().getReference().child("Event");
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // extraction db
                                String id = snapshot.getKey();
                                Log.d("dedans", "clickedid: "+clickedId+" /id:"+id);



                                if(clickedId.equals(id)){
                                    Log.d("mhm", "on rentr dans l id: "+clickedId);
                                    snapshot.getRef().removeValue();
                                    Toast.makeText(AnotherProfileActivity.this,"Event deleted successfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                    Intent intent = new Intent(AnotherProfileActivity.this, Profile.class);
                    startActivity(intent);
                }
                else if(which==DialogInterface.BUTTON_NEGATIVE) {

                }
            }
        };



        pariticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AnotherProfileActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


    }
}
