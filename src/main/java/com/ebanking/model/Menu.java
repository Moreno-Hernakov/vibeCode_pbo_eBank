package com.ebanking.model;

public class Menu {
    private String menuTitle;
    private String routePath;

    public Menu() {}

    public Menu(String menuTitle, String routePath) {
        this.menuTitle = menuTitle;
        this.routePath = routePath;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }
}
