package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class calculation extends AppCompatActivity {
    TextView out1;
    TextView out2;

    int i=0;
    int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        out1=(TextView)findViewById(R.id.out1);
        out2=(TextView)findViewById(R.id.out2);
    }
    public void btn1(View view){
        i=i+3;
        out1.setText(i);
    }
    public void btn2(View view){
        i=i+2;
        out1.setText(i);
    }
    public void btn3(View view){
        i=i+1;
        out1.setText(i);
    }

    public void btn5(View view){
        j=j+3;
        out2.setText(i);
    }
    public void btn6(View view){
        j=j+2;
        out2.setText(j);
    }
    public void btn7(View view){
        j=j+1;
        out2.setText(j);
    }
    public void btn4(View view){
        i=0;
        j=0;
        out1.setText(i);
        out2.setText(j);
    }
}
