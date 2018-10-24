package com.bitme.rozet.k.bitme;

import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrencyConversion extends AppCompatActivity {
    TextView numberTextView, abrvTextView, oneTextView, btcTextView, todaysValueTextView, equivalentValueTextView, equivalentAbrvTextView;
    Button returnButton;

    SharedPreferences sharedPreferences;
    Currencies currencies;
    Typeface mentone;

    int currencyPos;
    boolean executeOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        init();
        setupNumberTextView();
        setupAbrvTextView();
        setupOneTextView();
        setupBTCTextView();
        setupTodaysValueTextView();
        setupEquivalentValueTextView();
        setupEquivalentAbrvTextView();
        setupReturnButton();

        startCountAnimation(600);
    }

    private void setupReturnButton() {
        returnButton = findViewById(R.id.currencyconversion_return);
        returnButton.setTypeface(mentone);
    }

    private void setupEquivalentValueTextView() {
        equivalentValueTextView = findViewById(R.id.currencyconversion_equivalent_value_text);
        equivalentValueTextView.setTypeface(mentone);
    }

    private void setupEquivalentAbrvTextView() {
        equivalentAbrvTextView = findViewById(R.id.currencyconversion_equivalent_abrv_text);
        equivalentAbrvTextView.setTextColor(Color.parseColor("#FF9800"));
        equivalentAbrvTextView.setTypeface(mentone);
    }

    private void setupTodaysValueTextView() {
        todaysValueTextView = findViewById(R.id.currencyconversion_todays_value_text);
        todaysValueTextView.setTypeface(mentone);
    }

    private void setupOneTextView() {
        oneTextView = findViewById(R.id.currencyconversion_one_text);
        oneTextView.setTypeface(mentone);
    }

    private void setupBTCTextView() {
        btcTextView = findViewById(R.id.currencyconversion_btc_text);
        btcTextView.setTextColor(Color.parseColor("#FF9800"));
        btcTextView.setTypeface(mentone);
    }

    private void setupAbrvTextView() {
        abrvTextView = findViewById(R.id.currencyconversion_abrv);
        abrvTextView.setTextColor(Color.parseColor("#FF9800"));
        abrvTextView.setTypeface(mentone);
    }

    private void setupNumberTextView() {
        numberTextView = findViewById(R.id.currencyconversion_number);
        numberTextView.setTypeface(mentone);
    }

    private void startCountAnimation(int value) {
        ValueAnimator animator = ValueAnimator.ofInt(0, value);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                numberTextView.setText(animation.getAnimatedValue().toString());
                equivalentValueTextView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    private void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        currencyPos = sharedPreferences.getInt("currency", 0);
        currencies = new Currencies();
        mentone = Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (executeOnResume)
            finish();

        executeOnResume = true;
    }
}
