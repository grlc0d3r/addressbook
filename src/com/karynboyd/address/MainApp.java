/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karynboyd.address;

import com.karynboyd.address.model.Contact;
import com.karynboyd.address.model.ContactListWrapper;
import com.karynboyd.address.view.ContactEditDialogController;
import com.karynboyd.address.view.ContactOverviewController;
import com.karynboyd.address.view.RootLayoutController;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
        /*  no arg constructor for Contact doesn't work unless dummy data is filled in for everything except firstName and lastName
            these are the rest of the testing data, in the order of their first & lastNames below:
            , "12345 Wizard Way", "Chicago", "IL", 11111, "hdresden@wizard.com", "888-222-3333"
            , "6789 Suave Lane", "Austin", "TX", 11111, "losrodriguez@wizard.com", "888-333-4444"
            , "91082 Blue Line Rd", "Chicago", "IL", 11111, "kmurphy@akido.com", "888-444-5555"
            , "21400 Lovers Lane", "Chicago", "IL", 11111, "traith@raith.com", "888-555-6666"
            , "936723 Muerte Circle", "Chicago", "IL", 11111, "wbutters@city.com", "888-777-8888"
        */
        
        contactData.add(new Contact("Harry", "Dresden")); 
        contactData.add(new Contact("Carlos", "Rodriguez"));
        contactData.add(new Contact("Karrin", "Murphy"));
        contactData.add(new Contact("Thomas", "Raith"));
        contactData.add(new Contact("Waldo", "Butters"));
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
            
            // allow RootLayoutController to access MainApp
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        File file = getDataFilePath();
        if (file != null)
        {
            loadContactDataFile(file);
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
    
    public boolean showContactEditDialog(Contact contact)
    {
        // pops up a dialog box to edit a contact - OK saves changes; Cancel closes the dialog and puts user back into main window
        // added after ContactEditDialog.fxml and ContactEditDialogController.java were set up then added handleNewContact() to ContactOverviewController
        try
        {
            // setup the loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ContactEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            // setup the dialog stage and load the page into the scene and the scene into the stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Contact");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            // load the contact to the controller
            ContactEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setContact(contact);
            
            // tells the dialog to display and wait for user input
            dialogStage.showAndWait();
            
            return controller.isOkClicked();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        
    }
    
    public Stage getPrimaryStage() { return primaryStage; }

    public static void main(String[] args) { launch(args); }
    
    // keeps track of app state to persist data
    public File getDataFilePath()
    {
        // Java uses Preferences class to store application state in places like the registry file
        // can't use for entire address book but can use to store path to the last opened file which can store the app state
        Preferences storeState = Preferences.userNodeForPackage(MainApp.class);
        String path = storeState.get("path", null);
        if (path != null)
        {
            return new File(path);
        }
        else
        {
            return null;
        }
    }
    
    // this sets the path for the data file
    public void setDataFilePath(File file)
    {
        Preferences storeState = Preferences.userNodeForPackage(MainApp.class);
        if (file != null)
        {
            storeState.put("path", file.getPath());
            
            // Then update the primaryStage's title
            primaryStage.setTitle("Address Book- " + file.getName());
        }
        else
        {
            storeState.remove("path");
            primaryStage.setTitle("Address Book");
        }
    }
    
    public void loadContactDataFile(File file)
    {
        // gets stuff OUT of XML
        try
        {
            JAXBContext context = JAXBContext.newInstance(ContactListWrapper.class); 
            
            Unmarshaller pullData = context.createUnmarshaller();
            ContactListWrapper wrapper = (ContactListWrapper) pullData.unmarshal(file);
            
            contactData.clear();
            contactData.addAll(wrapper.getContacts());
            
            setDataFilePath(file);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Oops!");
            alert.setHeaderText("Error loading file data");
            alert.setContentText("Could not load file from file: \n" + file.getPath());
            
            alert.showAndWait();
        }
    }
    
    public void saveContactDataToFile(File file)
    {
        // gets stuff for 1 contact INTO the file
        try
        {
            JAXBContext context = JAXBContext.newInstance(ContactListWrapper.class);
            Marshaller pushData = context.createMarshaller();
            pushData.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            ContactListWrapper wrapper = new ContactListWrapper();
            wrapper.setContacts(contactData);
            
            pushData.marshal(wrapper, file);
            
            setDataFilePath(file);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Oops");
            alert.setHeaderText("Error saving data to file");
            alert.setContentText("Could not save data to file: \n" + file.getPath());
            
            alert.showAndWait();
        }
    }
}
