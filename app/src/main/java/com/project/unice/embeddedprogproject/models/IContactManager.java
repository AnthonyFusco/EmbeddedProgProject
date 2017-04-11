package com.project.unice.embeddedprogproject.models;

import com.project.unice.embeddedprogproject.pages.Contact;

import java.util.List;


/**
 * Interface to handle multiple ContentResolver
 */
public interface IContactManager {
    /**
     * Get the list of contact to be used by a listView.
     * @see Contact
     * @param filter possible filter to the list
     * @return the list of Contact Objects
     */
    List<Contact> getContacts(String filter);
}
