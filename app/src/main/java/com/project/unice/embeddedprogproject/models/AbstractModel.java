package com.project.unice.embeddedprogproject.models;

import android.content.ContentValues;

import com.project.unice.embeddedprogproject.sqlite.IModel;
import com.project.unice.embeddedprogproject.sqlite.ModelAnnotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * All of the models may extends this class
 * Every model may have for each property a ModelAnnotation
 */
public abstract class AbstractModel implements IModel {

    /**
     * @return The name of the Table in the database
     */
    @Override
    public String getTableName() {
        return getClass().getSimpleName();
    }

    /**
     * Method to serialize a model
     * @param value the container of the values
     */
    @Override
    public void put(ContentValues value) {
        for (Field field : getModelProperties()) {
            ModelAnnotation annotation = field.getAnnotation(ModelAnnotation.class);
            if (annotation.primaryKey()) continue;
            try {
                value.put(field.getName(), String.valueOf(field.get(this)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return All the columns fields of the model
     */
    public List<Field> getModelProperties(){
        List<Field> list = new ArrayList<>();
        for (Field field : getClass().getDeclaredFields()) {
            ModelAnnotation annotation = field.getAnnotation(ModelAnnotation.class);
            if (annotation != null){
                list.add(field);
            }
        }
        return list;
    }

    /**
     * A couple of two elements
     * @param <X> findFirstValue element
     * @param <Y> second element
     */
    public class Tuple<X, Y> {
        public final X x;
        public final Y y;
        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * @return The tuple Field/Annotation for the primary key
     */
    public Tuple<Field, ModelAnnotation> getPrimaryProperties(){
        ModelAnnotation annotationId = null;
        Field fieldId = null;
        for (Field field :
                getModelProperties()) {
            ModelAnnotation a = field.getAnnotation(ModelAnnotation.class);
            if (a.primaryKey()){
                annotationId = a;
                fieldId = field;
                break;
            }
        }
        return new Tuple<>(fieldId, annotationId);
    }

    /**
     * @return the request to create the table
     */
    public String createTableRequest(){
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE " + getTableName());
        List<Field> listeFields = getModelProperties();
        if (listeFields.size() > 0){
            builder.append(" (");
            int i = 1;
            for (Field field : listeFields) {
                ModelAnnotation annotation = field.getAnnotation(ModelAnnotation.class);
                String columnName = annotation.columnName();
                boolean primary = annotation.primaryKey();
                String type = annotation.sqlType().toString();
                builder.append(columnName).append(" ").append(type);
                if (primary) builder.append(" PRIMARY KEY AUTOINCREMENT");
                if (i < listeFields.size()) builder.append(", ");
                i++;
            }
            builder.append(")");
        }
        builder.append(";");
        return builder.toString();
    }

    /**
     * @return the request to drop the table
     */
    public String dropTableRequest(){
        return "DROP TABLE IF EXISTS " + getTableName() + ";";
    }

    /**
     * @param classe the model to create the table
     * @return the request to create the table for the model passed
     */
    public static String createTableRequest(Class<? extends AbstractModel> classe){
        try {
            return classe.newInstance().createTableRequest();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param classe the model to drop the table
     * @return the request to drop the table for the model passed
     */
    public static String dropTableRequest(Class<? extends AbstractModel> classe){
        try {
            return classe.newInstance().dropTableRequest();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
