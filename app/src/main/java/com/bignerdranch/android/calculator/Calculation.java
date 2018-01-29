package com.bignerdranch.android.calculator;

public class Calculation {
    private static final String TAG = "Calculation";

    private OPERATION mOperation;
    private StringBuffer mBuffer;
    private StringBuffer mPrevOperations;
    private StringBuffer mTempResult;
    private Double mOperand_1;
    private Double mOperand_2;
    private CalculateListener mListener;
    private boolean isAfterResult;


    public Calculation(CalculateListener listener) {
        mBuffer = new StringBuffer();
        mPrevOperations = new StringBuffer();
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
            if (value.matches("[+\\-*\\/]")) {
                mBuffer = new StringBuffer(String.valueOf(mOperand_1));
            } else {
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
                default:
                    addDigit(value);
            }
        }
        mListener.updateView(getTempResultText(value), getCurrentText(value), "");
    }

    private String getCurrentText(String value) {
        if (value == "=") {
            return "= " + mOperand_1;
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

        if (value == "=") {
            return "";
        }

        if (mOperand_1 == null) {
            return "= " + mBuffer.toString();
        }

        if (mOperation == null) {
            return "= " + mBuffer.toString();
        }

        if (mBuffer.length() < 2) {
            return "= " + mOperand_1;
        } else {
            Double tempResult;
            tempResult = calculate(mOperand_1, Double.parseDouble(mBuffer.toString().substring(1)));
            return "= " + String.valueOf(tempResult);
        }

    }

    private void addDigit(String value) {
        mBuffer.append(value);
    }

    private void handleOperation(String value) {
        if (mOperation == null) {
            mOperand_1 = Double.parseDouble(mBuffer.toString());
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
                mOperation = getOperation(value);
                mBuffer = new StringBuffer("");
                mBuffer.append(value);
            }
        }
    }

    private void resultOper() {
        mOperand_2 = Double.parseDouble(mBuffer.substring(1));
        mOperand_1 = calculate(mOperand_1, mOperand_2);
        mOperation = null;
        mBuffer = new StringBuffer();
        isAfterResult = true;
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
