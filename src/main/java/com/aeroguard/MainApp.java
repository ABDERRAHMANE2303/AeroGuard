package com.aeroguard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.aeroguard.controllers.HomeController;
import com.aeroguard.controllers.MainViewController;

public class MainApp extends Application {

    private Stage primaryStage;
    private Scene homeScene;
    private Scene mainViewScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Load Home Page
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/com/aeroguard/fxml/home.fxml"));
        Parent homeRoot = homeLoader.load();

        // Set controller reference
        HomeController homeController = homeLoader.getController();
        homeController.setMainApp(this); // Set the main app reference

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/aeroguard/images/HomeBackground.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);

        // Creates home page
        backgroundImageView.setFitWidth(1280);
        backgroundImageView.setFitHeight(720);
        backgroundImageView.setPreserveRatio(false);
        StackPane homeStackPane = new StackPane();
        homeStackPane.getChildren().addAll(backgroundImageView, homeRoot);
        homeScene = new Scene(homeStackPane, 1280, 720);
        homeScene.getStylesheets().add(getClass().getResource("/com/aeroguard/css/home.css").toExternalForm());

        // Load Main View
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/com/aeroguard/fxml/main-view.fxml"));
        Parent mainViewRoot = mainViewLoader.load();
        MainViewController mainViewController = mainViewLoader.getController();

        mainViewScene = new Scene(mainViewRoot, 1279, 720);
        mainViewScene.getStylesheets()
                .add(getClass().getResource("/com/aeroguard/css/lightthemestyle.css").toExternalForm());
        mainViewController.setScene(mainViewScene);

        // Set up the stage
        Image logo = new Image(getClass().getResourceAsStream("/com/aeroguard/images/AeroGuardLogo.png"), 500, 500,
                true, true);
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("AeroGuard");

        primaryStage.setScene(homeScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void switchToMainView() {
        primaryStage.setScene(mainViewScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
