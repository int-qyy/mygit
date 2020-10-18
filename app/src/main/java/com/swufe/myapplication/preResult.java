package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class preResult extends AppCompatActivity {
    TextView type,result;
    EditText rmb;
    float change;
    private static final String TAG="preResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_result);
        Intent intent = getIntent();
        float change = 0;
        Bundle bundle = intent.getExtras();
        change= bundle.getFloat("Dollar_Rate", 0.0f);
        rmb=(EditText) findViewById(R.id.input);
        result=(TextView)findViewById(R.id.out2);
        if(rmb.toString()==null ||rmb.toString().equals("")) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }else {
            String str = rmb.getText().toString();
            float t = Float.parseFloat(str);
            double b = t * change;
            String str1 = String.format("%.4f", b).toString();
            result.setText(str1 + "  ");

        }
    }
}
