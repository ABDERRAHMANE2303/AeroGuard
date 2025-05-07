package com.aeroguard.infrastructures;

import com.aeroguard.obstacle.Obstacle;
import com.aeroguard.geometry.Point;

public class OmnidirectionalEquipements {
    private float r;
    private float R;
    private double latitude;
    private double longitude;
    private double alpha;
    private float height;
    private float altitude;
    private float elevation;

    public OmnidirectionalEquipements(float r, float R, double latitude, double longitude, float height, double alpha, float altitude) {
        this.r = r;
        this.R = R;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alpha = alpha;
        this.height = height;
        this.altitude = altitude;
        this.elevation = altitude + height;
    }

    public double distanceToObstacle(Obstacle obstacle) {
        Point dvorPoint = new Point(this.latitude, this.longitude, this.elevation);
                return dvorPoint.distanceToPoint(obstacle.getObstacleAsPoint());
    }

    public double hLimite(Obstacle obstacle) {
        double distanceDvorPoint = this.distanceToObstacle(obstacle);
        double tangentAlpha = Math.tan(Math.toRadians(this.alpha));
        return distanceDvorPoint * tangentAlpha + this.elevation;
    }

    public double danger(Obstacle obstacle) {
        double hLimite = this.hLimite(obstacle);
        double penetration=obstacle.getElevation()-hLimite;
        return penetration;
    }

    public float getR() {
        return R;
    }

    public void setR(float r) {
        this.r = R;
    }

    public float getr(){
        return r;
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

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        updateElevation();
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
        updateElevation();
    }

    public float getElevation() {
        return elevation;
    }

    private void updateElevation() {
        this.elevation = this.altitude + this.height;
    }
}
