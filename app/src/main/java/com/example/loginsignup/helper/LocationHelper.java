package com.example.loginsignup.helper;

public class LocationHelper {

    String address;
    String date;
    String email;
    String lat;
    String lng;
    String time;
    String deviceDetails;

    public LocationHelper() {
    }

    public LocationHelper(String address, String lat, String lng, String time, String date, String deviceDetails) {
        this.lat = lat;
        this.deviceDetails = deviceDetails;
        this.lng = lng;
        this.time = time;
        this.address = address;
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeviceDetails() {
        return deviceDetails;
    }

    public void setDeviceDetails(String deviceDetails) {
        this.deviceDetails = deviceDetails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
