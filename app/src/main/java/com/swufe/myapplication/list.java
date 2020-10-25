package com.swufe.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class list  extends AppCompatActivity implements Runnable,AdapterView.OnItemClickListener {

    private static final String TAG = "List_Activity";
    ListView list;
    Handler handler;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list);

       // super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_rate_list);

        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);


        Intent intent = getIntent();
        list = (ListView)findViewById(R.id.list);
        //开启子线程
        Thread t = new Thread(this);
        t.start();

        //线程间消息同步
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    ArrayList<HashMap<String, String>> listItems = (ArrayList<HashMap<String, String>>) msg.obj;
                    List<RateItem> rateLists = (ArrayList<RateItem>)msg.obj;
                    myAdapter myadapter = new myAdapter(list.this, R.layout.editlist,rateLists);
                    //list.setAdapter(myadapter);
                    //list.setOnItemClickListener(list.this);//添加事件监听
                    //String str = (String) msg.obj;
                    //Log.i(TAG, "handleMessage:getMessage meg = " + str);
                }
                super.handleMessage(msg);
            }
        };
    }
/**
    public void run() {
        Log.i(TAG,"run:run()......");
        Message msg = handler.obtainMessage(5);
        try{
            String url = "https://www.usd-cny.com/bankofchina.htm";
            Document doc = null;
            doc = Jsoup.connect(url).get();
            //Log.i(TAG, "run: "+ doc.title());
            Elements tables = doc.getElementsByTag("table");
            Element table0 = tables.get(0);
            // 获取 TD 中的数据
            Elements tds = table0.getElementsByTag("td");
            //List<String> list2 = new ArrayList<String>();
            ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
            for(int i=0; i<tds.size(); i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                //Log.i(TAG, "run: " + str1 + "==>" + val);
                HashMap<String, String> map = new HashMap<String, String>();
                float v = 100f / Float.parseFloat(val);
                float rate =(float)(Math.round(v*100))/100;
                map.put("ItemTitle", str1);
                map.put("ItemDetail", String.valueOf(rate));
                list1.add(map);
            }
            msg.obj = list1;
            handler.sendMessage(msg);

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 **/

@Override
public void run() {
    Log.i("List","run...");
    Message msg = handler.obtainMessage(5);
    List<String> retList = new ArrayList<String>();
    String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);
    if(curDateStr.equals(logDate)){
        //如果相等，则不从网络中获取数据
        Log.i("run","日期相等，从数据库中获取数据");
        DBManager dbManager = new DBManager(list.this);
        for(RateItem rateItem : dbManager.listAll()){
            retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
        }
    }else{
        Log.i("run","日期相等，从网络中获取在线数据");
        //获取网络数据
        try {
            List<RateItem> rateList = new ArrayList<RateItem>();
            String url = "https://www.usd-cny.com/bankofchina.htm";
            Document doc = null;
            doc = Jsoup.connect(url).get();
            //Log.i(TAG, "run: "+ doc.title());

            //Log.i("WWW","retStr:" + retStr);
            //需要对获得的html字串进行解析，提取相应的汇率数据...

            //Document doc = Jsoup.parse(retStr);
            Elements tables = doc.getElementsByTag("table");
            Element table0 = tables.get(0);
            // 获取 TD 中的数据
            Elements tds = table0.getElementsByTag("td");
            for(int i=0; i<tds.size(); i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                //Log.i(TAG, "run: " + str1 + "==>" + val);
                HashMap<String, String> map = new HashMap<String, String>();
                float v = 100f / Float.parseFloat(val);
                float rate =(float)(Math.round(v*100))/100;

                retList.add(str1 + "->" + val);
                RateItem rateItem = new RateItem(str1,val);
                rateList.add(rateItem);
            }


            DBManager dbManager = new DBManager(list.this);
            dbManager.deleteAll();
            Log.i("db","删除所有记录");
            dbManager.addAll(rateList);
            Log.i("db","添加新记录集");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //更新记录日期
        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(DATE_SP_KEY, curDateStr);
        edit.commit();
        Log.i("run","更新日期结束：" + curDateStr);
    }

    msg.obj = retList;
    msg.what = 5;
    handler.sendMessage(msg);
}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object ip = list.getItemAtPosition(position);
        HashMap<String, String> map = (HashMap<String, String>)ip;
        String titlestr = map.get("ItemTitle");
        String detailstr = map.get("ItemDetail");
        Float curr_rate = Float.parseFloat(detailstr);
        Log.i(TAG, "onItemClick: title=" + titlestr);
        Log.i(TAG, "onItemClick: detail=" + detailstr);

        /*TextView title = (TextView)view.findViewById(R.id.itemTitle);
        TextView detail = (TextView)view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2=" + title2);
        Log.i(TAG, "onItemClick: detail2=" + detail2);*/

        Intent config_new = new Intent(this,preResult.class);
        //传递参数
        Bundle bdl = new Bundle();
        bdl.putString("itemTitle",titlestr);
        bdl.putFloat("itemDetail",curr_rate);
        config_new.putExtras(bdl);

        Log.i(TAG,"openOne:itemTitle="+titlestr);
        Log.i(TAG,"openOne:itemDetail="+curr_rate);
        //打开新页面
        startActivity(config_new);
    }






}




