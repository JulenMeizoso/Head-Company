package com.example.headcompany.model;

public class Source {
    private String descripcionEs;
    private String descripcionEu;
    private int id;

    // Constructor
    public Source(int id, String descripcionEs, String descripcionEu) {
        super();
        this.id = id;
        this.descripcionEs = descripcionEs;
        this.descripcionEu = descripcionEu;
    }

    // Getters & Setters
    public String getDescripcionEs() {
        return descripcionEs;
    }

    public void setDescripcionEs(String descriptionEs) {
        this.descripcionEs = descriptionEs;
    }

    public String getDescripcionEu() {
        return descripcionEu;
    }

    public void setDescripcionEu(String descriptionEu) {
        this.descripcionEu = descriptionEu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}