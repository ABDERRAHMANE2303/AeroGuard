//a eliminer dans la version final

package com.aeroguard.infrastructures;

import com.aeroguard.geometry.Line;
import com.aeroguard.geometry.Rectangle;
import com.aeroguard.geometry.Point;

public class RoadBande {
    private Point center;
    public Rectangle base;
    private Rectangle roadBase;

    public RoadBande(Road road) {
        this.roadBase = road.getBase();
        this.center = road.getCenter();
        this.base = createRoadBande();
    }

    private Rectangle createRoadBande() {
        Point topLeft = roadBase.getP3();
        Point topRight = roadBase.getP4();

        // Offset points accordingly
        Point newTopRight = offsetPoint(topRight, -60, 110);
        Point newTopLeft = offsetPoint(topLeft, 60, -110);
        Line TopLine=new Line(newTopLeft, newTopRight);
        Rectangle InitialBase=new Rectangle(newTopLeft, newTopRight, roadBase.getLength() );
        return InitialBase;
//        return InitialBase.rotateRectangle(0,TopLine.getCenter());
    }

    private Point offsetPoint(Point point, double latOffset, double lonOffset) {
        double earthRadius = 6371000; // meters

        double newLat = point.getLatitude() + (latOffset / earthRadius) * (180 / Math.PI);
        double newLon = point.getLongitude() + (lonOffset / earthRadius) * (180 / Math.PI) / Math.cos(point.getLatitude() * Math.PI / 180);

        return new Point(newLat, newLon, point.getElevation());
    }

    public Point getCenter() {
        return this.center;
    }

    public Point getTopLeftCorner() {
        return base.getP1();
    }

    public Point getTopRightCorner() {
        return base.getP2();
    }

    public Point getBottomLeftCorner() {
        return base.getP4();
    }

    public Point getBottomRightCorner() {
        return base.getP3();
    }
    public Rectangle setBase(Rectangle base) {
        this.base = base;
        return base;
    }
}


