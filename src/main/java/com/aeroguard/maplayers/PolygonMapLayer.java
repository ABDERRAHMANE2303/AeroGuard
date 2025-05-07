package com.aeroguard.maplayers;

import com.gluonhq.maps.MapLayer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.List;

public class PolygonMapLayer extends MapLayer {

    private final List<double[]> latLonPoints;
    private final Polygon polygon;

    public PolygonMapLayer(List<double[]> latLonPoints,Color color) {
        this.latLonPoints = latLonPoints;
        this.polygon = new Polygon();
        this.polygon.setFill(color);
        this.polygon.setOpacity(0.3);
        this.polygon.setStroke(color); 
        this.polygon.setStrokeWidth(2.0);
        getChildren().add(polygon); 
    }

    @Override
    protected void layoutLayer() {
        // Clear previous polygon points
        polygon.getPoints().clear();
        // Convert lat/lon points to map points (pixel coordinates)
        for (double[] latLon : latLonPoints) {
            Point2D mapPoint = getMapPoint(latLon[0], latLon[1]);
            polygon.getPoints().addAll(mapPoint.getX(), mapPoint.getY());
        }
    }

}
