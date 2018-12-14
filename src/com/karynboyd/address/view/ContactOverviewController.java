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
    }
    
    public void setMainApp(MainApp mainApp)
    {
        // updated showContactOverview to reference this from the MainApp
        this.mainApp = mainApp;
        contactTable.setItems(mainApp.getContactData());
    }
}
