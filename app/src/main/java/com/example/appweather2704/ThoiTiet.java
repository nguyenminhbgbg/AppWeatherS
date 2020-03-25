package com.example.appweather2704;

public class ThoiTiet {
    public String Day;
    public String Status;
    public String Image;
    public String MaxTemp, MinTemp;

    public ThoiTiet(String day, String status, String image, String maxTemp, String minTemp) {
        Day = day;
        Status = status;
        Image = image;
        MaxTemp = maxTemp;
        MinTemp = minTemp;
    }
}
