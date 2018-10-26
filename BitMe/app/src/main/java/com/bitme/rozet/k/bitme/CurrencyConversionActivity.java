package com.bitme.rozet.k.bitme;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
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

public class CurrencyConversionActivity extends AppCompatActivity {
    TextView numberTextView, abrvTextView, oneTextView, btcTextView, todaysValueTextView, equivalentValueTextView, equivalentAbrvTextView;
    Button returnButton;

    private Currencies currencies;
    private Typeface mentone;
    private int currencyPos;
    private int currencyConvertedValue = 0;
    private String abrvAndSymbol = "";
    private boolean executeOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        init();
        setupTextViews();
        setupReturnButton();
        retrieveConvertedCurrency();
    }

    private void setupTextViews() {
        numberTextView = findViewById(R.id.currencyconversion_number);
        numberTextView.setTypeface(mentone);

        abrvTextView = findViewById(R.id.currencyconversion_abrv);
        // changes font and text color
        abrvTextView.setTextColor(Color.parseColor(
                "#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        abrvTextView.setTypeface(mentone);
        abrvTextView.setText(abrvAndSymbol);

        oneTextView = findViewById(R.id.currencyconversion_one_text);
        oneTextView.setTypeface(mentone);

        btcTextView = findViewById(R.id.currencyconversion_btc_text);
        // changes font and text color
        btcTextView.setTextColor(Color.parseColor(
                "#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        btcTextView.setTypeface(mentone);

        todaysValueTextView = findViewById(R.id.currencyconversion_todays_value_text);
        todaysValueTextView.setTypeface(mentone);

        equivalentValueTextView = findViewById(R.id.currencyconversion_equivalent_value_text);
        equivalentValueTextView.setTypeface(mentone);

        equivalentAbrvTextView = findViewById(R.id.currencyconversion_equivalent_abrv_text);
        // changes font and text color
        equivalentAbrvTextView.setTextColor(Color.parseColor(
                "#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))));
        equivalentAbrvTextView.setTypeface(mentone);
        equivalentAbrvTextView.setText(abrvAndSymbol);
    }

    // uses AsyncHttpClient to request a JSON of converted currency
    private void retrieveConvertedCurrency() {
        String fromCurrency = "BTC";
        // selects the currency that the user requested in RequestCurrencyActivity activity
        final String toCurrency = currencies.getCurrencyAbrv(currencyPos);
        final String combinedCurrencies = fromCurrency + "_" + toCurrency;
        // the url with sufficient queries to retrieve the JSON
        String url = "http://free.currencyconverterapi.com/api/v6/convert?q="
                        + combinedCurrencies + "&compact=ultra";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    String data = response.getString(combinedCurrencies);
                    // converts currency to an int to truncate the decimal
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
                } else {
                    // start the animation
                    startCountAnimation(currencyConvertedValue);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

                Toast.makeText(getApplicationContext(), "Connection to server cannot be reached", Toast.LENGTH_LONG).show();
            }
        });
    }

    // button simply ends the activity - returning to RequestCurrencyActivity
    private void setupReturnButton() {
        returnButton = findViewById(R.id.currencyconversion_return);
        // changes font and text color
        returnButton.setTypeface(mentone);
        returnButton.setTextColor(Color.parseColor(
                "#"+Integer.toHexString(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryLight))));
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // animation for increasing the number value
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
        // retrieves the position of the currency selected by the user
        Intent intent = getIntent();
        currencyPos = intent.getIntExtra("userCurrencyPosition", 0);
        currencies = new Currencies();
        mentone = Typeface.createFromAsset(getAssets(),"fonts/mentone_semibol_ita.otf");

        abrvAndSymbol = currencies.getCurrencySymbol(currencyPos) + " " + currencies.getCurrencyAbrv(currencyPos);
    }

    @Override
    // finishes the activity when the user switches to another application
    protected void onResume() {
        super.onResume();
        if (executeOnResume)
            finish();

        executeOnResume = true;
    }
}
