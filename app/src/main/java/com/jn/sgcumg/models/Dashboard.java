package com.jn.sgcumg.models;


public class Dashboard {

    private String id_dashboard;
    private int image;
    private String name;

    public Dashboard(String id_dashboard, int image, String name) {
        this.id_dashboard = id_dashboard;
        this.image = image;
        this.name = name;
    }

    public String getId_dashboard() {
        return id_dashboard;
    }

    public void setId_dashboard(String id_dashboard) {
        this.id_dashboard = id_dashboard;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
