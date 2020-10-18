package com.swufe.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView out1;
    TextView out2;
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
    int j=0;
    /**
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        out1=(TextView)findViewById(R.id.out1);
        out2=(TextView)findViewById(R.id.out2);
    }
    public void btn1(View view){
        i=i+3;
        out1.setText(""+i);
    }
    public void btn2(View view){
        i=i+2;
        out1.setText(""+i);
    }
    public void btn3(View view){
        i=i+1;
        out1.setText(""+i);
    }

    public void btn5(View view){
        j=j+3;
        out2.setText(""+j);
    }
    public void btn6(View view){
        j=j+2;
        out2.setText(""+j);
    }
    public void btn7(View view){
        j=j+1;
        out2.setText(""+j);
    }
    public void btn4(View view){
        i=0;
        j=0;
        out1.setText(""+i);
        out2.setText(""+j);
    }
     **/

    float DollarRate=(float)0.147;
    float EuroRate=(float)0.122;
    float WonRate=(float)144.72;
    double RMB;
    private static final String TAG="MainActivity";



    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        out = (TextView) findViewById(R.id.textView);
        Intent second = new Intent(this, result.class);
        edit = (EditText) findViewById(R.id.inp);
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("Dollar_rate", DollarRate);
        editor.putFloat("Euro_rate", EuroRate);
        editor.putFloat("Won_rate", WonRate);
        editor.commit();

    }


    public void btn(View btn){
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        DollarRate=sharedPreferences.getFloat("Dollar_rate",0.0f);
        EuroRate=sharedPreferences.getFloat("Euro_rate",0.0f);
        WonRate=sharedPreferences.getFloat("Won_rate",0.0f);

        if(edit.toString()==null ||edit.toString().equals("")) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }else{
            String str=edit.getText().toString();
            float t=Float.parseFloat(str);

            if(btn.getId()==R.id.dollar){
                float b=t*DollarRate;
                String str1=String.format("%.4f",b).toString();
                out.setText(str1+"dollar");
            }else if(btn.getId()==R.id.euro) {
                    float b = t *EuroRate;
                    String str1 = String.format("%.4f", b).toString();
                    out.setText(str1 + "euro");
            }else if(btn.getId()==R.id.won) {
                float b = t *WonRate;
                String str1 = String.format("%.4f", b).toString();
                out.setText(str1 + "won");
            }
        }
    }

    public void onConfig(View view) {
        Intent intent = new Intent(this, result.class);
        //使用Bundle一体化的传输数据
        Bundle bundle = new Bundle();

        bundle.putFloat("Dollar_Rate", DollarRate);
        bundle.putFloat("Euro_Rate", EuroRate);
        bundle.putFloat("Won_Rate", WonRate);

        intent.putExtras(bundle);

        Log.i(TAG,"Dollar_Rate"+DollarRate);
        Log.i(TAG,"Euro_Rate"+EuroRate);
        Log.i(TAG,"Won_Rate"+ WonRate);

        startActivityForResult(intent, 1);
    }
    public void turn(View view){
        Intent intent=new Intent(MainActivity.this,list.class);
        startActivity(intent);
    }
    public void grid(View view){
        Intent intent=new Intent(MainActivity.this,GridViewList.class);
        startActivity(intent);
    }

@Override
  protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1 && resultCode==1){
            Bundle bundle=data.getExtras();
            DollarRate=bundle.getFloat("Dollar_rate",0.0f);
            EuroRate=bundle.getFloat("Euro_rate",0.0f);
            WonRate=bundle.getFloat("Won_rate",0.0f);
            Log.i(TAG,"onActivityResult: dollarRate=" + DollarRate);
            Log.i(TAG,"onActivityResult: euroRate=" + EuroRate);
            Log.i(TAG,"onActivityResult: wonRate=" + WonRate);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.first_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_set){

        }
        return super.onOptionsItemSelected(item);
    }




}
