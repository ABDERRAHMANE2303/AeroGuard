package com.aeroguard.geometry;

import com.aeroguard.obstacle.Obstacle;

public class PyramidLikeSurfaces extends IsocelTrapezoid{
    private double pente;
    public PyramidLikeSurfaces(Point topRightCorner, Point topLeftCorner, double pente, double baseDistance, double length) {

        super(topRightCorner, topLeftCorner, length, baseDistance);
        if (topRightCorner == null || topLeftCorner == null) {
            throw new IllegalArgumentException("Top right and top left corners cannot be null");
        }
        this.pente = pente;
    }

    public double calculateRoofHeight(Point point) {
        if (this.getbeginingLine() == null) {
            throw new IllegalStateException("beginning Line is not initialized");
        }
        double distanceToBase = this.getbeginingLine().distanceToLine(point);
        return this.getP1().getElevation() + distanceToBase * pente;
    }

    public double obstaclePenetration(Obstacle obstacle) {
        Point roofLimit = new Point(obstacle.getLatitude(), obstacle.getLongitude(), obstacle.getElevation());
        double roofHeight = calculateRoofHeight(roofLimit);
        return obstacle.getElevation() - roofHeight;
    }

    public double getPente() {
        return pente;
    }
    public void setPente(double newPente){
        this.pente=newPente;
    }
}
