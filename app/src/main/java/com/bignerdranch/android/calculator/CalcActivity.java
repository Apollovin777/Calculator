package com.bignerdranch.android.calculator;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener,Calculation.CalculateListener {
    private static final String TAG = "TAGGG";
    private static final String KEY_INDEX = "INDEX";
    private Calculation mCalc;
    private boolean mTotalClear ;
    private TextView mTextTemp;
    private TextView mTextCurrent;
    private TextView mTextPrev;
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
    private Button mButtonDot;
    private Button mButtonBack;
    private String mText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_calc);
        mTotalClear = false;

        mTextTemp = findViewById(R.id.text_temp);
        mTextCurrent = findViewById(R.id.text_current);

        mTextPrev = findViewById(R.id.text_prev);
        mTextPrev.setMovementMethod(new ScrollingMovementMethod() {});

        mCalc = new Calculation(this);

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
        if (mTotalClear){
            mButtonClear.setText("AC");
        }else{
            mButtonClear.setText("C");
        }
        mButtonClear.setOnClickListener(this);

        mButtonSign = findViewById(R.id.button_sign);
        mButtonSign.setOnClickListener(this);

        mButtonDot = findViewById(R.id.button_dot);
        mButtonDot.setOnClickListener(this);

        mButtonBack = findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(this);
        Typeface font = Typeface.createFromAsset(getAssets(), "Quivira.otf");
        mButtonBack.setTypeface(font);
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.button_clear && mTotalClear){
            mCalc = new Calculation(this);
            mButtonClear.setText("C");
        }
        mButtonClear.setText("C");
        mTotalClear = false;

        switch (v.getId()) {
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
            case R.id.button_dot:
                mCalc.addData(".");
                break;
            case R.id.button_back:
                mCalc.addData("d");
                break;
            case R.id.button_clear:
                mCalc.addData("c");
                mTotalClear = true;
                mButtonClear.setText("AC");
                break;
        }
    }

    @Override
    public void updateView(String temp,String current, String prev) {

        String current_text = current.replace("_", "-");
        if (current_text=="")
            current_text = "0";
        if (mTextCurrent != null) {
            mTextCurrent.setText(current_text);
        }

        String temp_text = temp.replace("_","-");
        if (mTextTemp != null) {
            mTextTemp.setText(temp_text);
        }

        mTextPrev.setText(prev);
        if (mTextPrev != null){
            final Layout layout = mTextPrev.getLayout();
            if (layout != null) {
                int scrollDelta = layout.getLineBottom(mTextPrev.getLineCount() - 1)
                        - mTextPrev.getScrollY() - mTextPrev.getHeight();
                if (scrollDelta > 0)
                    mTextPrev.scrollBy(0, scrollDelta);
            }
        }
    }

}
