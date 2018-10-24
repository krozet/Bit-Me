package com.bitme.rozet.k.bitme;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrencyConversion extends AppCompatActivity {
    TextView numberTextView, abrvTextView, oneBTCTextView, todaysValueTextView, equivalentTextView;
    Button returnButton;

    SharedPreferences sharedPreferences;
    Currencies currencies;

    int currencyPos;
    boolean executeOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        init();
        setupNumberTextView();
        setupAbrvTextView();
        setupOneBTCTextView();
        setupTodaysValueTextView();
        setupEquivalentTextView();
        setupReturnButton();
    }

    private void setupReturnButton() {
        returnButton = findViewById(R.id.currencyconversion_return);
    }

    private void setupEquivalentTextView() {
        equivalentTextView = findViewById(R.id.currencyconversion_equivalent_text);
    }

    private void setupTodaysValueTextView() {
        todaysValueTextView = findViewById(R.id.currencyconversion_todays_value_text);
    }

    private void setupOneBTCTextView() {
        oneBTCTextView = findViewById(R.id.currencyconversion_one_btc_text);
    }

    private void setupAbrvTextView() {
        abrvTextView = findViewById(R.id.currencyconversion_abrv);
    }

    private void setupNumberTextView() {
        numberTextView = findViewById(R.id.currencyconversion_number);
    }

    private void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        currencyPos = sharedPreferences.getInt("currency", 0);
        currencies = new Currencies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (executeOnResume)
            finish();

        executeOnResume = true;
    }
}
