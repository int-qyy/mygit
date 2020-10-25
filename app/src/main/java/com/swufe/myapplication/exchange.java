package com.swufe.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class exchange extends AppCompatActivity {


    TextView out;
    EditText edit;


    float dollarRate=(float)0.147;
    float EuroRate=(float)0.122;
    float WonRate=(float)144.72;
    double RMB;
    private static final String TAG="exchange";

    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);
        out=(TextView)findViewById(R.id.textView);
        Intent second=new Intent(this,result.class);
        edit=(EditText)findViewById(R.id.inp);
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putFloat("Dollar_rate",dollarRate);
        editor.putFloat("Euro_rate",EuroRate);
        editor.putFloat("Won_rate",WonRate);
        editor.apply();

        URL url = null;
        try{
            url=new URL("www.usd-cny.com/bankofchina.html");
            HttpURLConnection http=(HttpURLConnection) url.openConnection();
            InputStream in= http.getInputStream() ;


            String html =inputStream2String(in);
            Log.i(TAG,"run:html="+html);



        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void btn(View btn){
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate=sharedPreferences.getFloat("Dollar_rate",0.0f);
        EuroRate=sharedPreferences.getFloat("Euro_rate",0.0f);
        WonRate=sharedPreferences.getFloat("Won_rate",0.0f);

        if(edit.toString()==null ||edit.toString().equals("")) {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }else{
            String str=edit.getText().toString();
            float t=Float.parseFloat(str);

            if(btn.getId()==R.id.dollar){
                float b=t*dollarRate;
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
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate=sharedPreferences.getFloat("Dollar_rate",0.0f);
        EuroRate=sharedPreferences.getFloat("Euro_rate",0.0f);
        WonRate=sharedPreferences.getFloat("Won_rate",0.0f);
        Intent intent = new Intent(this, result.class);
        //使用Bundle一体化的传输数据
        Bundle bundle = new Bundle();
        bundle.putFloat("Dollar_Rate", dollarRate);
        bundle.putFloat("Euro_Rate", EuroRate);
        bundle.putFloat("Won_Rate", WonRate);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public void turn(View view){
        Intent intent=new Intent(exchange.this,list.class);
        startActivity(intent);
    }
    public void grid(View view){
        Intent intent=new Intent(exchange.this, DeleteList.class);
        startActivity(intent);
    }

    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"utf-8");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1 && resultCode==2){
            Bundle bundle=data.getExtras();
            dollarRate=bundle.getFloat("Dollar_rate",0.0f);
            EuroRate=bundle.getFloat("Euro_rate",0.0f);
            WonRate=bundle.getFloat("Won_rate",0.0f);
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
