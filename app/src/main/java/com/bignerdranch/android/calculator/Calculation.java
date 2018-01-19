package com.bignerdranch.android.calculator;


import java.util.LinkedList;

public class Calculation {
    private OPERATION operation;
    private LinkedList<String> list;

    public Calculation() {
        list = new LinkedList<>();
    }

    public enum OPERATION{
        PLUS, MINUS, MULTIPLY, DIVIDE
    }

    public void addOperand(String value){
        list.add(value);
    }

    public OPERATION getOperation() {
        return operation;
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
        if (list.size()==0 || operation == null)
            return "0";

        if (list.size()==1 || list.get(1)=="") {
            return list.get(0);
        }

        Double first = Double.parseDouble(list.get(0));
        Double second = Double.parseDouble(list.get(1));

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
        list = new LinkedList<>();

        return String.valueOf(result);
    }

    


}
