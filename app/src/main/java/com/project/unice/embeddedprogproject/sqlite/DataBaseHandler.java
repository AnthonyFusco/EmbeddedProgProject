package com.project.unice.embeddedprogproject.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.unice.embeddedprogproject.models.AbstractModel;
import com.project.unice.embeddedprogproject.models.BusinessCard;

public class DataBaseHandler extends SQLiteOpenHelper {

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AbstractModel.createTableRequest(BusinessCard.class));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AbstractModel.dropTableRequest(BusinessCard.class));
        onCreate(db);
    }
}