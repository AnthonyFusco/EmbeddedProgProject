package com.project.unice.embeddedprogproject.sqlite;


/**
 * Model annotation. All the models may implement it
 */
public @interface ModelAnnotation {
    String columnName();
    boolean primaryKey() default false;
    SqliteTypes sqlType();
}
