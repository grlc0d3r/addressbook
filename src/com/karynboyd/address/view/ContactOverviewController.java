/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karynboyd.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.karynboyd.address.MainApp; 
import com.karynboyd.address.model.Contact;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Karyn
 */
public class ContactOverviewController
{
    // to access the variables, need to use @FXML  - technically just for private but my fingers go stuck
    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label stateLabel;
    @FXML
    private Label zipLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    
    private MainApp mainApp;
    
    public ContactOverviewController() {}
    
    @FXML
    private void initialize()
    {
        // NOTE: because of Java8 wonkiness, if you need an Integer in a column, the cellValueFactory has to be set up
        // a little differently 
        // columnName.setCellValueFactory(cellData -> cellData.getValue().theIntegerProperty().asObject());        
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());    
        
        // once showContactDetails() is set up.... add this to reset the details
        showContactDetails(null);
        
        // then this is the listener keeping an ear out for selection changes
        contactTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showContactDetails(newValue));
        // so when the selectedItem(property) in the contactTable changes from the oldValue (the original selected contact) to the newValue (the newly selected contact
        // change the Contact Details view to the information from the newly selected contact using the information in the observable list
    }
    
    public void setMainApp(MainApp mainApp)
    {
        // updated showContactOverview to reference this from the MainApp
        this.mainApp = mainApp;
        contactTable.setItems(mainApp.getContactData());
    }
    
    // helps fill in labels with 1 contact's data
    private void showContactDetails(Contact contact)
    {
        // check to see if the contact is null - meaning no contact selected
        if (contact != null)
        {
            // for each label, set the text to get that property
            firstNameLabel.setText(contact.getFirstName());
            lastNameLabel.setText(contact.getLastName());
            streetLabel.setText(contact.getStreet());
            cityLabel.setText(contact.getCity());
            stateLabel.setText(contact.getState());
            // because zip isn't a StringProperty, we have to use Integer.toString
            zipLabel.setText(Integer.toString(contact.getZip()));
            emailLabel.setText(contact.getEmail());
            phoneLabel.setText(contact.getPhone());
        }
        else
        {
            // there's nothing selected so make certain everything is set to ""
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            cityLabel.setText("");
            stateLabel.setText("");
            zipLabel.setText("");
            emailLabel.setText("");
            phoneLabel.setText("");
        }
    }
    
    // adding the code for the delete button using FXML
    @FXML
    private void handleDeleteContact()
    {
        // once this method is setup, set the on action of the Delete button to handleDeletePerson 
        int selectedIndex = contactTable.getSelectionModel().getSelectedIndex();        
        if (selectedIndex >- 0)
        {
            // adding error handling just in case someone clicks DELETE without selecting a contact (which would be a -1)
            contactTable.getItems().remove(selectedIndex);                
        }
        else
        {
            // use the Alert class to let the user know they've not selected anything
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage()); // where is this going to show up at/over?
            alert.setTitle("Oops!");
            alert.setHeaderText("No selection made");
            alert.setContentText("Make certain you've selected a Contact in the table to the left before clicking delete");
            
            alert.showAndWait(); // for user OK
        }
    }
    
    // code for New button
   /* if I add a no arg constructor for Contact, I keep getting a "variable firstname may not have been initialized" and NOTHING has worked so far
    so for now - commenting out this code until I can figure out how to fix it */
    @FXML
    private void handleNewContact() 
    {
       // System.out.println("Still working on this. I keep getting a 'variable firstName may not have been initialized' error when I have the noarg entered");
      
        Contact tempContact = new Contact(); 
        boolean okClicked = mainApp.showContactEditDialog(tempContact);
        if (okClicked)
        {
            mainApp.getContactData().add(tempContact);
        } 
    } 
    
    @FXML
    private void handleEditContact()
    {
        Contact selected = contactTable.getSelectionModel().getSelectedItem();
        if(selected != null)
        {
            boolean okClicked = mainApp.showContactEditDialog(selected);
            if (okClicked) 
            {
                showContactDetails(selected);
            }            
        }
        else
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Oops!");
            alert.setHeaderText("No selection made");
            alert.setContentText("Please make a selection from the Contact Table");
            
            alert.showAndWait();
        }
    }
    
}
