package com.aeroguard.BackgroundStuff;

import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import com.aeroguard.maplayers.PolygonMapLayer;

public class PolygonLayerTask extends Task<Void> {

    private final MapView mapView;
    private final PolygonMapLayer layer;
    private final boolean addLayer;

    public PolygonLayerTask(MapView mapView, PolygonMapLayer layer, boolean addLayer) {
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
