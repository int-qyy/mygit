package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class result extends AppCompatActivity implements Runnable {

    EditText dollar,euro,won;
    float Dollar_rate,Euro_rate,Won_rate;
    private static final String TAG = "Result";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Dollar_rate= bundle.getFloat("Dollar_Rate", 0.0f);
        Euro_rate= bundle.getFloat("Euro_Rate", 0.0f);
        Won_rate = bundle.getFloat("Won_Rate", 0.0f);
        SharedPreferences sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        Dollar_rate=sharedPreferences.getFloat("Dollar_rate",0.0f);
        Euro_rate=sharedPreferences.getFloat("Euro_rate",0.0f);
        Won_rate=sharedPreferences.getFloat("Won_rate",0.0f);
        dollar=(EditText) findViewById(R.id.dollar);
        euro=(EditText) findViewById(R.id.euro);
        won=(EditText) findViewById(R.id.won);
        dollar.setText(String.valueOf(Dollar_rate));
        euro.setText(String.valueOf(Euro_rate));
        won.setText(String.valueOf(Won_rate));

        Thread thread=new Thread(this);
        thread.start();

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str =(String) msg.obj;
                    Log.i(TAG,"handleMessage:getMessage msg= "+str);
                    //show.setText(str);
                }
                super.handleMessage(msg);
            }
        };

    }




    public void btn(View v){

            Intent intent_save=getIntent();
            String str1 = dollar.getText().toString();
            float DollarRate = Float.parseFloat(str1);
            String str2 = euro.getText().toString();
            float EuroRate = Float.parseFloat(str2);
            String str3 = won.getText().toString();
            float WonRate = Float.parseFloat(str3);
            Bundle bdl = new Bundle();
            bdl.putFloat("Dollar_rate", DollarRate);
            bdl.putFloat("Euro_rate", EuroRate);
            bdl.putFloat("Won_rate", WonRate);
            Log.i(TAG,"setResult: dollarRate=" + DollarRate);
            Log.i(TAG,"setResult: euroRate=" + EuroRate);
            Log.i(TAG,"setResult: wonRate=" + WonRate);
            intent_save.putExtras(bdl);
            setResult(1,intent_save);

            SharedPreferences sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putFloat("Dollar_rate", DollarRate);
            editor.putFloat("Euro_rate", EuroRate);
            editor.putFloat("Won_rate", WonRate);
            editor.commit();
            finish();

    }
    @Override
    public void run(){
        Log.i(TAG,"run:run().....");
/**
        Message msg = handler.obtainMessage(5);
        msg.obj="Hello from run()";
        handler.sendMessage(msg);
**/

        URL url = null;
        try{
            url=new URL("https://www.usd-cny.com/bankofchina.htm");
            HttpsURLConnection http=(HttpsURLConnection) url.openConnection();
            InputStream in= http.getInputStream() ;
            String html =inputStream2String(in);
            Log.i(TAG,"run:html="+html);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String inputStream2String(InputStream inputStream)
            throws IOException{
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"GB2312" );
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }


}
