package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class preResult extends AppCompatActivity {
    TextView type,result;
    EditText rmb;
    private static final String TAG="preResult";
    float rate;
    DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_result);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String itemTitle = bundle.getString("itemTitle","");
        rate = bundle.getFloat("itemDetail",0.1f);
        Log.i(TAG,"oneCreate:itemTitle = " + itemTitle);
        Log.i(TAG,"oneCreate:itemDetail = " + rate);

        rmb=(EditText) findViewById(R.id.inprmb);
        result=(TextView)findViewById(R.id.result);
        type=(TextView)findViewById(R.id.type);
        df = new DecimalFormat( "0.00");//设置double类型小数点后位数格式
        type.setText(itemTitle);

    }

    public void convert(View btn){
        String str = rmb.getText().toString();
        if(str == null || str.equals("") || str.equals(R.string.hint)){//no input
            Toast.makeText(this, "请输入人民币金额", Toast.LENGTH_SHORT).show();
        }else{
            float f = Float.parseFloat(str);
            float f1 = f * rate;
            Log.i(TAG, "rate====== "+ rate);
            String s = String.valueOf(df.format(f1));
            String str1 = rmb.getText().toString() + " 人民币 = " + s + " " + type.getText();
            result.setText(str1);
        }
    }
}

