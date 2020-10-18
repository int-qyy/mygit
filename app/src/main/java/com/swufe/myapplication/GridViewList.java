package com.swufe.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridViewList extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "GridView";
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_list);
        //开启线程
        Thread thread=new Thread();
        thread.start();
        /**
        //空显示

        ListView listView=(ListView)findViewById(R.id.mylist);
        String data[]={"one","two","three","four"};
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));

        //删除
        ((ArrayAdapter) adapter).remove(listView.getItemAtPosition(position));
        //adapter.notifyDataSetChanged(); 删除时会自动调用 ArrayAdapter类型
        //列表数据项删除SimpleAdapter类型

        //单击列表以删除 首先添加事件监听
        listView.setOnItemClickListener(GridViewList.this);


        ArrayList<HashMap<String, String>> listItems = new ArrayList<HashMap<String, String>>();
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItems, R.layout.editlist, new String[]{"ItemTitle", "ItemDetail"}, new int[]{R.id.itemTitle, R.id.itemDetail});
        listItems.remove(position);//删除数据
        listItemAdapter.notifyDataSetChanged();//更新适配器

**/
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position="+position);
        Log.i(TAG, "onItemClick: parent="+parent);
        //adapter.remove(listView.getItemAtPosition(position));
        //

    }

}
