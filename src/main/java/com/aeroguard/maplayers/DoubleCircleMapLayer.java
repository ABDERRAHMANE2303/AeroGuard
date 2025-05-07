package com.aeroguard.maplayers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapView;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DoubleCircleMapLayer extends MapLayer {
    private final Circle innerCircle;
    private final Circle outerCircle;
    private final double latitude;
    private final double longitude;
    private final double innerRadius;
    private final double outerRadius;
    private final MapView mapView;

    public DoubleCircleMapLayer(MapView mapView, double latitude, double longitude, double innerRadius,
            double outerRadius, Color innerColor, Color outerColor) {
        this.mapView = mapView;
        this.latitude = latitude;
        this.longitude = longitude;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;

        // Initialize the inner circle
        this.innerCircle = new Circle();
        this.innerCircle.setFill(innerColor);
        this.innerCircle.setOpacity(0.5);
        this.innerCircle.setStroke(innerColor);
        this.innerCircle.setStrokeWidth(2.0);

        // Initialize the outer circle
        this.outerCircle = new Circle();
        this.outerCircle.setFill(outerColor);
        this.outerCircle.setOpacity(0.3);
        this.outerCircle.setStroke(outerColor);
        this.outerCircle.setStrokeWidth(2.0);

        // Add the circles to the layer
        getChildren().addAll(outerCircle, innerCircle);
    }

    @Override
    protected void layoutLayer() {
        // Update the position of the circles when the map is moved or zoomed
        Point2D mapPoint = getMapPoint(latitude, longitude);
        innerCircle.setCenterX(mapPoint.getX());
        innerCircle.setCenterY(mapPoint.getY());
        outerCircle.setCenterX(mapPoint.getX());
        outerCircle.setCenterY(mapPoint.getY());

        // Convert radii from meters to pixels based on the current zoom level
        double innerRadiusInPixels = convertMetersToPixels(innerRadius);
        double outerRadiusInPixels = convertMetersToPixels(outerRadius);

        innerCircle.setRadius(innerRadiusInPixels);
        outerCircle.setRadius(outerRadiusInPixels);
    }

    private double convertMetersToPixels(double radiusInMeters) {
        // Get the zoom level from the MapView
        double zoom = mapView.getZoom();

        // The scale factor at the equator for the current zoom level
        double scale = 256 * Math.pow(2, zoom) / 40075016.686; // Earth's circumference in meters

        // Convert the radius to pixels based on the current scale
        return radiusInMeters * scale;
    }
}
