package com.reddington.scopesighter;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class UnitStatus extends Savable {
    private static final long serialVersionUID = 5461954460263577064L;
    boolean isImperial;

    public UnitStatus() {
    }

    public UnitStatus(boolean z) {
        this.isImperial = z;
    }

    public boolean getIsImperial() {
        return this.isImperial;
    }

    public void setUnitsImperial() {
        this.isImperial = true;
    }

    public void setUnitsMetric() {
        this.isImperial = false;
    }
}
