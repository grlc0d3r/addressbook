/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karynboyd.address;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Karyn
 */
public class MainApp extends Application 
{
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    @Override
    public void start(Stage primaryStage) 
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address Book");
        initRootLayout();
        showContactOverview();
    }
    
    public void initRootLayout()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            Scene scene = new Scene (rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void showContactOverview() 
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ContactOverview.fxml"));
            AnchorPane contactOverview = (AnchorPane) loader.load();
            
            rootLayout.setCenter(contactOverview);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() { return primaryStage; }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { launch(args); }
    
}
