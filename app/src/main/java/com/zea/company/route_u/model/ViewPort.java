package com.zea.company.route_u.model;

public class ViewPort {
    location northeast;
    location southwest;

    public ViewPort(location northeast, location southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public location getNortheast() {
        return northeast;
    }

    public void setNortheast(location northeast) {
        this.northeast = northeast;
    }

    public location getSouthwest() {
        return southwest;
    }

    public void setSouthwest(location southwest) {
        this.southwest = southwest;
    }
}
