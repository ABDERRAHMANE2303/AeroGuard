package com.aeroguard.controllers;


//could be more modular by defining everything(road , infrastructure , surfaces ...) in a separate class leaving this to only handle ui ... 


import com.aeroguard.infrastructures.OmnidirectionalEquipements;
import javafx.stage.FileChooser;
import com.aeroguard.BackgroundStuff.PolygonLayerTask;
import com.aeroguard.BackgroundStuff.ReportGenerator;
import com.aeroguard.geometry.Line;
import com.aeroguard.geometry.Point;
import com.aeroguard.geometry.Rectangle;
import com.aeroguard.maplayers.CircleMapLayer;
import com.aeroguard.maplayers.DoubleCircleMapLayer;
import com.aeroguard.maplayers.PolygonMapLayer;
import com.aeroguard.maplayers.MarkerLayer;
import com.aeroguard.obstacle.Obstacle;
import com.aeroguard.infrastructures.Road;
import com.aeroguard.surfaces.Approach;
import com.aeroguard.surfaces.Conique;
import com.aeroguard.surfaces.HorizentaleInterieur;
import com.aeroguard.surfaces.MonteAuDecolage;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import com.aeroguard.BackgroundStuff.CircleLayerTask;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainViewController {

        @FXML
        private Button generateReportButton;

        @FXML
        private TextField longitudeField;

        @FXML
        private TextField latitudeField;

        @FXML
        private TextField altitudeField;

        @FXML
        private TextField heightField;

        @FXML
        private TextField DescriptionField;

        @FXML
        private VBox VBoxButtonContainer;

        @FXML
        private Button zoomInButton, zoomOutButton;

        @FXML
        private StackPane MapViewPane;

        @FXML
        private MenuItem surfaceApprocheMenuItem;

        @FXML
        private MenuItem surfaceMonteeDecolageMenuItem;

        @FXML
        private MenuItem surfaceHorizentaleInterieurMenuItem;

        @FXML
        private MenuItem surfaceConiqueMenuItem;

        @FXML
        private MenuItem vorMenuItem;

        @FXML
        private MenuItem dmeMenuItem;

        @FXML
        private MenuItem vhftxMenuItem;

        @FXML
        private MenuItem VHFRxMenuItem;

        @FXML
        private MenuItem ssrMenuItem;

        @FXML
        private Button Switch;

        private MapView mapView;
        private Scene mainViewScene;

        // Infrastructure
        private Point topLeftRoad = new Point(23.70585, -15.93742, 0);
        private Point topRightRoad = new Point(23.70602, -15.93782, 0);

        private Point topLeftRoadBande = new Point(23.70494, -15.93668, 0);
        private Point topRightRoadBande = new Point(23.70601, -15.93916, 0);

        private Road road = new Road(topLeftRoad, topRightRoad, 3000);
        private Road roadBande = new Road(topLeftRoadBande, topRightRoadBande, 3120);

        // private RoadBande roadBande = new RoadBande(infrastructures);
        private OmnidirectionalEquipements vor = new OmnidirectionalEquipements(600, 3000, 10, 20, 15, 1, 10);
        private OmnidirectionalEquipements dme = new OmnidirectionalEquipements(300, 300, 10, 20, 15, 1, 10);
        private OmnidirectionalEquipements vhfTX = new OmnidirectionalEquipements(300, 2000, 10, 20, 15, 1, 10);
        private OmnidirectionalEquipements vhfRX = new OmnidirectionalEquipements(600, 2000, 10, 20, 15, 1, 10);
        private OmnidirectionalEquipements ssr = new OmnidirectionalEquipements(500, 15000, 23.71159526473195,
                        -15.927323932842086, 15, 0.25, 10);

        OmnidirectionalEquipements[] omnidirectionequipementslist = { vor, dme, vhfTX, vhfRX, ssr };

        // Surfaces du cote 03
        private Line surfacesStart03 = new Line(new Point(23.70546, -15.93943, 0), new Point(23.70445, -15.93694, 0));
        public Approach approach03 = new Approach(surfacesStart03.getPointLimite1(), surfacesStart03.getPointLimite2());
        private MonteAuDecolage monteAuDecolage03 = new MonteAuDecolage(surfacesStart03.getPointLimite1(),
                        surfacesStart03.getPointLimite2());

        // surfaces du cote 21
        private Line surfacesStart21 = new Line(new Point(23.73131, -15.92458, 0), new Point(23.73236, -15.92707, 0));
        private Approach approach21 = new Approach(surfacesStart21.getPointLimite1(),
                        surfacesStart21.getPointLimite2());
        private MonteAuDecolage monteAuDecolage21 = new MonteAuDecolage(surfacesStart21.getPointLimite1(),
                        surfacesStart21.getPointLimite2());

        // surfaces circulaire
        Conique conique = new Conique(road.getCenter());
        HorizentaleInterieur horizentaleInterieur = new HorizentaleInterieur(road.getCenter());

        // boolean values to check if the layers are added to the map

        // AeroDrome
        private boolean isApprocheLayerAdded = false;
        private boolean isMonteAuDecolageLayerAdded = false;
        private boolean isHorizentaleInterieurLayerAdded = false;
        private boolean isConiqueLayerAdded = false;
        // Radionavs
        private boolean isVORAdded = false;
        private boolean isDMEAdded = false;
        private boolean isVHFTxAdded = false;
        private boolean isVHFRxAdded = false;
        private boolean isSSRAdded = false;

        // Map layers

        CircleMapLayer horizentaleInterieurLayer;
        CircleMapLayer coniqueLayer;
        PolygonMapLayer MonteAuDecolageLayer21;
        PolygonMapLayer MonteAuDecolageLayer03;
        PolygonMapLayer ApprocheLayer03;
        PolygonMapLayer ApprocheLayer21;
        DoubleCircleMapLayer VORLayer;
        DoubleCircleMapLayer DMELayer;
        DoubleCircleMapLayer VHFTxLayer;
        DoubleCircleMapLayer VHFRxLayer;
        DoubleCircleMapLayer SSRLayer;

        boolean[] isOmniAdded = { isVORAdded, isDMEAdded, isVHFTxAdded, isVHFRxAdded, isSSRAdded };

        // Dealing with light night switch
        public final String LIGHT_THEME = getClass().getResource("/com/aeroguard/css/lightthemestyle.css")
                        .toExternalForm();
        public final String DARK_THEME = getClass().getResource("/com/aeroguard/css/darktheme.css").toExternalForm();
        private boolean isLightMode = true;

        @FXML
        private void initialize() {
                Switch.setOnAction(event -> toggleTheme());
                generateReportButton.setOnAction(event -> generateReport());
                surfaceMonteeDecolageMenuItem.setOnAction(event -> DisplayMonteAuDecolage());
                surfaceConiqueMenuItem.setOnAction(event -> DisplayConique());
                surfaceHorizentaleInterieurMenuItem.setOnAction(event -> DisplayHorizentaleInterieur());
                surfaceApprocheMenuItem.setOnAction(event -> DisplayApproche());
                vorMenuItem.setOnAction(event -> DisplayOmnidirectionalEquipement(0));
                dmeMenuItem.setOnAction(event -> DisplayOmnidirectionalEquipement(1));
                vhftxMenuItem.setOnAction(event -> DisplayOmnidirectionalEquipement(2));
                VHFRxMenuItem.setOnAction(event -> DisplayOmnidirectionalEquipement(3));
                ssrMenuItem.setOnAction(event -> DisplayOmnidirectionalEquipement(4));

                mapView = new MapView();
                mapView.setPrefSize(MapViewPane.getPrefWidth(), MapViewPane.getPrefHeight());
                MapViewPane.getChildren().add(mapView);

                // used to Set an initial map point and zoom level (0-18, where 0 is world view and 18 is very detailed)

                MapPoint initialPoint = new MapPoint(24.1542205017935, -16.47111928005603);
                mapView.flyTo(0, initialPoint, 0.1);
                mapView.setZoom(10);

                // buttons to zoom in and out , to be added in the main-view.fxml and linked from here
                // zoomInButton.setOnAction(e -> mapView.setZoom(mapView.getZoom() + 1)); //
                // Zoom in
                // zoomOutButton.setOnAction(e -> mapView.setZoom(mapView.getZoom() - 1)); //
                // Zoom out

                //scroll based zoomin , needs more refining to be smoother 
                MapViewPane.addEventFilter(ScrollEvent.SCROLL, event -> {
                        double delta = event.getDeltaY();
                        if (delta != 0) {

                                double currentZoom = mapView.getZoom();

                                // Calculate the new zoom level
                                double newZoom = (delta > 0) ? currentZoom + 1 : currentZoom - 1;
                                MapPoint centerPoint = mapView.getCenter();
                                MapPoint mouseMapPoint = mapView.getMapPosition(event.getX(), event.getY());
                                double latDiff = mouseMapPoint.getLatitude() - centerPoint.getLatitude();
                                double lonDiff = mouseMapPoint.getLongitude() - centerPoint.getLongitude();
                                double newLat = mouseMapPoint.getLatitude()
                                                - (latDiff / Math.pow(2, newZoom - currentZoom));
                                double newLon = mouseMapPoint.getLongitude()
                                                - (lonDiff / Math.pow(2, newZoom - currentZoom));
                                // MapPoint newCenter = new MapPoint(road.getCenter().getLatitude(),
                                //                 road.getCenter().getLongitude());

                                MapPoint newCenter = new MapPoint(newLat, newLon);
                                mapView.setZoom(newZoom);
                                mapView.flyTo(0, newCenter, 0.3); // a modifyer la7i9an
                        }
                        event.consume();
                });

                MarkerLayer markerLayer = new MarkerLayer();
                mapView.addLayer(markerLayer);
                mapView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                        if (event.getButton() == MouseButton.PRIMARY) {
                                // Get the latitude and longitude where the user clicked
                                double latitude = mapView.getMapPosition(event.getX(), event.getY()).getLatitude();
                                double longitude = mapView.getMapPosition(event.getX(), event.getY()).getLongitude();
                                MapPoint currentMarker = new MapPoint(latitude, longitude);
                                markerLayer.addMarker(latitude, longitude);
                                setCoordinates(latitude, longitude);
                        }
                });

                // Infrastructure

                Point RotationPointRoad = road.getBase().BeginingLine.getCenter();
                Rectangle rotatedRoad = road.getBase().rotateRectangle(-2.15, RotationPointRoad, 2980);
                road.setBase(rotatedRoad);
                List<double[]> RoadPoints = new ArrayList<>();
                RoadPoints.add(new double[] { 23.70585, -15.93742 });
                RoadPoints.add(new double[] { 23.70602, -15.93782 });
                RoadPoints.add(
                                new double[] { road.getBottomRightCorner().getLatitude(),
                                                road.getBottomRightCorner().getLongitude() });
                RoadPoints.add(
                                new double[] { road.getBottomLeftCorner().getLatitude(),
                                                road.getBottomLeftCorner().getLongitude() });
                PolygonMapLayer RoadLayer = new PolygonMapLayer(RoadPoints, Color.color(0, 0, 0));
                mapView.addLayer(RoadLayer);

                Point RotationPointRoadBande = roadBande.getBase().BeginingLine.getCenter();
                Rectangle rotatedRoadBande = roadBande.getBase().rotateRectangle(-2.15, RotationPointRoadBande, 3120);
                roadBande.setBase(rotatedRoadBande);
                List<double[]> RoadBandePoints = new ArrayList<>();
                RoadBandePoints.add(new double[] { roadBande.getBottomRightCorner().getLatitude(),
                                roadBande.getBottomRightCorner().getLongitude() });
                RoadBandePoints.add(new double[] { roadBande.getBottomLeftCorner().getLatitude(),
                                roadBande.getBottomLeftCorner().getLongitude() });
                RoadBandePoints.add(new double[] { roadBande.getTopLeftCorner().getLatitude(),
                                roadBande.getTopLeftCorner().getLongitude() });
                RoadBandePoints.add(new double[] { roadBande.getTopRightCorner().getLatitude(),
                                roadBande.getTopRightCorner().getLongitude() });
                PolygonMapLayer roadBandeLayer = new PolygonMapLayer(RoadBandePoints, Color.BLACK);
                mapView.addLayer(roadBandeLayer);

                // Map layers
                List<double[]> MonteAuDecolagePoints21 = new ArrayList<>();
                MonteAuDecolagePoints21.add(new double[] { surfacesStart21.getPointLimite2().getLatitude(),
                                surfacesStart21.getPointLimite2().getLongitude() });
                MonteAuDecolagePoints21.add(new double[] { surfacesStart21.getPointLimite1().getLatitude(),
                                surfacesStart21.getPointLimite1().getLongitude() });
                MonteAuDecolagePoints21.add(
                                new double[] { monteAuDecolage21.getP4().getLatitude(),
                                                monteAuDecolage21.getP4().getLongitude() });
                MonteAuDecolagePoints21.add(
                                new double[] { monteAuDecolage21.getP3().getLatitude(),
                                                monteAuDecolage21.getP3().getLongitude() });
                MonteAuDecolageLayer21 = new PolygonMapLayer(MonteAuDecolagePoints21, Color.RED);

                List<double[]> MonteAuDecolagePoints03 = new ArrayList<>();
                MonteAuDecolagePoints03.add(new double[] { surfacesStart03.getPointLimite1().getLatitude(),
                                surfacesStart03.getPointLimite1().getLongitude() });
                MonteAuDecolagePoints03.add(new double[] { surfacesStart03.getPointLimite2().getLatitude(),
                                surfacesStart03.getPointLimite2().getLongitude() });
                MonteAuDecolagePoints03.add(
                                new double[] { monteAuDecolage03.getP3().getLatitude(),
                                                monteAuDecolage03.getP3().getLongitude() });
                MonteAuDecolagePoints03.add(
                                new double[] { monteAuDecolage03.getP4().getLatitude(),
                                                monteAuDecolage03.getP4().getLongitude() });
                MonteAuDecolageLayer03 = new PolygonMapLayer(MonteAuDecolagePoints03, Color.RED);

                List<double[]> approche03Points = new ArrayList<>();
                approche03Points.add(new double[] { surfacesStart03.getPointLimite1().getLatitude(),
                                surfacesStart03.getPointLimite1().getLongitude() });
                approche03Points.add(new double[] { surfacesStart03.getPointLimite2().getLatitude(),
                                surfacesStart03.getPointLimite2().getLongitude() });
                approche03Points.add(
                                new double[] { approach03.getP3().getLatitude(), approach03.getP3().getLongitude() });
                approche03Points.add(
                                new double[] { approach03.getP4().getLatitude(), approach03.getP4().getLongitude() });
                ApprocheLayer03 = new PolygonMapLayer(approche03Points, Color.BLUE);

                List<double[]> approche21 = new ArrayList<>();
                approche21.add(new double[] { surfacesStart21.getPointLimite2().getLatitude(),
                                surfacesStart21.getPointLimite2().getLongitude() });
                approche21.add(new double[] { surfacesStart21.getPointLimite1().getLatitude(),
                                surfacesStart21.getPointLimite1().getLongitude() });
                approche21.add(new double[] { approach21.getP4().getLatitude(), approach21.getP4().getLongitude() });
                approche21.add(new double[] { approach21.getP3().getLatitude(), approach21.getP3().getLongitude() });
                ApprocheLayer21 = new PolygonMapLayer(approche21, Color.BLUE);

                coniqueLayer = new CircleMapLayer(mapView, road.getCenter().getLatitude(),
                                road.getCenter().getLongitude(),
                                conique.getRadius(), Color.YELLOW);
                horizentaleInterieurLayer = new CircleMapLayer(mapView, road.getCenter().getLatitude(),
                                road.getCenter().getLongitude(), horizentaleInterieur.getRadius(), Color.GREEN);

                VORLayer = new DoubleCircleMapLayer(mapView, 23.7439772, -15.9190315, vor.getr(), vor.getR(),
                                Color.BLACK, Color.GREY);
                DMELayer = new DoubleCircleMapLayer(mapView, 23.7439772, -15.9190315, dme.getr(), dme.getR(),
                                Color.BLACK, Color.GREY);
                VHFTxLayer = new DoubleCircleMapLayer(mapView, 23.713999,
                                -15.928170, vhfTX.getr(), vhfTX.getR(), Color.BLACK, Color.GREY);
                VHFRxLayer = new DoubleCircleMapLayer(mapView, 23.713999,
                                -15.928170, vhfRX.getr(), vhfRX.getR(), Color.BLACK, Color.GREY);
                SSRLayer = new DoubleCircleMapLayer(mapView, 23.7115909, -15.9275974, ssr.getr(), ssr.getR(),
                                Color.BLACK, Color.GREY);
        }

        public void setCoordinates(double latitude, double longitude) {
                latitudeField.setText(String.valueOf(latitude));
                longitudeField.setText(String.valueOf(longitude));
        }

        public void toggleTheme() {
                if (isLightMode) {
                        mainViewScene.getStylesheets().remove(LIGHT_THEME);
                        mainViewScene.getStylesheets().add(DARK_THEME);
                } else {
                        mainViewScene.getStylesheets().remove(DARK_THEME);
                        mainViewScene.getStylesheets().add(LIGHT_THEME);
                }
                isLightMode = !isLightMode; 
        }

        public void DisplayMonteAuDecolage() {
                if (MonteAuDecolageLayer03 != null && MonteAuDecolageLayer21 != null) {
                        PolygonLayerTask task1 = new PolygonLayerTask(mapView, MonteAuDecolageLayer03,
                                        !isMonteAuDecolageLayerAdded);
                        new Thread(task1).start();

                        PolygonLayerTask task2 = new PolygonLayerTask(mapView, MonteAuDecolageLayer21,
                                        !isMonteAuDecolageLayerAdded);
                        new Thread(task2).start();

                        isMonteAuDecolageLayerAdded = !isMonteAuDecolageLayerAdded;
                } else {
                        System.out.println("One or more layers are null.");
                }
                Platform.runLater(() -> {
                        mapView.requestLayout();
                });
        }

        public void DisplayConique() {
                CircleLayerTask task = new CircleLayerTask(mapView, coniqueLayer, !isConiqueLayerAdded);
                new Thread(task).start();
                Platform.runLater(() -> {
                        mapView.requestLayout();
                });
                isConiqueLayerAdded = !isConiqueLayerAdded;
        }

        public void DisplayHorizentaleInterieur() {
                CircleLayerTask task = new CircleLayerTask(mapView, horizentaleInterieurLayer,
                                !isHorizentaleInterieurLayerAdded);
                new Thread(task).start();
                Platform.runLater(() -> {
                        mapView.requestLayout();
                });
                isHorizentaleInterieurLayerAdded = !isHorizentaleInterieurLayerAdded;
        }

        public void DisplayApproche() {
                PolygonLayerTask task1 = new PolygonLayerTask(mapView, ApprocheLayer03, !isApprocheLayerAdded);
                new Thread(task1).start();

                PolygonLayerTask task2 = new PolygonLayerTask(mapView, ApprocheLayer21, !isApprocheLayerAdded);
                new Thread(task2).start();

                Platform.runLater(() -> {
                        mapView.requestLayout();
                });

                isApprocheLayerAdded = !isApprocheLayerAdded;
        }

        public void DisplayOmnidirectionalEquipement(int number) {
                DoubleCircleMapLayer[] radiosLayers = { VORLayer, DMELayer, VHFTxLayer, VHFRxLayer, SSRLayer };
                CircleLayerTask task1 = new CircleLayerTask(mapView, radiosLayers[number], !isOmniAdded[number]);
                new Thread(task1).start();
                Platform.runLater(() -> {
                        mapView.requestLayout();
                });
                isOmniAdded[number] = !isOmniAdded[number];
        }

        public void generateReport() {
                String longitudeText = longitudeField.getText();
                String latitudeText = latitudeField.getText();
                String altitudeText = altitudeField.getText();
                String heightText = heightField.getText();
                String descriptionText = DescriptionField.getText();

                // Validate input data
                if (longitudeText.isEmpty() || latitudeText.isEmpty() || altitudeText.isEmpty() || heightText.isEmpty()
                                || descriptionText.isEmpty()) {
                        showAlert("Veuillez entrer tous les champs correctement.");
                        return;
                }

                double longitude, latitude;
                float altitude, height;

                try {
                        longitude = Double.parseDouble(longitudeText);
                        latitude = Double.parseDouble(latitudeText);
                        altitude = Float.parseFloat(altitudeText);
                        height = Float.parseFloat(heightText);
                } catch (NumberFormatException e) {
                        showAlert("Veuillez entrer des valeurs numériques valides pour la longitude, la latitude, l'altitude et la hauteur.");
                        return;
                }

                String description = descriptionText;
                Obstacle obstacle = new Obstacle(latitude, longitude, height, altitude, description);

                // Collect penetration test results + filling the report data f latex file 
                ArrayList<Double> penetrationList = new ArrayList<>();

                // aerodrome
                penetrationList.add(approach03.obstaclePenetration(obstacle));
                penetrationList.add(monteAuDecolage03.obstaclePenetration(obstacle));
                penetrationList.add(approach21.obstaclePenetration(obstacle));
                penetrationList.add(monteAuDecolage21.obstaclePenetration(obstacle));
                penetrationList.add(conique.obstaclePenetration(obstacle));
                penetrationList.add(horizentaleInterieur.obstaclePenetration(obstacle));

                // radionavs&radiocomms
                penetrationList.add(vor.danger(obstacle));
                penetrationList.add(dme.danger(obstacle));
                penetrationList.add(vhfTX.danger(obstacle));
                penetrationList.add(vhfRX.danger(obstacle));
                penetrationList.add(ssr.danger(obstacle));

                Map<String, String> reportData = new HashMap<>();
                reportData.put("\\textbf{description}", description);
                reportData.put("\\textbf{latitude}", latitudeText);
                reportData.put("\\textbf{longitude}", longitudeText);
                reportData.put("\\textbf{altitude}", altitudeText);
                reportData.put("\\textbf{height}", heightText);

                reportData.put("\\textbf{@PenetrationTestResultApproche03}", Double.toString(penetrationList.get(0)));
                reportData.put("\\textbf{@PenetrationTestResultMonteAuDecolage03}",
                                Double.toString(penetrationList.get(1)));
                reportData.put("\\textbf{@PenetrationTestResultApproche21}", Double.toString(penetrationList.get(2)));
                reportData.put("\\textbf{@PenetrationTestResultMonteAuDecolage03}",
                                Double.toString(penetrationList.get(3)));
                reportData.put("\\textbf{@PenetrationTestResulthorizentaleinterieur}",
                                Double.toString(penetrationList.get(4)));
                reportData.put("\\textbf{@PenetrationTestResulconique}", Double.toString(penetrationList.get(5)));

                reportData.put("\\textbf{@PenetrationTestResultDVOR}", Double.toString(penetrationList.get(6)));
                reportData.put("\\textbf{@PenetrationTestResultDME}", Double.toString(penetrationList.get(7)));
                reportData.put("\\textbf{@PenetrationTestResultVHFTX}", Double.toString(penetrationList.get(8)));
                reportData.put("\\textbf{@PenetrationTestResultVHFRX}", Double.toString(penetrationList.get(9)));
                reportData.put("\\textbf{@PenetrationTestResultSSR}", Double.toString(penetrationList.get(10)));

                // the downloading procees , file chosing ...
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Report");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

                // Display save dialog and retrieve selected file
                File selectedFile = fileChooser.showSaveDialog(null);

                // Create and configure the ReportGenerator task
                File templateFile = new File("Report/Report_Template.tex");
                ReportGenerator reportTask = new ReportGenerator(templateFile, selectedFile, reportData);

                // Set event handlers for success and failure , useful for debugging , not needed in production . 
                reportTask.setOnSucceeded(event -> System.out.println("Report generated and compiled successfully!"));
                reportTask.setOnFailed(
                                event -> System.err.println("Report generation failed: " + reportTask.getMessage()));

                new Thread(reportTask).start();
        }

        //useful ms not yet used 
        private void showAlert(String message) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de validation");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
        }

        public void setScene(Scene scene) {
                this.mainViewScene = scene;
        }


        //getters to use things i defined in future unit tests , not a best practice tho , its better to define them in a separate class.

        
        public  Road getRoad() {
                return road;
        }

        public Road getRoadBande() {
                return roadBande;
        }

        public Approach getApproach03() {
                return approach03;
        }

        public MonteAuDecolage getMonteAuDecolage03() {
                return monteAuDecolage03;
        }

        public Approach getApproach21() {
                return approach21;
        }

        public MonteAuDecolage getMonteAuDecolage21() {
                return monteAuDecolage21;
        }

        public Conique getConique() {
                return conique;
        }

        public HorizentaleInterieur getHorizentaleInterieur() {
                return horizentaleInterieur;
        }
}