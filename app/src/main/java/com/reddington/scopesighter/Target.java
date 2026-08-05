package com.reddington.scopesighter;

import java.util.ArrayList;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class Target {
    private float centerX;
    private float centerY;
    private ArrayList<Hit> hits = new ArrayList<>();
    private String name;
    private float pixelDiameter;

    public Target() {
    }

    public Target(String str) {
        this.name = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void addHit(Hit hit) {
        this.hits.add(hit);
    }

    public ArrayList<Hit> getHits() {
        return this.hits;
    }

    public float getCenterX() {
        return this.centerX;
    }

    public void setCenterX(float f) {
        this.centerX = f;
    }

    public float getCenterY() {
        return this.centerY;
    }

    public void setCenterY(float f) {
        this.centerY = f;
    }

    public float getPixelDiameter() {
        return this.pixelDiameter;
    }

    public void setPixelDiameter(float f) {
        this.pixelDiameter = f;
    }
}
