package com.bignerdranch.android.calculator;

import java.text.DecimalFormat;

public class Calculation {
    private static final String TAG = "Calculation";

    private OPERATION mOperation;
    private StringBuffer mBuffer;
    private StringBuffer mHistory;
    private StringBuffer mTempResult;
    private Double mOperand_1;
    private Double mOperand_2;
    private CalculateListener mListener;
    private boolean isAfterResult;


    public Calculation(CalculateListener listener) {
        mBuffer = new StringBuffer();
        mHistory = new StringBuffer();
        mTempResult = new StringBuffer();
        this.mListener = listener;
        isAfterResult = false;

    }

    public interface CalculateListener {
        void updateView(String temp, String current, String prev);
    }

    public enum OPERATION {
        PLUS, MINUS, MULTIPLY, DIVIDE
    }


    public void addData(String value) {
        if (isAfterResult) {
            addToHistory(mBuffer.toString());
            if (value.matches("[+\\-*\\/]")) {
                mBuffer = new StringBuffer(String.valueOf(mOperand_1));
            } else {
                mBuffer = new StringBuffer();
                mOperand_1 = null;
            }
            isAfterResult = false;
        }

        if (value.matches("[+\\-*\\/]")) {
            handleOperation(value);
        } else {
            switch (value) {
                case "d":
                    if (mBuffer.length() > 0) {
                        mBuffer.deleteCharAt(mBuffer.length() - 1);
                    }
                    break;
                case "=":
                    resultOper();
                    break;
                case ".":
                    addPeriod();
                    break;
                case "c":
                    clear();
                    break;
                default:
                    addDigit(value);
            }
        }
        mListener.updateView(getTempResultText(value), getCurrentText(value), mHistory.toString());
    }

    private void addToHistory(String value){
        mHistory.append("\n" + value);
        if (isAfterResult){
            mHistory.append("\n"+ "--------------------------");
        }
    }

    private void clear() {
        mBuffer = new StringBuffer();
        mOperand_1 = null;
        mOperand_2 = null;
        mOperation = null;
        isAfterResult = false;
    }

    private String getCurrentText(String value) {

            if (value == "=" || isAfterResult) {
                return "= " + mBuffer.toString();
            } else {
                return mBuffer.toString();
            }
    }

    private void addPeriod(){
        if (mBuffer.length()==0){
            mBuffer.append("0"+".");
            return;
        }

        if (mBuffer.toString().contains(".")){
            return;
        }

        if (getBufferLastChar().matches("[0-9]")) {
            mBuffer.append(".");
        }
        else {
            mBuffer.append("0"+".");
        }
    }

    private String getBufferLastChar(){
        if (mBuffer.length()>0){
            char temp = mBuffer.charAt(mBuffer.length()-1);
            return String.valueOf(temp);
        }
        else {
            return "";
        }
    }


    private String getTempResultText(String value) {

        if (isAfterResult) {
            return "";
        }

        if (value == "c"){
            return "";
        }

        if (mOperand_1 == null) {
            return "= " + mBuffer.toString();
        }

        if (mOperation == null) {
            return "= " + mBuffer.toString();
        }

        if (mBuffer.length() < 2) {
            return "= " + formatDouble(mOperand_1);
        } else {
            Double tempResult;
            tempResult = calculate(mOperand_1, Double.parseDouble(mBuffer.toString().substring(1)));
            return "= " + formatDouble(tempResult);
        }

    }

    private String formatDouble(Double value){
        Double result = round(value,9);
        DecimalFormat df = new DecimalFormat("#.#########");
        return df.format(result);
    }

    private void addDigit(String value) {
        mBuffer.append(value);
    }



    private void handleOperation(String value) {
        if (mOperation == null) {
            mOperand_1 = Double.parseDouble(mBuffer.toString());
            addToHistory(String.valueOf(mOperand_1));
            mBuffer = new StringBuffer("");
            mBuffer.append(value);
            mOperation = getOperation(value);
        } else {
            if (mBuffer.length() <= 1) {
                mOperation = getOperation(value);
                mBuffer = new StringBuffer();
                mBuffer.append(value);
            } else {
                mOperand_2 = Double.parseDouble(mBuffer.substring(1));
                mOperand_1 = calculate(mOperand_1, mOperand_2);
                addToHistory(formatDouble(mOperand_1));
                addToHistory(getSign(mOperation) + formatDouble(mOperand_2));
                mOperation = getOperation(value);
                mBuffer = new StringBuffer("");
                mBuffer.append(value);
            }
        }
    }

    private void resultOper() {
        if (!isAfterResult) {
             if (mOperation != null) {
                mOperand_2 = Double.parseDouble(mBuffer.substring(1));
                addToHistory(getSign(mOperation) + formatDouble(mOperand_2));
                mOperand_1 = calculate(mOperand_1, mOperand_2);
                mOperation = null;
                mBuffer = new StringBuffer(formatDouble(mOperand_1));
                isAfterResult = true;
            }
        }
    }

    private OPERATION getOperation(String value) {
        OPERATION oper = null;
        switch (value) {
            case "+":
                oper = OPERATION.PLUS;
                break;
            case "-":
                oper = OPERATION.MINUS;
                break;
            case "*":
                oper = OPERATION.MULTIPLY;
                break;
            case "/":
                oper = OPERATION.DIVIDE;
                break;
        }
        return oper;
    }

    private String getSign(OPERATION operation){
        String sign = "";
        switch (operation) {
            case PLUS:
                sign = "+";
                break;
            case MINUS:
                sign = "-";
                break;
            case MULTIPLY:
                sign = "*";
                break;
            case DIVIDE:
                sign = "/";
                break;
        }
        return sign;
    }


    private Double calculate(Double operand_1, Double operand_2) {
        Double result = null;
        switch (mOperation) {
            case PLUS:
                result = operand_1 + operand_2;
                break;
            case MINUS:
                result = operand_1 - operand_2;
                break;
            case MULTIPLY:
                result = operand_1 * operand_2;
                break;
            case DIVIDE:
                result = operand_1 / operand_2;
                break;
        }
        return result;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
