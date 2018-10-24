package com.bitme.rozet.k.bitme;

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
        submitButton.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
        submitButton.setTextColor(Color.parseColor("#224A6B"));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        // checks if name is empty and if it only contains valid chars
        return !name.isEmpty() && name.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    private void setupUserNameEditText() {
        userNameEditText = findViewById(R.id.inputname_enter_name_field);
        userNameEditText.setTextColor(Color.parseColor("#FF9800"));
        userNameEditText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
    }
}
