package com.bignerdranch.android.calculator;

import android.location.Location;
import android.util.Log;

public class Calculation {
    private static final String TAG = "Calculation";

    private OPERATION mOperation;
    private StringBuffer mBuffer;
    private StringBuffer mOutput;
    private Double operand_1;
    private Double operand_2;
    private CalculateListener mListener;
    private boolean mContainOperand;
    private boolean mClear;


    public Calculation(CalculateListener listener) {
        mBuffer = new StringBuffer();
        mOutput = new StringBuffer();
        this.mListener = listener;
        mClear = false;
    }

    public interface CalculateListener {
        void updateView(String value);
    }

    public enum OPERATION {
        PLUS, MINUS, MULTIPLY, DIVIDE
    }


    public void addData(String s) {
        if (mContainOperand && s.matches("[0-9.]")&& !checkSecondOperation()) {
            mBuffer = new StringBuffer();
            mContainOperand = false;
        } else {
            mContainOperand = false;
        }
        switch (s) {
            case "+":
                performOperations("+");
                mOperation = OPERATION.PLUS;
                break;
            case "-":
                performOperations("-");
                mOperation = OPERATION.MINUS;
                break;
            case "*":
                performOperations("*");
                mOperation = OPERATION.MULTIPLY;
                break;
            case "/":
                performOperations("/");
                mOperation = OPERATION.DIVIDE;
                break;
            case "_":
                getLastOperIndex();
                break;
            case "d":
                String character = String.valueOf(mBuffer.charAt(mBuffer.length()-1));
                if (character.matches("[+\\-*\\/]")){
                    mOperation = null;
                }
                mBuffer.deleteCharAt(mBuffer.length()-1);
                sendOutput(mBuffer.toString());
                break;
            case "=":
                returnResult();
                break;
            case ".":
                if(!mBuffer.toString().matches(".*[0-9]$")) {
                    mBuffer.append("0");
                }
                mBuffer.append(".");
                sendOutput(mBuffer.toString());
                break;
            case "c":
                if (mClear){
                    mBuffer = new StringBuffer();
                    mOutput = new StringBuffer();
                    mOperation = null;
                    mClear = false;
                    sendOutput("0");
                } else{
                    mClear = true;
                    mBuffer = new StringBuffer();
                    mOperation = null;
                    sendOutput("0");
                }
                break;
            default:
                mBuffer.append(s);
                sendOutput(mBuffer.toString());
                mClear = false;
        }

    }
    private void performOperations(String s){
        if (checkSecondOperation()) {
            returnResult();
        }
        if (mOperation == null) {
            mBuffer.append(s);
        } else{
            mBuffer.replace(mBuffer.length()-1,mBuffer.length(),s);
        }
        sendOutput(mBuffer.toString());
    }

    private void sendOutput(String val){
        mListener.updateView(mOutput.toString() + val);
    }

    private void getLastOperIndex(){
        int index = mBuffer.lastIndexOf("-");
        if (mBuffer.lastIndexOf("+")>index)
            index = mBuffer.lastIndexOf("+");
        if (mBuffer.lastIndexOf("*")>index)
            index = mBuffer.lastIndexOf("*");
        if (mBuffer.lastIndexOf("/")>index)
            index = mBuffer.lastIndexOf("/");
        if(index==-1) {
            mBuffer.insert(0,"_");
            sendOutput(mBuffer.toString());
            return;
        }
        mBuffer.insert(index+1,"_");
        sendOutput(mBuffer.toString().replace("_","-"));
    }

     public void returnResult() {

        String[] operandArray = mBuffer.toString().split("[+\\-*\\/]");

        if (operandArray.length == 1) {
            sendOutput(operandArray[0].toString());
            mBuffer = new StringBuffer();
            return;
        }

        for (int i = 0;i<operandArray.length;i++ ) {
            operandArray[i] = operandArray[i].replace("_","-");
        }

        Double operand_1 = Double.parseDouble(operandArray[0]);
        Double operand_2 = Double.parseDouble(operandArray[1]);
        Double result = calculate(operand_1, operand_2);
        result = Calculation.round(result,8);
        String strResult;
        if ((result == Math.floor(result)) && !Double.isInfinite(result)) {
            strResult = result.toString().substring(0,result.toString().length()-2);
        } else {
            strResult = result.toString();
        }


        //operand_1 = result;
        //mListener.updateView(mBuffer.toString() + "=" + strResult + "\n");
        mOutput.append(mBuffer.toString() + "=" + strResult + "\n");
        sendOutput(strResult);
        mBuffer = new StringBuffer(strResult.replace("-","_"));
        mContainOperand = true;
         mOperation = null;
    }

    private Double calculate(Double operand_1, Double operand_2) {
        switch (mOperation) {
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
        String operation = "";
         if (mBuffer.toString().contains("+"))
             operation = "+";
         if (mBuffer.toString().contains("-"))
             operation = "-";
        if (mBuffer.toString().contains("*"))
            operation = "*";
        if (mBuffer.toString().contains("/"))
            operation = "/";
        return !mBuffer.toString().endsWith(operation);
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
