package com.project.unice.embeddedprogproject.sqlite.databaseModels;

import android.content.ContentValues;

public interface IModel {

    String getTableName();
    void put(ContentValues value);
}
