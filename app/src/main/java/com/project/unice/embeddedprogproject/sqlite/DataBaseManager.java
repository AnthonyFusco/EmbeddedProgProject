package com.project.unice.embeddedprogproject.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Gestion de la base de donnees
 */
public abstract class DataBaseManager implements IDatabaseManager {
    private final static int VERSION = 1;
    public static final String DATABASE_NAME = "database.db";
    private SQLiteDatabase database = null;
    private DataBaseCreator handler = null;
    private Context context;

    public DataBaseManager(Context pContext, String nomBase) {
        this.context = pContext;
        this.handler = new DataBaseCreator(pContext, nomBase, null, VERSION);
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public Context getContext() {
        return context;
    }

    public SQLiteDatabase open() {
        database = handler.getWritableDatabase();
        return database;
    }

    public void close() {
        database.close();
    }

}
