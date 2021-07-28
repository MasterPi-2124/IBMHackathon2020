package com.example.iotapp;

public class PlaceInformation {
    private String name;
    private double latitude;
    private double longtitude;
    private double temperature;
    private double humidity;
    private double ghi;
    private double chieu_sang;
    private double goc_mat_troi;
    private double luong_mua;
    private double toc_do_gio;
    private int evaluation;
    private String stability;

    public PlaceInformation(String _name, double _latitude, double _longtitude, double _temperature,
                            double _humidity, double _ghi, double _chieu_sang, double _goc_mat_troi,
                            double _luong_mua, double _toc_do_gio, int _evaluation, String _stability){
        this.name = _name;
        this.latitude = _latitude;
        this.longtitude = _longtitude;
        this.temperature = _temperature;
        this.humidity = _humidity;
        this.ghi = _ghi;
        this.chieu_sang = _chieu_sang;
        this.goc_mat_troi = _goc_mat_troi;
        this.luong_mua = _luong_mua;
        this.toc_do_gio = _toc_do_gio;
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

    public double getChieu_sang() {
        return chieu_sang;
    }

    public void setChieu_sang(double chieu_sang) {
        this.chieu_sang = chieu_sang;
    }

    public double getGoc_mat_troi() {
        return goc_mat_troi;
    }

    public void setGoc_mat_troi(double goc_mat_troi) {
        this.goc_mat_troi = goc_mat_troi;
    }

    public double getLuong_mua() {
        return luong_mua;
    }

    public void setLuong_mua(double luong_mua) {
        this.luong_mua = luong_mua;
    }

    public double getToc_do_gio() {
        return toc_do_gio;
    }

    public void setToc_do_gio(double toc_do_gio) {
        this.toc_do_gio = toc_do_gio;
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
