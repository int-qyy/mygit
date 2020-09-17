package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class calculation extends AppCompatActivity {
    TextView out;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        out=(TextView)findViewById(R.id.img);
    }
    public void btn1(View view){
        i=i+1;
        out.setText("Team A得分为"+i);
    }
    public void btn2(View view){
        i=i+2;
        out.setText("Team A得分为"+i);
    }
    public void btn3(View view){
        i=i+3;
        out.setText("Team A得分为"+i);
    }

    public void btn4(View view) {
        i=0;
        out.setText("Team A得分为"+i);
    }
}
