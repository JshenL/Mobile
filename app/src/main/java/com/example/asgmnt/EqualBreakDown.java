package com.example.asgmnt;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EqualBreakDown extends AppCompatActivity {

    private EditText billAmountEditText;
    private EditText numUsersEditText;
    private Button calculateButton;
    private TextView resultTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equalbreakdown);

        // Initialize UI elements
        billAmountEditText = findViewById(R.id.billAmountEditText);
        numUsersEditText = findViewById(R.id.numUsersEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity to navigate back to MainActivity
                finish();
            }
        });

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the resultTextView
                resultTextView.setText("");
            }
        });

        // Set up click listener for the Calculate button
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEqualBreakdown();
                //user can clear the result to proceed next calculate
                clearButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void calculateEqualBreakdown() {
        String billAmountText = billAmountEditText.getText().toString();
        String numUsersText = numUsersEditText.getText().toString();

        if (billAmountText.isEmpty() || numUsersText.isEmpty()) {
            resultTextView.setText("Please enter valid values.");
            return;
        }


        try {
            // Parse user input
            double billAmount = Double.parseDouble(billAmountText);
            int numUsers = Integer.parseInt(numUsersText);

            // Calculate equal break-down
            double equalAmount = billAmount / numUsers;

            String result = String.format("%.2f", equalAmount);
            String resultText = "Equal Amount for Each User: RM" + result;
            resultTextView.setText(resultText);


        } catch (NumberFormatException e) {
            resultTextView.setText("Invalid input. Please enter valid numbers.");


        }

    }
}

