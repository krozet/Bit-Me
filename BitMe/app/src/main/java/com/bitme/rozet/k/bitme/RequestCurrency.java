package com.bitme.rozet.k.bitme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Currency;

public class RequestCurrency extends AppCompatActivity {
    private static final int RESULT_FIRST_START = 1;

    TextView nameTextView, helloTextView, spinnerItemTextView;
    Spinner selectCurrencySpinner;

    SharedPreferences sharedPreferences;
    Currencies currency;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_currency);


        checkPreviouslyStarted();
        name = sharedPreferences.getString("userName", "User");
        setupNameTextView();
        setupSelectCurrencySpinner();
    }

    private void setupSelectCurrencySpinner() {
        currency = new Currencies();
        selectCurrencySpinner = findViewById(R.id.requestcurrency_select_currency_spinner);
        selectCurrencySpinner.setAdapter(new MySpinnerAdapter(this, R.layout.spinner_item, currency.getList()));

        spinnerItemTextView = findViewById(R.id.requestcurrency_spinner_item);
//        spinnerItemTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
    }

    private void setupNameTextView() {
        nameTextView = findViewById(R.id.requestcurrency_name);
        nameTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
        String displayName = name + "!";
        nameTextView.setText(displayName);

        helloTextView = findViewById(R.id.requestcurrency_hello);
        helloTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf"));
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
