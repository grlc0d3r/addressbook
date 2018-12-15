/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.karynboyd.address.model;

import javafx.beans.property.IntegerProperty; // for zip
import javafx.beans.property.SimpleIntegerProperty; 
import javafx.beans.property.StringProperty; // for everything else
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Karyn
 */
public class Contact 
{
    // use Properties so that changes to variables in the window update the data
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty street;
    private final StringProperty city;
    private final StringProperty state;
    private final IntegerProperty zip;
    private final StringProperty email;
    private final StringProperty phone;
 
    public Contact() 
    {
        this(null, null);
    }

    public Contact(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

        // data for testing
        this.street = new SimpleStringProperty("some street");
        this.zip = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("some city");
        this.state = new SimpleStringProperty("IL");
       this.email = new SimpleStringProperty("someone@somecompany.com");
        this.phone = new SimpleStringProperty("111-222-3333");

   }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public StringProperty firstNameProperty() { return firstName; }
    
    public String getLastName() { return lastName.get(); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public StringProperty lastNameProperty() { return lastName; }
    
    public String getStreet() { return street.get(); }
    public void setStreet(String street) { this.street.set(street); }
    public StringProperty streetProperty() { return street; }
    
    public String getCity() { return city.get(); }
    public void setCity(String city) { this.city.set(city); }
    public StringProperty cityProperty() { return city; }
    
    public String getState() { return state.get(); }
    public void setState(String state) { this.state.set(state); }
    public StringProperty stateProperty() { return state; }
    
    public int getZip() { return zip.get(); }
    public void setZip(int zip) { this.zip.set(zip); }
    public IntegerProperty zipProperty() { return zip; }
    
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }
    
    public String getPhone() { return phone.get(); }
    public void setPhone(String phone) { this.email.set(phone); }
    public StringProperty phoneProperty() { return phone; }
}
