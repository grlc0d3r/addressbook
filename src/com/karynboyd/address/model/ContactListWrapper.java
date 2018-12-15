package com.karynboyd.address.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// need to create a wrapper/helper class to save the list into XML for storing
// because personData is an ObservableList which can't be annotated but Java Architecture for XML Binding - JAXB - needs the topmost class to be annotated
// RootElement is the <type> and then XmlElement is each instance like <books> as the root because it'll be an XML filled with books and <book> as the element for a specific book
@XmlRootElement(name = "contactz")
public class ContactListWrapper 
{
    private List<Contact> contacts;
    
    @XmlElement(name = "contact")
    public List<Contact> getContacts() { return contacts; }
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }
}
