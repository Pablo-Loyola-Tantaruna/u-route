package com.zea.company.route_u.model;

import java.util.List;

public class PlacePhoto {

    private int height;
    private List<String> html_attributions;
    private String photo_reference;
    private int width;

    public PlacePhoto(int height, List<String> html_attributions, String photo_reference, int width) {
        this.height = height;
        this.html_attributions = html_attributions;
        this.photo_reference = photo_reference;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
