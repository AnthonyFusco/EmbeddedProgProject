package com.project.unice.embeddedprogproject.fragments.views;

import com.project.unice.embeddedprogproject.pages.Contact;

import java.util.List;

interface IContactManager {
    List<Contact> getContacts(String title);
}
