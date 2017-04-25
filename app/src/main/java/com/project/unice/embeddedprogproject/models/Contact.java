package com.project.unice.embeddedprogproject.models;

import com.project.unice.embeddedprogproject.sqlite.ModelAnnotation;
import com.project.unice.embeddedprogproject.sqlite.SqliteTypes;

public class Contact extends AbstractModel {

    @ModelAnnotation(columnName = "Id", primaryKey = true, sqlType = SqliteTypes.INTEGER)
    public int id;

    @ModelAnnotation(columnName = "IdContactAndroid", sqlType = SqliteTypes.INTEGER)
    public int idContactAndroid;


    //@ModelAnnotation(columnName = "Name", sqlType = SqliteTypes.TEXT)
    public String name;

    @ModelAnnotation(columnName = "Phone", sqlType = SqliteTypes.TEXT)
    public String phone;

    @ModelAnnotation(columnName = "IdBusinessCard", sqlType = SqliteTypes.INTEGER)
    public int idBusinessCard;

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", idContactAndroid=" + idContactAndroid +
               /* ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +*/
                ", idBusinessCard=" + idBusinessCard +
                '}';
    }
}
