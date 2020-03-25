package com.example.appweather2704;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    String tenThanhPho = "";
    ImageView imgBack;
    TextView txtName;
    ListView lv;
    CustomAdapter customAdapter;

    ArrayList<ThoiTiet> mangthoitiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        INit();

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");    // chuyền tên thành phố cần tìm kiếm
        // từ main sang mani2 để lưu tên thành phố-> tìm kiếm dữ liệu
        Log.d("ketqua","dữ liệu truyền từ Main1." + city);

        if(city.equals("")){
            tenThanhPho = "Saigon";
            Get7DayData(tenThanhPho);
        }
        else{
            tenThanhPho = city;
            Get7DayData(tenThanhPho);
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                onBackPressed();
            }
        });
    }

    private void INit() {
        imgBack = findViewById(R.id.imgBack);
        txtName = findViewById(R.id.txtNameContry);
        lv = findViewById(R.id.listView);
        mangthoitiet = new ArrayList<ThoiTiet>();
        customAdapter = new CustomAdapter(Main2Activity.this,mangthoitiet);
        lv.setAdapter(customAdapter);

    }

    private void Get7DayData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q="+data+"&units=metric&cnt=7&appid=7c4aa59fbe52df6137c1998095ca4c23";
        RequestQueue requestQueue = Volley.newRequestQueue(Main2Activity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("ketqua", "json" + response);   //JSONObject hàm bung ngoặc nhọn trong chuỗi JSON
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtName.setText(name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            // dùng vòng lặp for. để nhập hết dữ liệu của JSONArray
                            for(int i = 0 ; i< jsonArrayList.length(); i++){
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String ngay = jsonObjectList.getString("dt"); // truyền dữ liệu từ
                                // chuỗi JSON có tên "dt" sang chuối ngay

                                long l = Long.valueOf(ngay);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                                String Day = simpleDateFormat.format(date);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_max");
                                String min = jsonObjectTemp.getString("temp_min");

                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String NhietDoMax = String.valueOf(a.intValue());
                                String NhietDoMin = String.valueOf(b.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                mangthoitiet.add(new ThoiTiet(Day,status,icon,NhietDoMax,NhietDoMin));
                            }
                        customAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }
}
