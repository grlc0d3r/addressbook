package com.karynboyd.address.view;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import com.karynboyd.address.MainApp;

public class RootLayoutController 
{
    // need a reference to the Main App
    private MainApp mainApp;
    
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
    
    // create empty address book for File -->  New in the menu
    @FXML
    private void handleNew()
    {
        mainApp.getContactData().clear();
        mainApp.setDataFilePath(null);
    }
    
    // opens FileChooser for File --> open in menu 
    @FXML
    private void handleOpen()
    {
        FileChooser fileChooser = new FileChooser();
        // filters extensions so only XML file extensions are accepted
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(filter);
        
        // open up the open dialog box
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        
        if (file != null) { mainApp.loadContactDataFile(file); }
    }
        
    
    // sets up File--> Save menu
    @FXML
    private void handleSave() 
    {
        File contactFile = mainApp.getDataFilePath();
        if(contactFile != null)
        {
            mainApp.saveContactDataToFile(contactFile);
        }
        else
        {
            handleSaveAs();
        }
        
    }
    
    // uses FileChooser to select a file to save to
    @FXML
    private void handleSaveAs() 
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(filter);
        
        // show the save dialog box
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        
        if (file != null)
        {
            // check the extension before saving
            if (!file.getPath().endsWith(".xml"))
            {
                file = new File(file.getPath() + ".xml");
            }
            
            mainApp.saveContactDataToFile(file);
        }
    }
    
    // closes out of application with File --> Close
    @FXML
    private void handleClose()
    {
        System.exit(0);
    }
}
