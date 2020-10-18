package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class result extends AppCompatActivity implements Runnable {

    EditText dollar,euro,won;
    float Dollar_rate,Euro_rate,Won_rate;
    private static final String TAG = "Result";
    Handler handler;
    String lastDate;
    String nowDate;
    SharedPreferences sharedPreferences;



    @Override
    public void run(){
        Log.i(TAG,"run:run().....");
/**

 **/
        //读取URL
        URL url = null;
        try{
            url=new URL("https://www.usd-cny.com/bankofchina.htm");
            /**
             HttpsURLConnection http=(HttpsURLConnection) url.openConnection();
             InputStream in= http.getInputStream() ;
             String html =inputStream2String(in);
             Log.i(TAG,"run:html="+html);
             **/
            //解析URL
            Document document= Jsoup.connect(String.valueOf(url)).get();
            Log.i(TAG,"run:"+document.title());
            Elements tables= (Elements) document.getElementsByTag("table");
            Element table6= (Element) tables.get(0);
            Elements tds= (Elements) table6.getElementsByTag("td");
            Bundle bundle=new Bundle();
            for(int i=0;i<tds.size();i+=6){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                String str1=td1.text();
                String val=td2.text();
                //Log.i(TAG,"run:"+str1+"==>"+val);
                float v=100f/Float.parseFloat(val);
                if(str1.equals("美元")){
                    Dollar_rate=v;
                    Log.i(TAG,"get: dollarRate=" + Dollar_rate);
                    bundle.putFloat("Dollar_rate", Dollar_rate);

                }else if(str1.equals("欧元")){
                    Euro_rate=v;
                    Log.i(TAG,"get: euroRate=" + Euro_rate);
                    bundle.putFloat("Euro_rate", Euro_rate);

                }else if(str1.equals("韩元")){
                    Won_rate=v;
                    Log.i(TAG,"get: wonRate=" + Won_rate);
                    bundle.putFloat("Won_rate", Won_rate);
                }


            }

            Message msg = handler.obtainMessage(5);
            msg.obj=bundle;
            handler.sendMessage(msg);
            Log.i(TAG,"get: dollarRate=" + Dollar_rate+"get: euroRate=" + Euro_rate+"get: wonRate=" + Won_rate);

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Dollar_rate= bundle.getFloat("Dollar_Rate", 0.0f);
        Euro_rate= bundle.getFloat("Euro_Rate", 0.0f);
        Won_rate = bundle.getFloat("Won_Rate", 0.0f);
        sharedPreferences=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        PreferenceManager.getDefaultSharedPreferences(this);
        /**
        Dollar_rate=sharedPreferences.getFloat("Dollar_rate",0.0f);
        Euro_rate=sharedPreferences.getFloat("Euro_rate",0.0f);
        Won_rate=sharedPreferences.getFloat("Won_rate",0.0f);
         **/
        dollar=(EditText) findViewById(R.id.dollar);
        euro=(EditText) findViewById(R.id.euro);
        won=(EditText) findViewById(R.id.won);
        dollar.setText(String.valueOf(Dollar_rate));
        euro.setText(String.valueOf(Euro_rate));
        won.setText(String.valueOf(Won_rate));
        Date toDate=new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = format.format(toDate);
        lastDate = sharedPreferences.getString("last_date", "");
        Log.i(TAG, "onCreate: 现在时间"+nowDate);
        if (!lastDate.equals(nowDate)) {
            Log.i(TAG, "onCreate: 需要更新，上次更新时间"+lastDate+"本次时间"+nowDate);
            Thread t = new Thread(result.this);
            t.start();

            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 5) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Bundle bundle = (Bundle) msg.obj;
                        Dollar_rate = bundle.getFloat("dollar_rate");
                        Euro_rate = bundle.getFloat("euro_rate");
                        Won_rate = bundle.getFloat("won_rate");
                        editor.putFloat("dollar_rate", Dollar_rate);
                        editor.putFloat("euro_rate", Euro_rate);
                        editor.putFloat("won_rate", Won_rate);
                        editor.putString("last_date", nowDate);
                        editor.apply();
                    }
                }
            };

        }


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
