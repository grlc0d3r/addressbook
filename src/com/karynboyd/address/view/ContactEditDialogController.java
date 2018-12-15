/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karynboyd.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.karynboyd.address.model.Contact;


/**
 *
 * @author Karyn
 */
public class ContactEditDialogController 
{
    // so kinda the same thing as with ContactOverview Controller
    // use @FXML so the data can access the view and vice versa
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField stateField;
    @FXML
    private TextField zipField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    
    private Stage dialogStage;
    private Contact contact;
    private boolean okClicked = false; // refers to the OK button
    
    // initializes the controller; called after FXML file loads
    @FXML
    private void initialize() {}
    
    public void setDialogStage(Stage dialogStage)
    {
        // sets up the dialogue box, like primaryStage starts up the main window
        this.dialogStage = dialogStage;
    }
    
    // with the stage setup, now we need to set up loading the person's contact info in for editing
    public void setContact(Contact contact)
    {
        this.contact = contact;
        
        // just like with the contact details side of the main window, we need to match the view (the text fields)
        // to the data
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        streetField.setText(contact.getStreet());
        cityField.setText(contact.getCity());
        stateField.setText(contact.getState());
        zipField.setText(Integer.toString(contact.getZip())); // and we'll have to use the toString for Integer, just like contact details
        emailField.setText(contact.getEmail());
        phoneField.setText(contact.getPhone());
    }
    
    // changes to "true" if OK is clicked
    public boolean isOkClicked() { return okClicked; }
    @FXML
    private void handleOk() 
    {
        // when the user clicks ok, this will check the input, set the okClicked flag to true, and close out the dialog window
        if (isInputValid())
        {
            // valid input?
            contact.setFirstName(firstNameField.getText());
            contact.setLastName(lastNameField.getText());
            contact.setStreet(streetField.getText());
            contact.setCity(cityField.getText());
            contact.setState(stateField.getText());
            contact.setZip(Integer.parseInt(zipField.getText())); // note the Integer.parseInt b/c zip is an int
            contact.setEmail(emailField.getText());
            contact.setPhone(phoneField.getText());
            
            
            // change the flag to true
            okClicked = true;
            // close the dialog box
            dialogStage.close();
        }
    }
    
    // method for validating fields
    private boolean isInputValid() 
    {
        String message = ""; // has to be initialized here else you get an error in message.length
        
        if(firstNameField.getText() == null || firstNameField.getText().length() == 0)
        {
            message += "Please enter a first name\n";
        }
        
        if(lastNameField.getText() == null || lastNameField.getText().length() == 0)
        {
            message += "Please enter a last name\n";
        }
        
        if (message.length() == 0)
        {
            return true;
        }
        
        else 
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage); // where does this pop up at/over
            alert.setTitle("Oops!");
            alert.setHeaderText("Invalid entry");
            alert.setContentText(message);
            
            alert.showAndWait();
            return false;
        }
    }
    
    // if cancel is clicked, just close the dialog box
    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }
}
