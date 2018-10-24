package com.bitme.rozet.k.bitme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputName extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText userNameEditText;
    Button submitButton;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name);
        
        setupUserNameEditText();
        setupSubmitButton();
    }

    private void setupSubmitButton() {
        submitButton = findViewById(R.id.inputname_submit);
        // changes font and text color
        submitButton.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
        submitButton.setTextColor(Color.parseColor("#224A6B"));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the name is acceptable, store it in shared preferences
                // and return to RequestCurrency activity
                if (validateName()) {
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("userName", name);
                    edit.commit();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a valid name.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validateName() {
        name = userNameEditText.getText().toString();
        // checks if name is empty
        // and if it only contains valid chars
        // and is under 20 chars
        return !name.isEmpty()
                && name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*")
                && name.length() < 20;
    }

    private void setupUserNameEditText() {
        userNameEditText = findViewById(R.id.inputname_enter_name_field);
        // changes font and text color
        userNameEditText.setTextColor(Color.parseColor("#FF9800"));
        userNameEditText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
    }

    @Override
    // closes the application to prevent going back to RequestCurrency
    public void onBackPressed() {
        Intent closeApplication = new Intent(Intent.ACTION_MAIN);
        closeApplication.addCategory(Intent.CATEGORY_HOME);
        closeApplication.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(closeApplication);
    }
}
