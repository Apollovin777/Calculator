package com.bignerdranch.android.calculator;

import com.bignerdranch.android.calculator.Calculation;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class CalcPreferences {
    private static final String PREF_CALC_OBJECT = "CALC_OBJECT";

    private static final String BUFFER = "BUFFER";
    private static final String OUTPUT = "OUTPUT";
    private static final String CURRENT_OPERATION = "operation";
    private static final String CONTAIN_OPERAND = "containOperand";
    private static final String CLEAR = "clear";


    enum Season { WINTER, SPRING, SUMMER, AUTUMN }

    public static Calculation getStoredCalc(Context context,Calculation.CalculateListener listener) {

//        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
//        Calculation calc = new Calculation(listener);
//
//        String operation = pref.getString(CURRENT_OPERATION,null);
//        if (operation != null) {
//            calc.setOperation(Calculation.OPERATION.valueOf(operation));
//        } else{
//            calc.setOperation(null);
//        }
//
//        String buffer = pref.getString(BUFFER,null);
//        if (buffer != null) {
//            calc.setBuffer(new StringBuffer(buffer));
//        }else{
//            calc.setBuffer(new StringBuffer());
//        }
//
//        String output = pref.getString(OUTPUT,null);
//        if (output != null) {
//            calc.setOutput(new StringBuffer(output));
//        } else {
//            calc.setOutput(new StringBuffer());
//        }
//
//        boolean containOperand = pref.getBoolean(CONTAIN_OPERAND,false);
//        calc.setContainOperand(containOperand);
//
//        boolean clear = pref.getBoolean(CLEAR,false);
//        calc.setClear(clear);

        return null;
    }
    public static void setStoredCalc(Context context, Calculation object) {

//        String operation = null;
//                if (object.getOperation() != null){
//                    operation = object.getOperation().toString();
//                }
//
//        PreferenceManager.getDefaultSharedPreferences(context)
//                .edit()
//                .putString(BUFFER, object.getBuffer().toString())
//                .putString(OUTPUT, object.getOutput().toString())
//                .putString(CURRENT_OPERATION, operation)
//                .putBoolean(CONTAIN_OPERAND, object.isContainOperand())
//                .putBoolean(CLEAR, object.isClear())
//                .apply();
    }
}

