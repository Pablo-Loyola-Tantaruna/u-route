package com.zea.company.route_u.model;

import java.util.ArrayList;
import java.util.List;

public class results {
    String business_status;
    Geometria geometry;
    String icon;
    String icon_background_color;
    String icon_mask_base_uri;
    String name;
    Open_now opening_hours;
    ArrayList<PlacePhoto> photos;
    String place_id;
    PlusCode plus_code;
    float rating;
    String reference;
    String scope;
    ArrayList<?> types;
    int user_retings_total;
    String vicinity;

    public results(String business_status, Geometria geometry, String icon, String icon_background_color, String icon_mask_base_uri, String name, Open_now opening_hours, ArrayList<PlacePhoto> photos, String place_id, PlusCode plus_code, float rating, String reference, String scope, ArrayList<?> types, int user_retings_total, String vicinity) {
        this.business_status = business_status;
        this.geometry = geometry;
        this.icon = icon;
        this.icon_background_color = icon_background_color;
        this.icon_mask_base_uri = icon_mask_base_uri;
        this.name = name;
        this.opening_hours = opening_hours;
        this.photos = photos;
        this.place_id = place_id;
        this.plus_code = plus_code;
        this.rating = rating;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.user_retings_total = user_retings_total;
        this.vicinity = vicinity;
    }

    public String getBusiness_status() {
        return business_status;
    }

    public void setBusiness_status(String business_status) {
        this.business_status = business_status;
    }

    public Geometria getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometria geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon_background_color() {
        return icon_background_color;
    }

    public void setIcon_background_color(String icon_background_color) {
        this.icon_background_color = icon_background_color;
    }

    public String getIcon_mask_base_uri() {
        return icon_mask_base_uri;
    }

    public void setIcon_mask_base_uri(String icon_mask_base_uri) {
        this.icon_mask_base_uri = icon_mask_base_uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Open_now getOpen_now() {
        return opening_hours;
    }

    public void setOpen_now(Open_now open_now) {
        this.opening_hours = open_now;
    }

    public ArrayList<PlacePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<PlacePhoto> photos) {
        this.photos = photos;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public PlusCode getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(PlusCode plus_code) {
        this.plus_code = plus_code;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ArrayList<?> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<?> types) {
        this.types = types;
    }

    public int getUser_retings_total() {
        return user_retings_total;
    }

    public void setUser_retings_total(int user_retings_total) {
        this.user_retings_total = user_retings_total;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
