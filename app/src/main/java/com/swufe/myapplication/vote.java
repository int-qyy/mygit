package com.swufe.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class vote extends AppCompatActivity {

    private static final String TAG="vote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
    }

    public void onClick1(View btn){
        switch (btn.getId()){
            case R.id.btn_approve:
                new VoteTask().execute("赞成");
                break;
            case R.id.btn_object:
                new VoteTask().execute("赞成");
                break;
            case R.id.btn_abstain:
                new VoteTask().execute("赞成");
                break;
        }
    }
    private class VoteTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params) {
            for (String p : params) {
                Log.i(TAG, "doInBackground:" + p);
            }
            String ret = doVate(params[0]);
            return ret;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(vote.this, s, Toast.LENGTH_SHORT).show();
        }
    }

    public String executeHttpGet(){
        String result= null;
        URL url=null;
        HttpURLConnection connection=null;
        InputStreamReader in =null;
        try{
            url=new URL("https//10.0.2.2.8888/data/get/?/token=aalexzhou");
            connection=(HttpURLConnection)url.openConnection();
            in=new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(in);
            StringBuffer stringBuffer=new StringBuffer();
            String line=null;
            line=bufferedReader.readLine();
            while(line!=null){
                stringBuffer.append(line);

            }
            result=stringBuffer.toString();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
            if(in!=null){
                try{
                    in.close();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }



    private String doVate(String voteStr){
        String retStr="";
        Log.i("vote","doVate()voteStr"+voteStr);
        try{
            StringBuffer stringBuffer=new StringBuffer();
            stringBuffer.append("r=").append(URLEncoder.encode(voteStr,"utf-8"));


            byte[]data=stringBuffer.toString().getBytes();
            String urlPath="httpsS/192.";
            URL url=new URL(urlPath);
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",String.valueOf(data.length));
            OutputStream outputStream=httpURLConnection.getOutputStream();
            outputStream.write(data);
            int response=httpURLConnection.getResponseCode();
            if(response==HttpURLConnection.HTTP_OK){
                InputStream inputStream=httpURLConnection.getInputStream();
                retStr=inputStreamToString(inputStream);
                Log.i("vote","retStr"+retStr);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return retStr;
    }

    private String inputStreamToString(InputStream inputStream)
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
    /**
    public class DownloadFilesTask extends AsyncTask<URL,Integer,Long> {
        protected Long doInBackground(URL...urls){
            int count=urls.length;
            long totalSize=0;
            for(int i=0;i<count;i++){
                totalSize+=Downloader.downloadFile(urls[i]);
                publishProgress((int)((i/(float)count)*100));
                if(isCancelled())break;
            }
            return totalSize;
        }
        protected void onProgressUpdate(Integer...progress){
            setProgressPercent(progress[0]);
        }
        protected void onPostExecute(Long result){
            showDialog("Downliaded"+result+"bytes");
        }
    }
**/

    protected String doInBackground(String...params){
        for(String p:params){
            Log.i(TAG,"doInBackground:"+p);
        }
        String ret=doVate(params[0]);
        return ret;
    }
    
}