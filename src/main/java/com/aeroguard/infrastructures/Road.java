package com.aeroguard.infrastructures;

import com.aeroguard.geometry.Line;
import com.aeroguard.geometry.Rectangle;
import com.aeroguard.geometry.Point;

public class Road {
    private Rectangle base;
    private Point center;
    private double length;
    private double width;
    public Line TopLine;
    public Line BottomLine;
    public Line LeftLine;
    public Line RightLine;

    public Road(Point topLeftCorner, Point topRightCorner,double lengthroad) {
        this.base = new Rectangle(topLeftCorner, topRightCorner,lengthroad);
        this.center = calculateCenter();
        this.length = lengthroad;
        this.width = topLeftCorner.distanceToPoint(topRightCorner);
        this.TopLine = new Line(topLeftCorner, topRightCorner);
        this.BottomLine = new Line(this.base.getP3(),this.base.getP4());
        this.LeftLine = new Line(this.base.getP3(),topLeftCorner);
        this.RightLine = new Line(topRightCorner,this.base.getP4());
    }

    private Point calculateCenter() {
        double avgLatitude = (base.getP1().getLatitude() + base.getP2().getLatitude() + base.getP3().getLatitude() + base.getP4().getLatitude()) / 4;
        double avgLongitude = (base.getP1().getLongitude() + base.getP2().getLongitude() + base.getP3().getLongitude() + base.getP4().getLongitude()) / 4;
        double avgElevation = (base.getP1().getElevation() + base.getP2().getElevation() + base.getP3().getElevation() + base.getP4().getElevation()) / 4;
        return new Point(avgLatitude, avgLongitude, avgElevation);
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

    public Point getCenter() {
        return center;
    }

    public Rectangle getBase() {
        return base;
    }

    public Rectangle setBase(Rectangle base) {
        this.base = base;
        return base;
    }
}
