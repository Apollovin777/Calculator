package com.bignerdranch.android.calculator;

import android.content.Context;
import android.preference.PreferenceManager;

import com.bignerdranch.android.calculator.Calculation;
import com.google.gson.Gson;

public class CalcPreferences {
    private static final String PREF_CALC_OBJECT = "CALC_OBJECT";

    public static Calculation getStoredCalc(Context context) {

        Gson gson = new Gson();
        String json = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_CALC_OBJECT, "");
        Calculation obj = gson.fromJson(json, Calculation.class);


        return obj;
    }
    public static void setStoredCalc(Context context, Calculation object) {

        Gson gson = new Gson();
        String json = gson.toJson(object);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_CALC_OBJECT, json)
                .apply();
    }
}
