package com.aeroguard.geometry;

public class Rectangle {
    private Point p1; // Top left
    private Point p2;// Top right
    public Line BeginingLine;
    private Point p3; // Bottom right
    private Point p4;// Bottom left
    public Line Endingline;
    private double length;
    private double width;

    public Rectangle(Point p1, Point p2, double length) {
        this.p1 = p1;
        this.p2 = p2;
        this.BeginingLine=new Line(p1, p2);
        this.length = length;
        this.width = p1.distanceToPoint(p2);

        //calculate p3 and p4
        double bearing = calculateBearing(p1, p2);
        this.p3 = offsetPoint(p2, length / 1000.0, bearing + 90);
        this.p4 = offsetPoint(p1, length / 1000.0, bearing + 90);
        this.Endingline=new Line(p3, p4);
    }

    // Bearing calculation function (same as in IsocelTrapezoid)
    public double calculateBearing(Point from, Point to) {
        double lat1 = Math.toRadians(from.getLatitude());
        double lat2 = Math.toRadians(to.getLatitude());
        double deltaLon = Math.toRadians(to.getLongitude() - from.getLongitude());

        double y = Math.sin(deltaLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) -
                Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLon);
        return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    // Offset point calculation considering bearing
    private Point offsetPoint(Point point, double distance, double bearing) {
        double R = 6371.0; // Radius of Earth in kilometers
        double bearingRad = Math.toRadians(bearing);

        double lat1 = Math.toRadians(point.getLatitude());
        double lon1 = Math.toRadians(point.getLongitude());

        double lat2 = Math.asin(Math.sin(lat1) * Math.cos(distance / R) +
                Math.cos(lat1) * Math.sin(distance / R) * Math.cos(bearingRad));
        double lon2 = lon1 + Math.atan2(Math.sin(bearingRad) * Math.sin(distance / R) * Math.cos(lat1),
                Math.cos(distance / R) - Math.sin(lat1) * Math.sin(lat2));
        lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI; // Normalize to -180...+180

        return new Point(Math.toDegrees(lat2), Math.toDegrees(lon2), point.getElevation());
    }

    public Rectangle rotateRectangle(double angleDegrees, Point RotationPoint,double initialLength) {
        // Rotate each corner of the rectangle around the center of the roadBande
        Point rotatedTopLeft = getP1().rotatePoint( RotationPoint, angleDegrees);
        Point rotatedTopRight = getP2().rotatePoint( RotationPoint, angleDegrees);
        Point rotatedBottomLeft = getP4().rotatePoint( RotationPoint, angleDegrees);
        Point rotatedBottomRight = getP3().rotatePoint( RotationPoint, angleDegrees);

        // Update the base rectangle with the rotated points
        Rectangle rotatedRectangle = new Rectangle(rotatedTopLeft, rotatedTopRight,initialLength);
        return rotatedRectangle;
    }


    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getP3() {
        return p3;
    }

    public Point getP4() {
        return p4;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public Line getBeginingLine(){
        return this.BeginingLine;
    }

    public Line getEndingLine(){
        return this.Endingline;
    }


}
