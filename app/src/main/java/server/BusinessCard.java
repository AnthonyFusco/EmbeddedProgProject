package server;

import server.interfaces.IBusinessCard;

public class BusinessCard implements IBusinessCard {
    private int id;
    private int name;
    private String number;

    public BusinessCard(int id, int name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
