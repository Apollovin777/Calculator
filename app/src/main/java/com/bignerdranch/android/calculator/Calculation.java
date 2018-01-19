package com.bignerdranch.android.calculator;


import java.util.LinkedList;

public class Calculation {
    private OPERATION operation;
    private String[] list;

    public Calculation() {
        list = new String[2];
    }

    public enum OPERATION{
        PLUS, MINUS, MULTIPLY, DIVIDE
    }

    public void addOperand(String value){
        if(list[0]==null){
            list[0]=value;
        } else {
            list[1] = value;
        }
    }

    public void setOperation(OPERATION operation) {
        this.operation = operation;
    }

    private int parceInt(String value){
        return 0;
    }

    private float parceFloat(String value){
        return 0f;
    }

    public String getResult(){
        if (list[0]==null)
            return "0";

        if (operation == null) {
            return list[0];
        }

        if (operation != null && list[1]==null){
            list[1] = list[0];
            return this.getResult();
        }

        Double first = Double.parseDouble(list[0]);
        Double second = Double.parseDouble(list[1]);

        Double result=0d;
        switch (operation){
            case PLUS:
                result = first + second;
                break;
            case MINUS:
                result = first - second;
                break;
            case MULTIPLY:
                result = first * second;
                break;
            case DIVIDE:
                result = first / second;
                break;
        }
        list[0]=String.valueOf(result);
        list[1]=null;
        return String.valueOf(result);
    }




}
