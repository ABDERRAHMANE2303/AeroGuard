package com.aeroguard.maplayers;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MarkerLayer extends MapLayer {

    private MapPoint currentPoint;
    private Node currentMarker;

    public void addMarker(double latitude, double longitude) {
        // Reuse the existing marker if it exists, otherwise create a new one
        if (currentMarker == null) {
            currentMarker = new Circle(3, Color.BLACK); 
            getChildren().add(currentMarker);
        }

        // Update the marker's position
        currentPoint = new MapPoint(latitude, longitude);
        layoutLayer(); // Initially position the marker
    }

    // Method to update the marker's position on the map
    @Override
    protected void layoutLayer() {
        if (currentPoint != null && currentMarker != null) {
            // Convert geographic coordinates (lat, lon) to screen coordinates (x, y)
            Point2D mapPoint = getMapPoint(currentPoint.getLatitude(), currentPoint.getLongitude());
            currentMarker.setTranslateX(mapPoint.getX());
            currentMarker.setTranslateY(mapPoint.getY());
        }
    }
}
