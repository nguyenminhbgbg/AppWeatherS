package com.example.appweather2704;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<ThoiTiet> arrayList;

    public CustomAdapter(Context context, ArrayList<ThoiTiet> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.dong_listview,null);

        ThoiTiet thoiTiet = arrayList.get(i);
        TextView txtDay = convertView.findViewById(R.id.txtNgay);
        TextView txtStatus = convertView.findViewById(R.id.txtTrangthai);
        TextView txtMaxTemp = convertView.findViewById(R.id.txtMaxTemp);
        TextView txtMinTemp = convertView.findViewById(R.id.txtMinTemp);
        ImageView imgStatus = convertView.findViewById(R.id.imgTrangThai);

        txtDay.setText(thoiTiet.Day);
        txtStatus.setText(thoiTiet.Status);
        txtMaxTemp.setText(thoiTiet.MaxTemp + "ºC");
        txtMinTemp.setText(thoiTiet.MinTemp + "ºC");

        Picasso.with(context).load("http://openweathermap.org/img/wn/"+ thoiTiet.Image + ".png").into(imgStatus);


        return convertView;
    }
}
