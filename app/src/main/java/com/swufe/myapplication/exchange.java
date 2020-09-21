package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class exchange extends AppCompatActivity {


    TextView out;
    EditText edit;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        out=(TextView)findViewById(R.id.re);
        edit=(EditText)findViewById(R.id.inp);
    }

    public void btn1(View view){
        String str=edit.getText().toString();
        float t=Float.parseFloat(str);
        double c=t*6.8;
        out.setText("美元汇率"+c);
    }
    public void btn2(View view){
        String str=edit.getText().toString();
        float t=Float.parseFloat(str);
        double c=t*8.04;
        out.setText("欧元汇率"+c);
    }
    public void btn3(View view){
        String str=edit.getText().toString();
        float t=Float.parseFloat(str);
        double c=t*4.4;
        out.setText("澳元汇率"+c);
    }
}
