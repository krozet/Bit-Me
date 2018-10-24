package com.bitme.rozet.k.bitme;

import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CurrencyConversion extends AppCompatActivity {
    TextView numberTextView, abrvTextView, oneTextView, btcTextView, todaysValueTextView, equivalentValueTextView, equivalentAbrvTextView;
    Button returnButton;

    SharedPreferences sharedPreferences;
    Currencies currencies;
    Typeface mentone;

    int currencyPos;
    int currencyConvertedValue = 0;
    String abrvAndSymbol = "";
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
        retrieveConvertedCurrency();

    }

    private void retrieveConvertedCurrency() {
        String fromCurrency = "BTC";
        final String toCurrency = currencies.getCurrencyAbrv(currencyPos);
        final String combinedCurrencies = fromCurrency + "_" + toCurrency;
        String url = "http://free.currencyconverterapi.com/api/v6/convert?q="
                        + combinedCurrencies + "&compact=ultra";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String data = response.getString(combinedCurrencies);
                    currencyConvertedValue = (int) Float.parseFloat(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                // South Korean won is too large for the animation to handle properly
                // will fix in the first patch after shipment
                if (toCurrency.equals("KRW")) {
                    numberTextView.setText(String.valueOf(currencyConvertedValue));
                    equivalentValueTextView.setText(String.valueOf(currencyConvertedValue));
                } else
                    startCountAnimation(currencyConvertedValue);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Toast.makeText(getApplicationContext(), "Connection to server cannot be reached", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupReturnButton() {
        returnButton = findViewById(R.id.currencyconversion_return);
        returnButton.setTypeface(mentone);
        returnButton.setTextColor(Color.parseColor("#224A6B"));
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupEquivalentValueTextView() {
        equivalentValueTextView = findViewById(R.id.currencyconversion_equivalent_value_text);
        equivalentValueTextView.setTypeface(mentone);
    }

    private void setupEquivalentAbrvTextView() {
        equivalentAbrvTextView = findViewById(R.id.currencyconversion_equivalent_abrv_text);
        equivalentAbrvTextView.setTextColor(Color.parseColor("#FF9800"));
        equivalentAbrvTextView.setTypeface(mentone);
        equivalentAbrvTextView.setText(abrvAndSymbol);

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
        abrvTextView.setText(abrvAndSymbol);
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

        abrvAndSymbol = currencies.getCurrencySymbol(currencyPos) + " " + currencies.getCurrencyAbrv(currencyPos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (executeOnResume)
            finish();

        executeOnResume = true;
    }
}
