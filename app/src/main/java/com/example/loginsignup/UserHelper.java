package com.example.loginsignup;

public class UserHelper {
    String lat,lng,currentTime;


    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }



    public UserHelper() {
    }
    public UserHelper(String lat, String lng, String currentTime) {
        this.lat = lat;
        this.lng = lng;
        this.currentTime=currentTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
