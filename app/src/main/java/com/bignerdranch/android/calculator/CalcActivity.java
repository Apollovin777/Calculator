package com.bignerdranch.android.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG = "CalcActivity";
    private Calculation mCalc;
    private String operand = "";
    private TextView mTextView;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButton0;
    private Button mButtonPlus;
    private Button mButtonMinus;
    private Button mButtonMultiply;
    private Button mButtonDivide;
    private Button mButtonEqual;
    private Button mButtonClear;
    private String mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        mTextView = findViewById(R.id.text_result);
        mButton0 = findViewById(R.id.button_0);
        mButton0.setOnClickListener(this);

        mButton1 = findViewById(R.id.button_1);
        mButton1.setOnClickListener(this);

        mButton2 = findViewById(R.id.button_2);
        mButton2.setOnClickListener(this);

        mButton3 = findViewById(R.id.button_3);
        mButton3.setOnClickListener(this);

        mButton4 = findViewById(R.id.button_4);
        mButton4.setOnClickListener(this);

        mButton5 = findViewById(R.id.button_5);
        mButton5.setOnClickListener(this);

        mButton6 = findViewById(R.id.button_6);
        mButton6.setOnClickListener(this);

        mButton7 = findViewById(R.id.button_7);
        mButton7.setOnClickListener(this);

        mButton8 = findViewById(R.id.button_8);
        mButton8.setOnClickListener(this);

        mButton9 = findViewById(R.id.button_9);
        mButton9.setOnClickListener(this);

        mButtonPlus = findViewById(R.id.button_plus);
        mButtonPlus.setOnClickListener(this);

        mButtonMinus = findViewById(R.id.button_minus);
        mButtonMinus.setOnClickListener(this);

        mButtonMultiply = findViewById(R.id.button_mult);
        mButtonMultiply.setOnClickListener(this);

        mButtonDivide = findViewById(R.id.button_div);
        mButtonDivide.setOnClickListener(this);

        mButtonEqual = findViewById(R.id.button_equal);
        mButtonEqual.setOnClickListener(this);

        mButtonClear = findViewById(R.id.button_clear);
        mButtonClear.setOnClickListener(this);

        mCalc = new Calculation();
    }

    @Override
    public void onClick(View v) {
        String temp;
        switch (v.getId()){
            case R.id.button_0:
                operand = operand + "0";
                mTextView.setText(operand);
                break;
            case R.id.button_1:
                operand = operand + "1";
                mTextView.setText(operand);
                break;
            case R.id.button_2:
                operand = operand + "2";
                mTextView.setText(operand);
                break;
            case R.id.button_3:
                operand = operand + "3";
                mTextView.setText(operand);
                break;
            case R.id.button_4:
                operand = operand + "4";
                mTextView.setText(operand);
                break;
            case R.id.button_5:
                operand = operand + "5";
                mTextView.setText(operand);
                break;
            case R.id.button_6:
                operand = operand + "6";
                mTextView.setText(operand);
                break;
            case R.id.button_7:
                operand = operand + "7";
                mTextView.setText(operand);
                break;
            case R.id.button_8:
                operand = operand + "8";
                mTextView.setText(operand);
                break;
            case R.id.button_9:
                operand = operand + "9";
                mTextView.setText(operand);
                break;
            case R.id.button_plus:
                mCalc.addOperand(operand);
                mCalc.setOperation(Calculation.OPERATION.PLUS);
                Log.i(TAG,"button_plus pressed");
                temp = (String) mTextView.getText();
                if (temp.endsWith("+")||temp.endsWith("-")||
                        temp.endsWith("*")||temp.endsWith("/")){

                    temp = temp.substring(0,temp.length()-2);
                    mTextView.setText(temp);
                }
                mTextView.setText(mTextView.getText() + " " + "+");
                operand = "";
                break;
            case R.id.button_minus:
                mCalc.addOperand(operand);
                mCalc.setOperation(Calculation.OPERATION.MINUS);
                temp = (String) mTextView.getText();
                if (temp.endsWith("+")||temp.endsWith("-")||
                        temp.endsWith("*")||temp.endsWith("/")){

                    temp = temp.substring(0,temp.length()-2);
                    mTextView.setText(temp);
                }
                mTextView.setText(mTextView.getText() + " " + "-");
                operand = "";
                break;
            case R.id.button_mult:
                mCalc.addOperand(operand);
                mCalc.setOperation(Calculation.OPERATION.MULTIPLY);
                temp = (String) mTextView.getText();
                if (temp.endsWith("+")||temp.endsWith("-")||
                        temp.endsWith("*")||temp.endsWith("/")){

                    temp = temp.substring(0,temp.length()-2);
                    mTextView.setText(temp);
                }
                mTextView.setText(mTextView.getText() + " " + "*");
                operand = "";
                break;
            case R.id.button_div:
                mCalc.addOperand(operand);
                mCalc.setOperation(Calculation.OPERATION.DIVIDE);
                temp = (String) mTextView.getText();
                if (temp.endsWith("+")||temp.endsWith("-")||
                        temp.endsWith("*")||temp.endsWith("/")){

                    temp = temp.substring(0,temp.length()-2);
                    mTextView.setText(temp);
                }
                mTextView.setText(mTextView.getText() + " " + "/");
                operand = "";
                break;
            case R.id.button_equal:
                mCalc.addOperand(operand);
                String result = mCalc.getResult();
                mTextView.setText(result);
                operand = "";
                break;
            case R.id.button_clear:
                mCalc = new Calculation();
                operand = "";
                mTextView.setText("0");
                break;
        }
    }
}
