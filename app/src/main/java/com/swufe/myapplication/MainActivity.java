package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView out;
    EditText edit;
/**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        out=(TextView)findViewById(R.id.re);
        edit=(EditText)findViewById(R.id.inp);
    }
    public void btn(View v){
        String str=edit.getText().toString();
        float t=Float.parseFloat(str);
        double c=t*1.8+32;
        out.setText("结果为"+c);
    }
    **/
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        out=(TextView)findViewById(R.id.img);
    }
    public void btn1(View view){
        i=i+1;
        out.setText("得分为"+i);
    }
    public void btn2(View view){
        i=i+2;
        out.setText("得分为"+i);
    }
    public void btn3(View view){
        i=i+3;
        out.setText("得分为"+i);
    }
    public void btn4(View view){
        i=0;
        out.setText("得分为"+i);
    }

}
