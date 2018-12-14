/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karynboyd.address;

import com.karynboyd.address.model.Contact;
import com.karynboyd.address.view.ContactOverviewController;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    
    // data is in an observable list because we can see the list as we're changing the data (it's not something we have to change, then print, then view our changes
    private ObservableList<Contact> contactData = FXCollections.observableArrayList();
    // testing data in a constructor
    public MainApp()
    {
        contactData.add(new Contact("Harry", "Dresden", "12345 Wizard Way", "Chicago", "IL", 11111, "hdresden@wizard.com", "888-222-3333"));
        contactData.add(new Contact("Carlos", "Rodriguez", "6789 Suave Lane", "Austin", "TX", 11111, "losrodriguez@wizard.com", "888-333-4444"));
        contactData.add(new Contact("Karrin", "Murphy", "91082 Blue Line Rd", "Chicago", "IL", 11111, "kmurphy@akido.com", "888-444-5555"));
        contactData.add(new Contact("Thomas", "Raith", "21400 Lovers Lane", "Chicago", "IL", 11111, "traith@raith.com", "888-555-6666"));
        contactData.add(new Contact("Waldo", "Butters", "936723 Muerte Circle", "Chicago", "IL", 11111, "wbutters@city.com", "888-777-8888"));
    }
    // returning the contact data
    public ObservableList<Contact> getContactData() { return contactData; }
    
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
            
            ContactOverviewController controller = loader.getController();
            controller.setMainApp(this);
            // after controller is call from MainApp - update ContactOverviewCOntroller to match the view's stuff (like the columns) to the data in the controller
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
