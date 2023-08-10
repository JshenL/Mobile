package com.example.asgmnt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class CustomBreakDown extends AppCompatActivity {

    private EditText billAmountEditText;
    private Button breakdownByPercentageButton;
    private Button breakdownByRatioButton;
    private Button breakdownByAmountButton;
    private EditText numUsersEditText;
    //private EditText userPercentageEditText;
    //private EditText userRatioEditText;
    //private EditText userAmountEditText;
    private LinearLayout percentageRatioLayout;
    private Button calculateButton;
    private Button calculate2Button;
    private Button calculate3Button;
    private TextView resultTextView;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custombreakdown);
        setTitle("Custom Breakdown");

        // Initialize UI elements
        billAmountEditText = findViewById(R.id.billAmountEditText);
        numUsersEditText = findViewById(R.id.numUsersEditText);


        percentageRatioLayout = findViewById(R.id.percentageRatioLayout);
        //userPercentageEditText = findViewById(R.id.UserPercentage);
        //userRatioEditText = findViewById(R.id.UserRatio);
        //userAmountEditText = findViewById(R.id.UserAmount);
        Button clearButton = findViewById(R.id.clearButton);
        resultTextView = findViewById(R.id.resultTextView);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity to navigate back to MainActivity
                finish();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the resultTextView
                resultTextView.setText("");
            }
        });





        breakdownByPercentageButton = findViewById(R.id.breakdownByPercentageButton);
        breakdownByPercentageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int numUsers = Integer.parseInt(numUsersEditText.getText().toString());
                generateUserPercentageFields(numUsers);

                //show only percentage related calculate button
                calculateButton.setVisibility(View.VISIBLE);
                //userRatioEditText.setVisibility(View.GONE);
                calculate2Button.setVisibility(View.GONE);
                calculate3Button.setVisibility(View.GONE);
            }
        });

        breakdownByRatioButton = findViewById(R.id.breakdownByRatioButton);
        breakdownByRatioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //userPercentageEditText.setVisibility(View.GONE);
                //userRatioEditText.setVisibility(View.VISIBLE);
                int numUsers = Integer.parseInt(numUsersEditText.getText().toString());
                generateUserRatioFields(numUsers);

                //show only ratio related calculate button
                calculate2Button.setVisibility(View.VISIBLE);
                calculateButton.setVisibility(View.GONE);
                calculate3Button.setVisibility(View.GONE);
            }
        });

        breakdownByAmountButton = findViewById(R.id.breakdownByAmountButton);
        breakdownByAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //userPercentageEditText.setVisibility(View.GONE);
                //userRatioEditText.setVisibility(View.VISIBLE);
                int numUsers = Integer.parseInt(numUsersEditText.getText().toString());
                generateUserAmountFields(numUsers);

                //show only amount related calculate button
                calculate3Button.setVisibility(View.VISIBLE);
                calculate2Button.setVisibility(View.GONE);
                calculateButton.setVisibility(View.GONE);
            }
        });

        calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numUsers = Integer.parseInt(numUsersEditText.getText().toString());
                double billAmount = Double.parseDouble(billAmountEditText.getText().toString());

                // Create an array to store user input percentages
                double[] userPercentages = new double[numUsers];

                // Loop through the dynamically generated EditText fields and extract input
                for (int i = 0; i < numUsers; i++) {
                    EditText editText = (EditText) percentageRatioLayout.getChildAt(i);
                    String input = editText.getText().toString();
                    if (!input.isEmpty()) {
                        userPercentages[i] = Double.parseDouble(input);
                    }
                }
                double totalPercentage = 0;
                for (double percentage : userPercentages) {
                    totalPercentage += percentage;
                }

                // Check if total percentage is valid
                if (totalPercentage > 100) {
                    resultTextView.setText("Total percentage must not be greater than 100.");
                    //user can clear the result to proceed next calculate
                    clearButton.setVisibility(View.VISIBLE);
                    return; // Exit the method
                }

                // Calculate and display individual amounts based on percentages
                StringBuilder resultText = new StringBuilder("Percentage Breakdown:\n");
                for (int i = 0; i < userPercentages.length; i++) {
                    double individualAmount = (userPercentages[i] / 100) * billAmount;
                    resultText.append("User ").append(i + 1).append(" Pays: RM").append(individualAmount).append("\n");
                }
                // Save the results using SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("BillResults", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("percentageResult", resultText.toString());
                editor.apply();
                resultTextView.setText(resultText.toString());
                //user can clear the result to proceed next calculate
                clearButton.setVisibility(View.VISIBLE);
                // Save results to SharedPreferences
                saveResultsToSharedPreferences(resultText.toString());
            }



        });

        calculate2Button = findViewById(R.id.calculate2Button);
        calculate2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numUsers = Integer.parseInt(numUsersEditText.getText().toString());
                double billAmount = Double.parseDouble(billAmountEditText.getText().toString());

                // Create an array to store user input ratios
                int[] userRatios = new int[numUsers];

                // Loop through the dynamically generated EditText fields and extract input
                for (int i = 0; i < numUsers; i++) {
                    EditText editText = (EditText) percentageRatioLayout.getChildAt(i);
                    String input = editText.getText().toString();
                    if (!input.isEmpty()) {
                        userRatios[i] = Integer.parseInt(input);
                    }
                }

                double totalRatios = 0;

                // Calculate the total ratios
                for (int ratio : userRatios) {
                    totalRatios += ratio;
                }

                if (totalRatios > 0) {
                    double totalAmount = 0;

                    // Calculate and display individual amounts based on ratios
                    StringBuilder resultText = new StringBuilder("Ratio Breakdown:\n");
                    for (int i = 0; i < userRatios.length; i++) {
                        double individualAmount = (userRatios[i] * billAmount) / totalRatios;
                        totalAmount += individualAmount;
                        resultText.append("User ").append(i + 1).append(" Pays: RM").append(individualAmount).append("\n");
                    }

                    resultText.append("Total Amount: RM").append(totalAmount);

                    // Save the results using SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("BillResults", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("RatioResult", resultText.toString());
                    editor.apply();
                    resultTextView.setText(resultText.toString());
                    //user can clear the result to proceed next calculate
                    clearButton.setVisibility(View.VISIBLE);
                    // Save results to SharedPreferences
                    saveResultsToSharedPreferences(resultText.toString());
                } else {
                    resultTextView.setText("Total ratio must be greater than 0.");
                    //user can clear the result to proceed next calculate
                    clearButton.setVisibility(View.VISIBLE);
                }
            }



        });

        calculate3Button = findViewById(R.id.calculate3Button);
        calculate3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int numUsers = Integer.parseInt(numUsersEditText.getText().toString());
                double totalBillAmount = Double.parseDouble(billAmountEditText.getText().toString());

                double totalIndividualAmount = 0;
                int[] userAmount = new int[numUsers];
                // Loop through the dynamically generated EditText fields for individual amounts
                for (int i = 0; i < numUsers; i++) {
                    EditText editText = (EditText) percentageRatioLayout.getChildAt(i);
                    String input = editText.getText().toString();
                    if (!input.isEmpty()) {
                        totalIndividualAmount += Double.parseDouble(input);
                    }
                }


                if (Math.abs(totalIndividualAmount - totalBillAmount) <= 0.01) {
                    // Individual amounts match the total bill amount
                    resultTextView.setText("Individual amounts verified.");
                    //user can clear the result to proceed next calculate
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    // Individual amounts do not match the total bill amount
                    resultTextView.setText("Individual amounts do not match the total bill amount.");
                    //user can clear the result to proceed next calculate
                    clearButton.setVisibility(View.VISIBLE);
                }
            }


        });

        historyButton = findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayHistoryResults();
                Intent historyIntent = new Intent(CustomBreakDown.this, History.class);
                startActivity(historyIntent);
            }
        });
       
    }


    private void generateUserRatioFields(int numUsers) {
        percentageRatioLayout.removeAllViews(); // Clear existing views

        for (int i = 0; i < numUsers; i++) {
            EditText editText = new EditText(this);
            editText.setHint("Enter Ratio for User " + (i + 1));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            percentageRatioLayout.addView(editText);
        }
    }

    private void generateUserPercentageFields(int numUsers) {
        percentageRatioLayout.removeAllViews(); // Clear existing views

        for (int i = 0; i < numUsers; i++) {
            EditText editText = new EditText(this);
            editText.setHint("Enter Percentage for User " + (i + 1));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            percentageRatioLayout.addView(editText);
        }

    }

    private void generateUserAmountFields(int numUsers) {
        percentageRatioLayout.removeAllViews(); // Clear existing views

        for (int i = 0; i < numUsers; i++) {
            EditText editText = new EditText(this);
            editText.setHint("Enter Amount for User " + (i + 1));
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            percentageRatioLayout.addView(editText);
        }
    }

    private void saveResultsToSharedPreferences(String results) {
        SharedPreferences sharedPreferences = getSharedPreferences("BillResults", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("percentageResult", results);
        editor.apply();
    }

    private void displayHistoryResults() {
        SharedPreferences sharedPreferences = getSharedPreferences("BillResults", MODE_PRIVATE);
        String percentageResult = sharedPreferences.getString("percentageResult", null);

        if (percentageResult != null) {
            resultTextView.setText(percentageResult);
        }
        else {
            resultTextView.setText("No history results available.");
        }
    }
}
