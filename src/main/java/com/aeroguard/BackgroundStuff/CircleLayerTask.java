package com.aeroguard.BackgroundStuff;

import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import com.aeroguard.maplayers.CircleMapLayer;
import com.aeroguard.maplayers.DoubleCircleMapLayer;

public class CircleLayerTask extends Task<Void> {

    private final MapView mapView;
    private final MapLayer layer; 
    private final boolean addLayer;

 
    public CircleLayerTask(MapView mapView, CircleMapLayer layer, boolean addLayer) {
        this.mapView = mapView;
        this.layer = layer;
        this.addLayer = addLayer;
    }

    public CircleLayerTask(MapView mapView, DoubleCircleMapLayer layer, boolean addLayer) {
        this.mapView = mapView;
        this.layer = layer;
        this.addLayer = addLayer;
    }

    @Override
    protected Void call() {
        Platform.runLater(() -> {
            if (addLayer) {
                mapView.addLayer(layer);
            } else {
                mapView.removeLayer(layer);
            }
        });
        return null;
    }
}
