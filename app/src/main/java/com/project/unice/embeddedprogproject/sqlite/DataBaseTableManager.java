package com.project.unice.embeddedprogproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.project.unice.embeddedprogproject.databaseModels.AbstractModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Gestion des tables de la base de donnees
 */
public class DataBaseTableManager extends DataBaseManager {

    public DataBaseTableManager(Context pContext, String nomBase) {
        super(pContext, nomBase);
    }

    /**
     * Ajout du model dans la base de donnees
     * @param model le model a ajouter
     */
    public void add(AbstractModel model) {
        open();
        ContentValues value = new ContentValues();
        model.put(value);
        getDatabase().insert(model.getTableName(), null, value);
        close();
    }


    /**
     * Supprimer un ensemble de models dans la base de donnees
     * @param list la liste des models a supprimer
     */
    public void removeRange(List<AbstractModel> list) {
        open();
        for (AbstractModel model : list) {
            AbstractModel.Tuple<Field, ModelAnnotation> tuple = model.getPrimaryProperties();
            try {
                getDatabase().delete(model.getTableName(), tuple.y.columnName() + " = ?", new String[]{String.valueOf(tuple.x.get(model))});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        close();
    }


    /**
     * Supprimer un model dans la base de donnees
     * @param model le model a supprimer
     */
    public void remove(AbstractModel model) {
        open();
        AbstractModel.Tuple<Field, ModelAnnotation> tuple = model.getPrimaryProperties();
        try {
            getDatabase().delete(model.getTableName(), tuple.y.columnName() + " = ?", new String[]{String.valueOf(tuple.x.get(model))});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        close();
    }
/*
    /**
     * modifier un model dans la base de donnees
     *
    public void modifyId(String columnAttribute, int newValue, String phoneNumber) {
        Cursor cursor = getDatabase().rawQuery("update Contact set " + columnAttribute + " = " + newValue + " where Phone  = ?", new String[]{String.valueOf(phoneNumber)});
        if (cursor != null) {
            cursor.close();
        }
    }*/

    @Override
    public void update(AbstractModel model) {
        remove(model);
        add(model);
    }

    /**
     * Selectionner un ensemble de models en fonction d'un predicat
     * @param classe le type mu model a traiter
     * @param columnName le nom de la colonne a filtrer
     * @param value la valeur a filtrer
     * @return liste de models
     */
    public List<IModel> selectWhere(Class<? extends IModel> classe, String columnName, Object value) {
        open();

        Cursor cursor = null;
        List<IModel> list = new ArrayList<>();

        try {
            cursor = getDatabase().rawQuery("select * from " + classe.newInstance().getTableName() + " where " + columnName + " = ?", new String[]{String.valueOf(value)});
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        while (cursor != null && cursor.moveToNext()) {
            IModel model = null;
            try {
                model = classe.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Field field : model.getClass().getDeclaredFields()) {
                try {
                    configureField(model, field, cursor);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            list.add(model);
        }
        if (cursor != null) {
            cursor.close();
        }

        close();
        return list;
    }


    /**
     * Search and return an element from the database.
     * @param classe the model class
     * @param columnName the column to do the filter
     * @param value the value to do the filter
     * @return the first element matching the parameters, null if it doesn't exist
     */
    public IModel findFirstValue(Class<? extends IModel> classe, String columnName, Object value){
        open();

        Cursor cursor = null;

        try {
            cursor = getDatabase().rawQuery("select * from " + classe.newInstance().getTableName() + " where " + columnName + " = ?", new String[]{String.valueOf(value)});
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        while (cursor != null && cursor.moveToNext()) {
            IModel model = null;
            try {
                model = classe.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (model != null) {
                for (Field field : model.getClass().getDeclaredFields()) {
                    try {
                        configureField(model, field, cursor);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            return model;
        }
        if (cursor != null) {
            cursor.close();
        }

        close();
        return null;
    }


    /**
     * Select all of the models of one type.
     * @param classe the type to select
     * @return list of the types
     */
    public List<IModel> selectAll(Class<? extends IModel> classe) {
        open();

        List<IModel> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = getDatabase().rawQuery("select * from " + classe.newInstance().getTableName(), null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        while (cursor != null && cursor.moveToNext()) {
            IModel model = null;
            try {
                model = classe.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            for (Field field : model.getClass().getDeclaredFields()) {
                try {
                    configureField(model, field, cursor);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
                list.add(model);
        }
        if (cursor != null) {
            cursor.close();
        }

        close();
        return list;
    }

    /**
     * Unserialize a field of a model.
     * @param model the model target
     * @param field the field target
     * @param cursor the cursor where the data is
     * @throws IllegalAccessException
     */
    private void configureField(IModel model, Field field, Cursor cursor) throws IllegalAccessException {
        ModelAnnotation annotation = field.getAnnotation(ModelAnnotation.class);
        if (annotation != null) {
            switch (annotation.sqlType()){
                case NULL: field.set(model, null); break;
                case TEXT: field.set(model, cursor.getString(cursor.getColumnIndex(annotation.columnName()))); break;
                case BLOB: field.set(model, cursor.getBlob(cursor.getColumnIndex(annotation.columnName()))); break;
                case INTEGER: field.set(model, cursor.getInt(cursor.getColumnIndex(annotation.columnName()))); break;
                case LONG: field.set(model, cursor.getLong(cursor.getColumnIndex(annotation.columnName()))); break;
                case REAL: field.set(model, cursor.getInt(cursor.getColumnIndex(annotation.columnName()))); break;
            }
        }
    }
}
