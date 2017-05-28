package com.project.unice.embeddedprogproject.sms;

import com.project.unice.embeddedprogproject.sqlite.databaseModels.IModel;

public interface ISender {

    public void send(IModel iModel, String contactNumber);
}
