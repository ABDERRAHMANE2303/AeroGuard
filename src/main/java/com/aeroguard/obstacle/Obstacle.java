package com.aeroguard.obstacle;

import com.aeroguard.geometry.Point;

public class Obstacle  {
    private double latitude;
    private double longitude;
    private float altitude;
    private float height;
    private float elevation;
    private String Description;
    private Point obstacleAsPoint=new Point(latitude,longitude,0);

    public Obstacle(double latitude, double longitude, float height, float altitude,String Description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
        this.altitude = altitude;
        this.elevation = altitude + height;
        this.Description = Description;
    }


    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }


    public float getAltitude() {
        return altitude;
    }


    public float getHeight() {
        return height;
    }


    public float getElevation() {
        return elevation;
    }

    public String getDescription() {
        return Description;
    }
    public Point getObstacleAsPoint() {
        return obstacleAsPoint;
    }
}
