package com.swufe.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DeleteList extends AppCompatActivity implements Runnable,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    private static final String TAG = "GridView";
    private int position;
    ListView listdelete;
    Handler handler;
    ArrayAdapter adapter;
    SimpleAdapter listItemAdapter;
    AlertDialog.Builder builder;
    ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>(); ;



         //空显示GridViewList
         //ListView listView=(ListView)findViewById(R.id.mylist);
        // String data[]={"one","two","three","four"};
        // ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
         //listView.setAdapter(adapter);
         //listView.setEmptyView(findViewById(R.id.nodata));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_list);

        Intent intent = getIntent();
        listdelete = (ListView)findViewById(R.id.dlist);
        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 5) {
                    /*List<String> list2 = (List<String>) msg.obj;
                    adapter = new ArrayAdapter<String>(DeleteActivity.this,
                            android.R.layout.simple_list_item_1,list2);
                    listdelete.setAdapter(adapter);*/
                    listItems = (ArrayList<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(DeleteList.this, listItems,  R.layout.editlist,
                            new String[] { "ItemTitle", "ItemDetail" },new int[] { R.id.itemTitle, R.id.itemDetail } );
                    listdelete.setAdapter(listItemAdapter);

                    //listdelete.setOnItemClickListener(DeleteActivity.this);//添加事件监听
                    listdelete.setOnItemLongClickListener(DeleteList.this);//添加长按事件监听

                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
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
            //ArrayList<String> list1 = new ArrayList<String>();
            ArrayList<HashMap<String, String>> list1 = new ArrayList<HashMap<String, String>>();
            for(int i=0; i<tds.size(); i+=6){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 5);
                String str1 = td1.text();
                String val = td2.text();
                //Log.i(TAG, "run: " + str1 + "==>" + val);
                float v = 100f / Float.parseFloat(val);
                float rate =(float)(Math.round(v*100))/100;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", str1);
                map.put("ItemDetail", String.valueOf(rate));
                list1.add(map);
                //String s = (String)(str1 + "==>" + val);
                //list1.add(s);
            }
            msg.obj = list1;
            handler.sendMessage(msg);

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: parent=" + parent);
        //SimpleItemAdapter:
        listItems.remove(position);
        listItemAdapter.notifyDataSetChanged();

        //adapter.remove(parent.getItemAtPosition(position));
        // adapter.notifyDataSetChanged()会自动调用
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.i(TAG, "onItemLongClick: 对话框事件处理");

                        /*//ArrayAdapter:
                        adapter.remove(parent.getItemAtPosition(osition));
                        adapter.notifyDataSetChanged()会自动调用*/

                        //SimpleItemAdapter:
                        // 删除数据项
                        listItems.remove(position);
                        // 更新适配器
                        listItemAdapter.notifyDataSetChanged();

                    }
                }).setNegativeButton("否", null);
        builder.create().show();
        return false;
    }
}


