package com.project.unice.embeddedprogproject.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.unice.embeddedprogproject.sqlite.databaseModels.AbstractModel;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.BusinessCard;
import com.project.unice.embeddedprogproject.sqlite.databaseModels.Contact;


/**
 * Creation de la base de donnees
 * Pour ajouter des tables a la base il faut mettre a jour les deux fonctions onCreate et onUpgrade
 *
 * db.execSQL(AbstractModel.createTableRequest(NouvelleClasse.class));
 * db.execSQL(AbstractModel.dropTableRequest(NouvelleClasse.class));
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    public DataBaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AbstractModel.createTableRequest(BusinessCard.class));
        db.execSQL(AbstractModel.createTableRequest(Contact.class));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(AbstractModel.dropTableRequest(BusinessCard.class));
        db.execSQL(AbstractModel.dropTableRequest(Contact.class));
        onCreate(db);
    }
}