package com.aeroguard.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import com.aeroguard.MainApp;

public class HomeController {

    private MainApp mainApp; 
    @FXML
    private Button switchbutton;

    public void initialize() {
        switchbutton.setOnAction(event -> handleButtonClick());
    }

    // This method is called by the MainApp to give a reference back to it
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    
    @FXML
    public void handleButtonClick() {
        mainApp.switchToMainView();
    }
}
