package com.example.janazahapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class createEvent extends AppCompatActivity {
    private ActionBar toolbar;
    private Button chooseMosque;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        chooseMosque=findViewById(R.id.chooseMosque);

        chooseMosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseMosquePopup chooseMosquePopup=new ChooseMosquePopup(createEvent.this);
                chooseMosquePopup.setTitle("choose a mosque to pray in");
                chooseMosquePopup.build();
            }
        });

    }
}
