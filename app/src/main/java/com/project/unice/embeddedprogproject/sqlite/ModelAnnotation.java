package com.project.unice.embeddedprogproject.sqlite;

public @interface ModelAnnotation {
    String columnName();
    boolean primaryKey() default false;
    SqliteTypes sqlType();
}
