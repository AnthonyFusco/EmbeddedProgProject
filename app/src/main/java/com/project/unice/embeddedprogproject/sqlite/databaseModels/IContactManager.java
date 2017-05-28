package com.project.unice.embeddedprogproject.sqlite.databaseModels;

import java.util.List;


/**
 * Interface to handle multiple ContentResolver
 */
public interface IContactManager {
    /**
     * Get the list of contact to be used by a listView.
     * @see Contact
     * @return the list of Contact Objects
     */
    List<IModel> getContacts();
}
