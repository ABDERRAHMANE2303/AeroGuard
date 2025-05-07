package com.aeroguard.surfaces;

import com.aeroguard.geometry.IsocelTrapezoid;
import com.aeroguard.geometry.Line;
import com.aeroguard.geometry.Point;
import com.aeroguard.geometry.PyramidLikeSurfaces;

public class MonteAuDecolage extends PyramidLikeSurfaces {
    private IsocelTrapezoid Base;
    public Line MonteeAuDecolageLineFinal;

    public MonteAuDecolage(Point topLeft, Point topRight) {
        super(topLeft, topRight, 0.02, 4100, 15000);
        this.MonteeAuDecolageLineFinal = getendingLine();
    }
}
