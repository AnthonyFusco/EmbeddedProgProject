package com.project.unice.embeddedprogproject.pages;

public class ChoiceElement {
    public boolean checked;
    public String property;
    public String value;

    public ChoiceElement(String choice, String value, boolean b) {
        this.checked = b;
        this.property = choice;
        this.value = value;
    }
}
