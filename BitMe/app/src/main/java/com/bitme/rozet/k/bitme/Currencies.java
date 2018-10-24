package com.bitme.rozet.k.bitme;

import java.util.ArrayList;

public class Currencies {
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

    public ArrayList getList() {
        ArrayList<String> currencies = new ArrayList<>();

        for (int i = 0; i < currenciesAndAbrvs[0].length; i++) {
            currencies.add(currenciesAndAbrvs[0][i] + " - "
                    + currenciesAndAbrvs[1][i]
                    + " (" + currenciesAndAbrvs[2][i]
                    + ")");
        }

        return currencies;
    }

    public String getCurrencyName(int pos) {
        return currenciesAndAbrvs[0][pos];
    }
    
    public String getCurrencyAbrv(int pos) {
        return currenciesAndAbrvs[1][pos];
    }

    public String getCurrencySymbol(int pos) {
        return currenciesAndAbrvs[2][pos];
    }
}
