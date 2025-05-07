package com.aeroguard.surfaces;

import com.aeroguard.geometry.Point;
import com.aeroguard.obstacle.Obstacle;

public class Conique {
    private Point Center;
    private double innerRadius=4000;
    private double outerRadius=6150;
    private static double pente=0.05;

    public Conique(Point Center){
        this.Center=Center;
    }

    public boolean IsInside(Obstacle obstacle) {
        return innerRadius<=Center.distanceToPoint(obstacle.getObstacleAsPoint()) && Center.distanceToPoint(obstacle.getObstacleAsPoint())<= outerRadius;
    }
    
    public double obstaclePenetration(Obstacle obstacle){
        double distanceToObstacle=Center.distanceToPoint(obstacle.getObstacleAsPoint());
        double depthInConique=distanceToObstacle-innerRadius;
        double roofLimite=depthInConique*this.pente;
        return obstacle.getElevation()-roofLimite;
    }

    public Point getCenter() {
        return Center;
    }

    public double getRadius(){
        return this.outerRadius;
    }
}
