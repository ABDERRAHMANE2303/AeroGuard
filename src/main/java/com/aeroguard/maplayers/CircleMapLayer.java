package com.aeroguard.maplayers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapView;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleMapLayer extends MapLayer {
    private final Circle Circle;
    private final double latitude;
    private final double longitude;
    private final double radius;
    private final MapView mapView; 

    public CircleMapLayer(MapView mapView, double latitude, double longitude, double radius, Color color) {
        this.mapView = mapView; 
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;

        this.Circle = new Circle();
        this.Circle.setFill(color);
        this.Circle.setOpacity(0.5);
        this.Circle.setStroke(color);
        this.Circle.setStrokeWidth(2.0);

        getChildren().add(Circle);
    }

    @Override
    protected void layoutLayer() {
        // Update the position of the circles when the map is moved or zoomed
        Point2D mapPoint = getMapPoint(latitude, longitude);
        Circle.setCenterX(mapPoint.getX());
        Circle.setCenterY(mapPoint.getY());

        // Convert radius from meters to pixels based on the current zoom level
        double radiusInPixels = convertMetersToPixels(radius);

        Circle.setRadius(radiusInPixels);
        Circle.setRadius(radiusInPixels);
    }

    private double convertMetersToPixels(double radiusInMeters) {
        double zoom = mapView.getZoom();

        // The scale factor at the equator for the current zoom level
        double scale = 256 * Math.pow(2, zoom) / 40075016.686; // Earth's circumference in meters

        // Convert the radius to pixels based on the current scale
        return radiusInMeters * scale;
    }
}