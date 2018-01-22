package com.bignerdranch.android.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener,Calculation.CalculateListener{
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
    private Button mButtonSign;
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

        mButtonSign = findViewById(R.id.button_sign);
        mButtonSign.setOnClickListener(this);

        mCalc = new Calculation(this);
    }

    @Override
    public void onClick(View v) {
        String temp;
        switch (v.getId()){
            case R.id.button_0:
                mCalc.addData("0");
                break;
            case R.id.button_1:
                mCalc.addData("1");
                break;
            case R.id.button_2:
                mCalc.addData("2");
                break;
            case R.id.button_3:
                mCalc.addData("3");
                break;
            case R.id.button_4:
                mCalc.addData("4");
                break;
            case R.id.button_5:
                mCalc.addData("5");
                break;
            case R.id.button_6:
                mCalc.addData("6");
                break;
            case R.id.button_7:
                mCalc.addData("7");
                break;
            case R.id.button_8:
                mCalc.addData("8");
                break;
            case R.id.button_9:
                mCalc.addData("9");
                break;
            case R.id.button_plus:
                mCalc.addData("+");
                break;
            case R.id.button_minus:
                mCalc.addData("-");
                break;
            case R.id.button_mult:
                mCalc.addData("*");
                break;
            case R.id.button_div:
                mCalc.addData("/");
                break;
            case R.id.button_equal:
                mCalc.addData("=");
                break;
            case R.id.button_sign:
                mCalc.addData("_");
                break;
            case R.id.button_clear:
                mCalc = new Calculation(this);
                operand = "";
                mTextView.setText("0");
                break;
        }
    }

    @Override
    public void updateView(String value) {
        mTextView.setText(value.replace("_","-"));
    }
}
