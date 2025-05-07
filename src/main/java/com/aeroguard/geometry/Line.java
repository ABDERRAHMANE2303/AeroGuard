package com.aeroguard.geometry;

public class Line {
    private Point pointLimite1;
    private Point pointLimite2;
    private double length;

    public Line(Point pointLimite1, Point pointLimite2) {
        this.pointLimite1 = pointLimite1;
        this.pointLimite2 = pointLimite2;
        this.length = pointLimite1.distanceToPoint(pointLimite2);
    }

    //needs to be reworked , most likely the root of the calculation issues sometimes . 
    public double distanceToLine(Point point) {
        double lat1 = Math.toRadians(pointLimite1.getLatitude());
        double lon1 = Math.toRadians(pointLimite1.getLongitude());
        double lat2 = Math.toRadians(pointLimite2.getLatitude());
        double lon2 = Math.toRadians(pointLimite2.getLongitude());
        double lat3 = Math.toRadians(point.getLatitude());
        double lon3 = Math.toRadians(point.getLongitude());

        double xDelta = lon2 - lon1;
        double yDelta = lat2 - lat1;
        double u = ((lon3 - lon1) * xDelta + (lat3 - lat1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
        final double EARTH_RADIUS = 6371000; // in meters
        double latClosest, lonClosest;

        if (u < 0) {
            latClosest = lat1;
            lonClosest = lon1;
        } else if (u > 1) {
            latClosest = lat2;
            lonClosest = lon2;
        } else {
            latClosest = lat1 + u * yDelta;
            lonClosest = lon1 + u * xDelta;
        }

        double latDistance = lat3 - latClosest;
        double lonDistance = lon3 - lonClosest;

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(latClosest) * Math.cos(lat3)
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    // Translate the line by a certain distance(weird method but it works)
    public Line translation(double offsetDistance) {
        Rectangle Auxilary=new Rectangle(this.pointLimite1,this.pointLimite2,offsetDistance);
        Point newPointLimite1 = Auxilary.getP3();
        Point newPointLimite2 =Auxilary.getP4();
        return new Line(newPointLimite1,newPointLimite2);
        //complicated method
//        double metersPerDegreeLat = 111320;
//        double metersPerDegreeLon = 40075000 * Math.cos(Math.toRadians(pointLimite1.getLatitude())) / 360;
//
//        double angle = Math.atan2(pointLimite2.getLatitude() - pointLimite1.getLatitude(),
//                pointLimite2.getLongitude() - pointLimite1.getLongitude());
//        double offsetLat = offsetDistance * Math.sin(angle) / metersPerDegreeLat;
//        double offsetLon = offsetDistance * Math.cos(angle) / metersPerDegreeLon;
//
//        Point newPointLimite1 = new Point(pointLimite1.getLatitude() + offsetLat, pointLimite1.getLongitude() + offsetLon, pointLimite1.getElevation());
//        Point newPointLimite2 = new Point(pointLimite2.getLatitude() + offsetLat, pointLimite2.getLongitude() + offsetLon, pointLimite2.getElevation());
//
//        return new Line(newPointLimite1, newPointLimite2);
    }

    public Point calculateCenter() {
        double midLatitude = (pointLimite1.getLatitude() + pointLimite2.getLatitude()) / 2;
        double midLongitude = (pointLimite1.getLongitude() + pointLimite2.getLongitude()) / 2;
        double midElevation = (pointLimite1.getElevation() + pointLimite2.getElevation()) / 2;

        return new Point(midLatitude, midLongitude, midElevation);
    }

    public double getLength() {
        return length;
    }

    public Point getPointLimite1() {
        return pointLimite1;
    }

    public Point getPointLimite2() {
        return pointLimite2;
    }

    public Point getCenter() {
        return this.calculateCenter();
    }
}
