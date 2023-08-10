package com.example.asgmnt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class History extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        SharedPreferences sharedPreferences = getSharedPreferences("BillResults", MODE_PRIVATE);
        String historyText = sharedPreferences.getString("percentageResult", "");

        // Display history data in TextView
        TextView historyTextView = findViewById(R.id.historyResultTextView);
        historyTextView.setText(historyText);
    }

}
