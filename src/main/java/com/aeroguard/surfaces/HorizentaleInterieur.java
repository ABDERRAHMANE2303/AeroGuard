package com.aeroguard.surfaces;

import com.aeroguard.geometry.Point;
import com.aeroguard.obstacle.Obstacle;

public class HorizentaleInterieur {
    private Point Center;
    private int Radius=4150;
    private int height=47;

    public HorizentaleInterieur(Point Center) {
        this.Center = Center;
    }

    public boolean IsInside(Obstacle obstacle) {
        return Center.distanceToPoint(obstacle.getObstacleAsPoint())<=Radius;
    }

    public double obstaclePenetration(Obstacle obstacle){
        return obstacle.getElevation()-this.height;
    }

    public Point getCenter() {
        return Center;
    }

    public void setCenter(Point Center) {
        this.Center = Center;
    }

    public double getRadius(){
        return Radius;
    }

    public int getHeight() {
        return height;
    }

}
