package com.zea.company.route_u.model;

public class Geometria {
    location location;
    ViewPort viewPort;

    public Geometria(location location, ViewPort viewPort) {
        this.location = location;
        this.viewPort = viewPort;
    }

    public location getLocation() {
        return location;
    }

    public void setLocation(location location) {
        location = location;
    }

    public ViewPort getViewPort() {
        return viewPort;
    }

    public void setViewPort(ViewPort viewPort) {
        this.viewPort = viewPort;
    }
}
