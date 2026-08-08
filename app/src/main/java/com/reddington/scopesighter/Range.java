package com.reddington.scopesighter;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class Range extends Savable {
    private static final long serialVersionUID = -5369995678678522030L;
    private float distanceToTarget;
    private float targetDiameter;
    private String targetBackground;

    public Range() {
        this.distanceToTarget = 0.0f;
        this.targetDiameter = 0.0f;
        this.targetBackground = "dianagenerico";
    }

    public Range(float f, float f2, String str) {
        super(str);
        this.distanceToTarget = f;
        this.targetDiameter = f2;
        this.targetBackground = "dianagenerico";
    }

    public Range(float f, float f2, String str, String targetBackground) {
        super(str);
        this.distanceToTarget = f;
        this.targetDiameter = f2;
        this.targetBackground = targetBackground;
    }

    public float getDistanceToTarget() {
        return this.distanceToTarget;
    }

    public void setDistanceToTarget(float f) {
        this.distanceToTarget = f;
    }

    public float getTargetDiameter() {
        return this.targetDiameter;
    }

    public void setTargetDiameter(float f) {
        this.targetDiameter = f;
    }

    public String getTargetBackground() {
        if (this.targetBackground == null) {
            return "dianagenerico";
        }
        return this.targetBackground;
    }

    public void setTargetBackground(String targetBackground) {
        this.targetBackground = targetBackground;
    }

    public boolean equals(Range range) {
        return range.getName().equals(getName()) && range.getDistanceToTarget() == this.distanceToTarget && range.getTargetDiameter() == this.targetDiameter && range.getTargetBackground().equals(getTargetBackground());
    }
}
