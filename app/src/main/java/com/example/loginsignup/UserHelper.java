package com.example.loginsignup;

public class UserHelper {
    String currentDate, email, lat, lng, currentTime, deviceDetails;

    public UserHelper(String email, String lat, String lng, String currentTime, String currentDate, String deviceDetails) {
        this.lat = lat;
        this.deviceDetails = deviceDetails;
        this.lng = lng;
        this.currentTime = currentTime;
        this.email = email;
        this.currentDate = currentDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
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

    public String getCurrentTime() {

        return currentTime;
    }

    public void setCurrentTime(String currentTime) {

        this.currentTime = currentTime;
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
