package com.example.headcompany.model;

public class Camera {

    // ATTRIBUTES
    private String address;
    private int cameraId;
    private String cameraName;
    private String kilometer;
    private double latitude;
    private double longitude;
    private String road;
    private int sourceId;
    private String urlImage;

    // CONSTRUCTOR

    public Camera() {}

    public Camera(String address, int cameraId, String cameraName, String kilometer, double latitude,
                  double longitude, String road, int sourceId, String urlImage) {
        super();
        this.address = address;
        this.cameraId = cameraId;
        this.cameraName = cameraName;
        this.kilometer = kilometer;
        this.latitude = latitude;
        this.longitude = longitude;
        this.road = road;
        this.sourceId = sourceId;
        this.urlImage = urlImage;
    }


    //GETTERS & SETTERS
    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getKilometer() {
        return kilometer;
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRoad() {
        return road;
    }

    public String getUrlImage() {
        return urlImage;
    }
}