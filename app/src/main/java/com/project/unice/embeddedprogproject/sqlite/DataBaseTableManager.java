package com.project.unice.embeddedprogproject.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.project.unice.embeddedprogproject.models.AbstractModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DataBaseTableManager extends DataBaseManager {

    public DataBaseTableManager(Context pContext, String nomBase) {
        super(pContext, nomBase);
    }

    public void add(AbstractModel model) {
        open();
        ContentValues value = new ContentValues();
        model.put(value);
        getDatabase().insert(model.getTableName(), null, value);
        close();
    }

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



    public List<AbstractModel> selectWhere(Class<? extends IModel> classe, ModelAnnotation annotation, Object value) {
        open();

        Cursor cursor = null;
        try {
            cursor = getDatabase().rawQuery("select * from " + classe.newInstance().getTableName() + " where " + annotation.columnName() + " = ?", new String[]{String.valueOf(value)});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        List<AbstractModel> list = new ArrayList<>();
        while (cursor.moveToNext()) {

            /*String contenu = cursor.getString(0);
            try {
                score.fromJson(new JSONObject(contenu));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
            //list.add();
        }
        cursor.close();

        close();
        return list;
    }

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

    private void configureField(IModel model, Field field, Cursor cursor) throws IllegalAccessException {
        ModelAnnotation annotation = field.getAnnotation(ModelAnnotation.class);
        if (annotation != null) {
            switch (annotation.sqlType()){
                case NULL: field.set(model, null); break;
                case TEXT: field.set(model, cursor.getString(cursor.getColumnIndex(annotation.columnName()))); break;
                case BLOB: field.set(model, cursor.getBlob(cursor.getColumnIndex(annotation.columnName()))); break;
                case INTEGER: field.set(model, cursor.getInt(cursor.getColumnIndex(annotation.columnName()))); break;
                case REAL: field.set(model, cursor.getInt(cursor.getColumnIndex(annotation.columnName()))); break;
            }
        }
    }
}
