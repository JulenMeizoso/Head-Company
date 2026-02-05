package com.example.headcompany.model;

public class Incidence {

    // ATTRIBUTES
    private String autonomousRegion;
    private String carRegistration;
    private String cause;
    private String cityTown;
    private String direction;
    private String endDate;
    private String incidenceDescription;
    private int incidenceId;
    private String incidenceLevel;
    private String incidenceName;
    private String incidenceType;
    private double latitude;
    private double longitude;
    private double pkEnd;
    private double pkStart;
    private String province;
    private String road;
    private int sourceId;
    private String startDate;

    // CONSTRUCTOR
    public Incidence(String autonomousRegion, String carRegistration, String cause, String cityTown, String direction,
                     String endDate, String incidenceDescription, int incidenceId, String incidenceLevel,
                     String incidenceName, String incidenceType, double latitude, double longitude, double pkEnd, double pkStart,
                     String province, String road, int sourceId, String startDate) {
        this.autonomousRegion = autonomousRegion;
        this.carRegistration = carRegistration;
        this.cause = cause;
        this.cityTown = cityTown;
        this.direction = direction;
        this.endDate = endDate;
        this.incidenceDescription = incidenceDescription;
        this.incidenceId = incidenceId;
        this.incidenceLevel = incidenceLevel;
        this.incidenceName = incidenceName;
        this.incidenceType = incidenceType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pkEnd = pkEnd;
        this.pkStart = pkStart;
        this.province = province;
        this.road = road;
        this.sourceId = sourceId;
        this.startDate = startDate;
    }


    //GETTERS & SETTERS
    public String getAutonomousRegion() {
        return autonomousRegion;
    }

    public void setAutonomousRegion(String autonomousRegion) {
        this.autonomousRegion = autonomousRegion;
    }

    public String getCarRegistration() {
        return carRegistration;
    }

    public void setCarRegistration(String carRegistration) {
        this.carRegistration = carRegistration;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIncidenceDescription() {
        return incidenceDescription;
    }

    public void setIncidenceDescription(String incidenceDescription) {
        this.incidenceDescription = incidenceDescription;
    }

    public int getIncidenceId() {
        return incidenceId;
    }

    public void setIncidenceId(int incidenceId) {
        this.incidenceId = incidenceId;
    }

    public String getIncidenceLevel() {
        return incidenceLevel;
    }

    public void setIncidenceLevel(String incidenceLevel) {
        this.incidenceLevel = incidenceLevel;
    }

    public String getIncidenceName() {
        return incidenceName;
    }

    public void setIncidenceName(String incidenceName) {
        this.incidenceName = incidenceName;
    }

    public String getIncidenceType() {
        return incidenceType;
    }

    public void setIncidenceType(String incidenceType) {
        this.incidenceType = incidenceType;
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

    public double getPkEnd() {
        return pkEnd;
    }

    public void setPkEnd(double pkEnd) {
        this.pkEnd = pkEnd;
    }

    public double getPkStart() {
        return pkStart;
    }

    public void setPkStart(double pkStart) {
        this.pkStart = pkStart;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}