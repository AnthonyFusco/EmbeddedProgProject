package com.project.unice.embeddedprogproject.models;

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

    @ModelAnnotation(columnName = "Company", sqlType = SqliteTypes.TEXT)
    public String company;

    @ModelAnnotation(columnName = "isCardAvailable", sqlType = SqliteTypes.INTEGER)
    public String isCardAvailable;

    @Override
    public String toString() {
        return "BusinessCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", isCardAvailable='" + isCardAvailable + '\'' +
                '}';
    }
}
