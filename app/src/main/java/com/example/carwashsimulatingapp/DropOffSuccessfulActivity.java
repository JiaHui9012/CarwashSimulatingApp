package com.example.carwashsimulatingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DropOffSuccessfulActivity extends AppCompatActivity {
    //views
    TextView ticketNumberTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_off_successful);

        //init views
        ticketNumberTV = findViewById(R.id.ticketNumberTextView);

        //create the get Intent object
        Intent intent = getIntent();
        //receive the value using key
        String ticketNumber = intent.getStringExtra("ticket_number");
        //display the ticket number into textView
        ticketNumberTV.setText(ticketNumber);
    }
}