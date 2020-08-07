package com.example.toando.appbanhang.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toando.appbanhang.R;
import com.example.toando.appbanhang.activity.adapter.DienthoaiAdapter;
import com.example.toando.appbanhang.activity.adapter.LaptopDHAdapter;
import com.example.toando.appbanhang.activity.model.Sanpham;
import com.example.toando.appbanhang.activity.until.CheckConnection;
import com.example.toando.appbanhang.activity.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopDoHoaActivity extends AppCompatActivity {

    Toolbar toolbarlaptopdh;
    ListView lvlaptopdh;
    LaptopDHAdapter laptopDHAdapter;
    ArrayList<Sanpham> manglaptopDH;
    int idlaptopdh = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    LaptopDoHoaActivity.mHandler mHandler;
    boolean limitData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop_do_hoa);
        Anhxa();
        GetIdDanhmuc();
        ActionToolbar();
        GetData(page);
        LoadMoreData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Anhxa() {
        toolbarlaptopdh = (Toolbar) findViewById(R.id.toolbarLaptopDH);
        lvlaptopdh = (ListView) findViewById(R.id.listviewlaptopDH);
        manglaptopDH = new ArrayList<>();
        laptopDHAdapter = new LaptopDHAdapter(getApplicationContext(), manglaptopDH);
        lvlaptopdh.setAdapter(laptopDHAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progress_bar, null);
        mHandler = new mHandler();

    }

    private void GetIdDanhmuc() {
        idlaptopdh = getIntent().getIntExtra("iddanhmuc", -1);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarlaptopdh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptopdh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongdanDienthoai+ String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tenlaptop = "";
                int Gialaptop = 0;
                String Hinhanhlaptop = "";
                String Mota = "";
                int Iddmlaptop = 0;
                if (response != null && response.length() != 2){
                    lvlaptopdh.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("ten_sp");
                            Gialaptop = jsonObject.getInt("gia_sp");
                            Hinhanhlaptop = jsonObject.getString("hinhanh_sp");
                            Mota = jsonObject.getString("mota");
                            Iddmlaptop = jsonObject.getInt("id_dm");
                            manglaptopDH.add(new Sanpham(id, Tenlaptop, Gialaptop, Hinhanhlaptop, Mota, Iddmlaptop));
                            laptopDHAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData = true;
                    lvlaptopdh.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Het san pham roi");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String, String>();
                param.put("id_dm", String.valueOf(idlaptopdh));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvlaptopdh.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    private void LoadMoreData() {
        lvlaptopdh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham", manglaptopDH.get(i));
                startActivity(intent);
            }
        });
        lvlaptopdh.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitData == false){
                    isLoading = true;
                    LaptopDoHoaActivity.ThreadData threadData = new LaptopDoHoaActivity.ThreadData();
                    threadData.start();
                }
            }
        });
    }
}
