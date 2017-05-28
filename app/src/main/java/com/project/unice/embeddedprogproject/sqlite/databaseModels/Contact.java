package com.project.unice.embeddedprogproject.sqlite.databaseModels;

import com.project.unice.embeddedprogproject.sqlite.ModelAnnotation;
import com.project.unice.embeddedprogproject.sqlite.SqliteTypes;

public class Contact extends AbstractModel {

    @ModelAnnotation(columnName = "Id", primaryKey = true, sqlType = SqliteTypes.INTEGER)
    public int id;

    @ModelAnnotation(columnName = "IdContactAndroid", sqlType = SqliteTypes.LONG)
    public Long idContactAndroid;

    @ModelAnnotation(columnName = "Name", sqlType = SqliteTypes.TEXT)
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
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", idBusinessCard=" + idBusinessCard +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return phone != null ? phone.equals(contact.phone) : contact.phone == null;

    }

    @Override
    public int hashCode() {
        return phone != null ? phone.hashCode() : 0;
    }

    public void setIdContactAndroid(long idContactAndroid) {
        this.idContactAndroid = idContactAndroid;
    }
}
