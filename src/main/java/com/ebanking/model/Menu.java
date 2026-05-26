package com.ebanking.model;

public class Menu {
    private String title;
    private String routePath;

    public Menu() {}

    public Menu(String title, String routePath) {
        this.title = title;
        this.routePath = routePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    @Override
    public String toString() {
        return "Menu{" + "title=" + title + ", routePath=" + routePath + '}';
    }
}
