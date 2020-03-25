package com.example.appweather2704;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText edtSearch;
    Button btnSearch, btnChangeActivity;
    TextView txtNamecity, txtcoutry, txtTemp, txtStatus, txtHumidity, txtCound, txtWill, txtDay;
    ImageView imgIcon;
    String City = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iNit();

        getCurrentWeatherData("SaiGon");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                if(city.equals("")){
                    City = "Saigon";
                    getCurrentWeatherData(City);
                }
                else{
                    City = city;
                    getCurrentWeatherData(City);
                }
            }
        });

        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name", city);
                startActivity(intent);
            }
        });
    }

    public void getCurrentWeatherData(String data){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=7c4aa59fbe52df6137c1998095ca4c23";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("ketqua", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String day = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtNamecity.setText("Tên thành phố:" + name);

                            long l = Long.valueOf(day);
                            Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");

                            String DAY = simpleDateFormat.format(date);

                            txtDay.setText(DAY);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1Weather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObject1Weather.getString("main");
                            String icon = jsonObject1Weather.getString("icon");

                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+ icon + ".png").into(imgIcon);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String nhietDo = jsonObjectMain.getString("temp");
                            String doAm = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(nhietDo);
                            String NhietDo = String.valueOf(a.intValue());

                            txtHumidity.setText(doAm + "%");
                            txtTemp.setText(NhietDo + "C");

                            JSONObject jsonObjectwild = jsonObject.getJSONObject("wind");
                            String gio = jsonObjectwild.getString("speed");
                            txtWill.setText(gio + "m/s");

                            JSONObject jsonObjectclouds = jsonObject.getJSONObject("clouds");
                            String may = jsonObjectclouds.getString("all");
                            txtCound.setText(may + "%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String contry = jsonObjectSys.getString("country");
                            txtcoutry.setText("Tên quốc gia: " + contry);

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

    private void iNit() {
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnChangeActivity = findViewById(R.id.btnChangeActivity);
        txtNamecity = findViewById(R.id.txtname);
        txtcoutry = findViewById(R.id.txtCountry);
        txtTemp = findViewById(R.id.txtTemp);
        txtStatus = findViewById(R.id.txtStatus);
        txtHumidity = findViewById(R.id.textViewhumidity);
        txtCound = findViewById(R.id.textViewCloud);
        txtWill = findViewById(R.id.textViewWill);
        txtDay = findViewById(R.id.textViewDay);
        imgIcon = findViewById(R.id.imgIcon);
    }
}
