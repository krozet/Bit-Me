package com.bitme.rozet.k.bitme;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CurrencyConversion extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Currencies currencies;

    int currencyPos;
    boolean executeOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        init();
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
