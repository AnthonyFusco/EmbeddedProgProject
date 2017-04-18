package com.project.unice.embeddedprogproject.sms;

import com.project.unice.embeddedprogproject.models.Contact;

public interface ISender {

    public void send(Contact contact);
}
