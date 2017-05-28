package com.project.unice.embeddedprogproject.sqlite.databaseModels;

import com.project.unice.embeddedprogproject.sqlite.ModelAnnotation;
import com.project.unice.embeddedprogproject.sqlite.SqliteTypes;

/**
 * Model Business Card
 */
public class BusinessCard extends AbstractModel {

    @ModelAnnotation(columnName = "Id", primaryKey = true, sqlType = SqliteTypes.INTEGER)
    public int id;

    @ModelAnnotation(columnName = "Name", sqlType = SqliteTypes.TEXT)
    public String name;

    @ModelAnnotation(columnName = "Phone", sqlType = SqliteTypes.TEXT)
    public String phone;

    @ModelAnnotation(columnName = "Contains", sqlType = SqliteTypes.TEXT)
    public String contains;

    @Override
    public String toString() {
        return "BusinessCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", contains='" + contains + '\'' +
                '}';
    }
}
