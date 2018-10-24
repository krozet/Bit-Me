package com.bitme.rozet.k.bitme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;

public class RequestCurrency extends AppCompatActivity {
    private static final int RESULT_FIRST_START = 1;

    TextView nameTextView, helloTextView, spinnerItemTextView;
    Button convertButton;
    Spinner selectCurrencySpinner;

    SharedPreferences sharedPreferences;
    Typeface mentone;
    Currencies currency;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_currency);

        checkPreviouslyStarted();
        setupNameTextView();
        setupSelectCurrencySpinner();
        setupConvertButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectCurrencySpinner.setSelection(sharedPreferences.getInt("currency", 0));
        displayName();
    }

    private void displayName() {
        name = sharedPreferences.getString("userName", "User");
        String displayName = name + "!";
        nameTextView.setText(displayName);
    }

    private void setupConvertButton() {
        convertButton = findViewById(R.id.requestcurrency_convert);
        convertButton.setTypeface(mentone);
        convertButton.setTextColor(Color.parseColor("#224A6B"));
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCurrencyConversion();
            }
        });
    }

    private void openCurrencyConversion() {
        Intent intent = new Intent(this, CurrencyConversion.class);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("currency", selectCurrencySpinner.getSelectedItemPosition());
        edit.commit();
        startActivity(intent);
    }

    private void setupSelectCurrencySpinner() {
        currency = new Currencies();
        selectCurrencySpinner = findViewById(R.id.requestcurrency_select_currency_spinner);
        selectCurrencySpinner.setAdapter(new MySpinnerAdapter(this, R.layout.spinner_item, currency.getList()));

        spinnerItemTextView = findViewById(R.id.requestcurrency_spinner_item);
    }

    private void setupNameTextView() {
        mentone = Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf");
        nameTextView = findViewById(R.id.requestcurrency_name);
        nameTextView.setTypeface(mentone);
        displayName();

        helloTextView = findViewById(R.id.requestcurrency_hello);
        helloTextView.setTypeface(mentone);
    }

    private void checkPreviouslyStarted() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean firstStart = sharedPreferences.getBoolean("isFirstStart", true);
        if(firstStart) {
            openInputNameActivity();
        }
    }

    private void openInputNameActivity() {
        Intent intent = new Intent(this, InputName.class);
        startActivityForResult(intent, RESULT_FIRST_START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case RESULT_FIRST_START:
                if (resultCode == RESULT_OK) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putBoolean("isFirstStart", Boolean.FALSE);
                    edit.commit();
                }
                break;
            default:
                break;
        }
    }
}
