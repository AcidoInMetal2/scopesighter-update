package com.reddington.scopesighter;

import java.io.Serializable;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class Savable implements Serializable {
    private static final long serialVersionUID = 1;
    private boolean active;
    private String name;

    public Savable() {
        this.name = "";
        this.active = false;
    }

    public Savable(String str) {
        this.name = "";
        this.active = false;
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public void setActiveStatus(boolean z) {
        if (z) {
            this.active = true;
        } else {
            this.active = false;
        }
    }

    public boolean getActiveStatus() {
        return this.active;
    }
}
