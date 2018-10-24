package com.bitme.rozet.k.bitme;

/*
    Class contains all possible currencies to be converted
    Includes currency name, abbreviation (ABRV), and symbol
 */

import java.util.ArrayList;

public class Currencies {
    private final static int CURRENCY_NAME = 0;
    private final static int CURRENCY_ABRV = 1;
    private final static int CURRENCY_SYMBOL = 2;

    private String[][] currenciesAndAbrvs = new String[][] {
            {"United States dollar", "Euro", "Japanese yen", "Pound sterling", "Australian dollar",
                    "Canadian dollar", "Swiss franc", "Renminbi", "Swedish krona", "New Zealand dollar",
                    "Mexican peso", "Singapore dollar", "Hong Kong dollar", "Norwegian krone", "South Korean won",
                    "Turkish lira", "Russian ruble", "Indian rupee", "Brazilian real", "South African rand"
            },
            {"USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD",
                    "MXN", "SGD", "HKD", "NOK", "KRW", "TRY", "RUB", "INR", "BRL", "ZAR"
            },
            {"$", "€", "¥", "£", "$", "$", "Fr", "元", "kr", "$",
                    "$", "$", "$", "kr", "₩", "₺", "\u20BD", "₹", "$", "R"
            }
    };

    Currencies() {
    }

    // creates the currencies to be displayed by the spinner widget
    public ArrayList getList() {
        ArrayList<String> currencies = new ArrayList<>();

        for (int i = 0; i < currenciesAndAbrvs[CURRENCY_NAME].length; i++) {
            currencies.add(currenciesAndAbrvs[CURRENCY_NAME][i] + " - "
                    + currenciesAndAbrvs[CURRENCY_ABRV][i]
                    + " (" + currenciesAndAbrvs[CURRENCY_SYMBOL][i]
                    + ")");
        }

        return currencies;
    }

    public String getCurrencyName(int pos) {
        return currenciesAndAbrvs[CURRENCY_NAME][pos];
    }

    public String getCurrencyAbrv(int pos) {
        return currenciesAndAbrvs[CURRENCY_ABRV][pos];
    }

    public String getCurrencySymbol(int pos) {
        return currenciesAndAbrvs[CURRENCY_SYMBOL][pos];
    }
}
