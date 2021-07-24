package com.example.iotapp;

public class PlaceInformation {
    private String name;
    private double latitude;
    private double longtitude;
    private double temperature;
    private double humidity;
    private double ghi;
    private int evaluation;
    private String stability;

    public PlaceInformation(String _name, double _latitude, double _longtitude, double _temperature, double _humidity, double _ghi, int _evaluation, String _stability){
        this.name = _name;
        this.latitude = _latitude;
        this.longtitude = _longtitude;
        this.temperature = _temperature;
        this.humidity = _humidity;
        this.ghi = _ghi;
        this.evaluation = _evaluation;
        this.stability = _stability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getGhi() {
        return ghi;
    }

    public void setGhi(double ghi) {
        this.ghi = ghi;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public String getStability() {
        return stability;
    }

    public void setStability(String stability) {
        this.stability = stability;
    }
}
