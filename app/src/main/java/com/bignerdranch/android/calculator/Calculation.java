package com.bignerdranch.android.calculator;

import android.location.Location;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.nio.Buffer;

public class Calculation  {
    private static final String TAG = "Calculation";

    @SerializedName("operation")
    private OPERATION mOperation;
    @SerializedName("buffer")
    private StringBuffer mBuffer;
    @SerializedName("output")
    private StringBuffer mOutput;
    @SerializedName("containOperand")
    private boolean mContainOperand;
    @SerializedName("clear")
    private boolean mClear;

    private CalculateListener mListener;

    public OPERATION getOperation() {
        return mOperation;
    }

    public void setOperation(OPERATION operation) {
        mOperation = operation;
    }

    public StringBuffer getBuffer() {
        return mBuffer;
    }

    public void setBuffer(StringBuffer buffer) {
        mBuffer = buffer;
    }

    public StringBuffer getOutput() {
        return mOutput;
    }

    public void setOutput(StringBuffer output) {
        mOutput = output;
    }

    public CalculateListener getListener() {
        return mListener;
    }

    public void setListener(CalculateListener listener) {
        mListener = listener;
    }

    public boolean isContainOperand() {
        return mContainOperand;
    }

    public void setContainOperand(boolean containOperand) {
        mContainOperand = containOperand;
    }

    public boolean isClear() {
        return mClear;
    }

    public void setClear(boolean clear) {
        mClear = clear;
    }


    public Calculation(CalculateListener listener) {
        mBuffer = new StringBuffer();
        mOutput = new StringBuffer();
        this.mListener = listener;
        mClear = false;
        mContainOperand = false;
    }

    public interface CalculateListener {
        void updateView(String value);
    }

    public enum OPERATION {
        PLUS, MINUS, MULTIPLY, DIVIDE
    }
    private boolean alreadyContainOperation() {
        String operation = "test";
        boolean result = false;
        if (mBuffer.toString().contains("+")) {
            operation = "+";
            result = true;
        }

        if (mBuffer.toString().contains("-")) {
            operation = "-";
            result = true;
         }
        if (mBuffer.toString().contains("*")) {
            operation = "*";
            result = true;
         }
        if (mBuffer.toString().contains("/")){
            operation = "/";
            result = true;
        }
        if (result) {
            return !isOperationLastChar(operation);
        }else {
            return false;
        }


    }

    private boolean isOperationLastChar(String value) {
        if (mBuffer.toString().endsWith(value)){
            return true;
        } else {
            return false;
        }
    }

    private boolean isOperationLastChar() {
        String lastChar = mBuffer.substring(mBuffer.length()-1);
        if (lastChar.matches("[+\\-*\\/]")) {
            return true;
        } else {
            return false;
        }
    }

    public void addData(String s) {
        if (mContainOperand && s.matches("[0-9.]") && !isOperationLastChar()) {
            mBuffer = new StringBuffer();
        }

        mContainOperand = false;

        switch (s) {
            case "+":
                if (alreadyContainOperation()) {
                    returnResult();
                }
                if (mOperation == null) {
                    mBuffer.append(s);
                } else{
                    mBuffer.replace(mBuffer.length()-1,mBuffer.length(),s);
                }
                sendOutput(mBuffer.toString());
                mOperation = OPERATION.PLUS;
                break;
            case "-":
                if (alreadyContainOperation()) {
                    returnResult();
                }
                if (mOperation == null) {
                    mBuffer.append(s);
                } else{
                    mBuffer.replace(mBuffer.length()-1,mBuffer.length(),s);
                }
                sendOutput(mBuffer.toString());
                mOperation = OPERATION.MINUS;
                break;
            case "*":
                if (alreadyContainOperation()) {
                    returnResult();
                }
                if (mOperation == null) {
                    mBuffer.append(s);
                } else{
                    mBuffer.replace(mBuffer.length()-1,mBuffer.length(),s);
                }
                sendOutput(mBuffer.toString());
                mOperation = OPERATION.MULTIPLY;
                break;
            case "/":
                if (alreadyContainOperation()) {
                    returnResult();
                }
                if (mOperation == null) {
                    mBuffer.append(s);
                } else{
                    mBuffer.replace(mBuffer.length()-1,mBuffer.length(),s);
                }
                sendOutput(mBuffer.toString());
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
        if (alreadyContainOperation()) {
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
