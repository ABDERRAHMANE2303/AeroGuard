package com.aeroguard.surfaces;

import com.aeroguard.geometry.*;
import com.aeroguard.obstacle.Obstacle;

public class Approach extends PyramidLikeSurfaces {
    private Line section1LineFinal;
    private Line section2LineFinal;
    private IsocelTrapezoid section2Base;

    public Approach(Point topLeft, Point topRight) {
        // pente inutile ici c seulement pour la creation du trapez
        super(topLeft, topRight, 0.025, 5080, 15000);
        // basically creating the section 1 and 2 bases using trapez class and getting the points from it
        this.section1LineFinal = new Line(new IsocelTrapezoid(topLeft, topRight, 3000, 1240).getP3(),
                new IsocelTrapezoid(topLeft, topRight, 3000, 1240).getP4());
        this.section2Base = new IsocelTrapezoid(section1LineFinal.getPointLimite1(),
                section1LineFinal.getPointLimite2(), 3600, 2.392);
        this.section2LineFinal = section2Base.getendingLine();
    }

    @Override
    public double calculateRoofHeight(Point point) {
        if (this.getbeginingLine() == null) {
            throw new IllegalStateException("beginningLine is not initialized");
        }
        double distanceToBase = this.getbeginingLine().distanceToLine(point);
        double roofHeight = this.getP1().getElevation();

        if (distanceToBase <= 3000) {
            roofHeight += distanceToBase * 0.02;
        } else if (distanceToBase <= 6600) {
            roofHeight += distanceToBase * 0.025;
        } else if (distanceToBase <= 15000) {
            roofHeight += 165;
        }
        return roofHeight;
    }

    public String getSection(Obstacle obstacle) {
        double distance = this.getbeginingLine().distanceToLine(obstacle.getObstacleAsPoint());
        String outcome = "";
        if (distance <= 3000) {
            outcome = "Section 1";
        } else if (distance <= 6600) {
            outcome = "Section 2";
        } else if (distance <= 15000) {
            outcome = "Section horizentale";
        }
        return outcome;
    }

    public Line getsection1LineFinal() {
        return this.section1LineFinal;
    }

    public Line getsection2LineFinal() {
        return this.section2LineFinal;
    }

    public IsocelTrapezoid getsection2Base() {
        return this.section2Base;
    }
}
