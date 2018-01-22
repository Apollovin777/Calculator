package com.bignerdranch.android.calculator;

import android.location.Location;
import android.util.Log;

public class Calculation {
    private static final String TAG = "Calculation";

    private OPERATION operation;
    private StringBuffer mBuffer;
    private Double operand_1;
    private Double operand_2;
    private CalculateListener mListener;
    private boolean mContainOperand;


    public Calculation(CalculateListener listener) {
        mBuffer = new StringBuffer();
        this.mListener = listener;

    }

    public interface CalculateListener {
        void updateView(String value);
    }

    public enum OPERATION {
        PLUS, MINUS, MULTIPLY, DIVIDE
    }

    public void addData(String s) {
        if (mContainOperand && s.matches("[0-9]")) {
            mBuffer = new StringBuffer();
            mContainOperand = false;
        } else {
            mContainOperand = false;
        }
        switch (s) {
            case "+":
                if (checkSecondOperation()) {
                    returnResult();
                }
                if (operation == null) {
                    mBuffer.append(s);
                }
                operation = OPERATION.PLUS;
                mListener.updateView(mBuffer.toString());
                break;
            case "-":
                if (checkSecondOperation()) {
                    returnResult();
                }
                if (operation == null) {
                    mBuffer.append(s);
                }
                operation = OPERATION.MINUS;
                mListener.updateView(mBuffer.toString());
                break;
            case "*":
                if (checkSecondOperation()) {
                    returnResult();
                }
                if (operation == null) {
                    mBuffer.append(s);
                }
                operation = OPERATION.MULTIPLY;
                mListener.updateView(mBuffer.toString());
                break;
            case "/":
                if (checkSecondOperation()) {
                    returnResult();
                }
                if (operation == null) {
                    mBuffer.append(s);
                }
                operation = OPERATION.DIVIDE;
                mListener.updateView(mBuffer.toString());
                break;
            case "_":
                getLastOperIdex();
                break;
            case "=":
                returnResult();
                break;
            default:
                mBuffer.append(s);
                mListener.updateView(mBuffer.toString());
        }

    }

    private void getLastOperIdex(){
        int index = mBuffer.lastIndexOf("-");
        if (mBuffer.lastIndexOf("+")>index)
            index = mBuffer.lastIndexOf("+");
        if (mBuffer.lastIndexOf("*")>index)
            index = mBuffer.lastIndexOf("*");
        if (mBuffer.lastIndexOf("/")>index)
            index = mBuffer.lastIndexOf("/");
        if(index==-1) {
            mBuffer.insert(0,"_");
            mListener.updateView(mBuffer.toString());
            return;
        }
        mBuffer.insert(index+1,"_");
        mListener.updateView(mBuffer.toString().replace("_","-"));

    }

    private void parseOperator() {
        if (operand_1 == null) {
            operand_1 = Double.parseDouble(mBuffer.toString());
        } else {
            operand_2 = Double.parseDouble(mBuffer.toString());
        }
    }

    public void returnResult() {
        String[] operandArray = mBuffer.toString().split("[+\\-*\\/]");

        if (operandArray.length == 1) {
            mListener.updateView(operandArray[0].toString());
            mBuffer = new StringBuffer();
            return;
        }

        for (int i = 0;i<operandArray.length;i++ ) {
            operandArray[i] = operandArray[i].replace("_","-");
        }

        operand_1 = Double.parseDouble(operandArray[0]);
        operand_2 = Double.parseDouble(operandArray[1]);
        Double result = calculate();
        //operand_1 = result;
        mListener.updateView(mBuffer.toString() + " = " + result.toString());
        mBuffer = new StringBuffer(result.toString().replace("-","_"));
        mContainOperand = true;
        operation = null;


    }

    private Double calculate() {
        switch (operation) {
            case PLUS:
                return operand_1 + operand_2;
            case MINUS:
                return operand_1 - operand_2;
            case MULTIPLY:
                return operand_1 * operand_2;
            case DIVIDE:
                return operand_1 / operand_2;
        }
        return null;
    }

    private boolean checkSecondOperation() {
        return mBuffer.toString().contains("+") ||
                mBuffer.toString().contains("-") ||
                mBuffer.toString().contains("*") ||
                mBuffer.toString().contains("/");

    }


}
