package com.reddington.scopesighter;

import android.app.Application;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class ScopeSighterApplication extends Application {
    float deviceHeight;
    float deviceWidth;
    String horizontalRotation;
    Result r;
    ArrayList<Savable> savables = new ArrayList<>();
    Target t;
    String verticalRotation;

    @Override // android.app.Application
    public void onCreate() {
        this.t = new Target("Target One");
        loadSavables();
        if (this.savables.size() != 0) {
            boolean z = false;
            Iterator<Savable> it = this.savables.iterator();
            while (it.hasNext()) {
                if (it.next() instanceof UnitStatus) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            this.savables.add(new UnitStatus(true));
            return;
        }
        this.savables.add(new UnitStatus(true));
    }

    public void setDeviceHeight(float f) {
        this.deviceHeight = f;
    }

    public float getDeviceHeight() {
        return this.deviceHeight;
    }

    public void setDeviceWidth(float f) {
        this.deviceWidth = f;
    }

    public float getDeviceWidth() {
        return this.deviceWidth;
    }

    public void setTarget(Target target) {
        this.t = target;
    }

    public Target getTarget() {
        return this.t;
    }

    public Scope getActiveScope() {
        for (Savable savable : this.savables) {
            if ((savable instanceof Scope) && savable.getActiveStatus()) {
                return (Scope) savable;
            }
        }
        return new Scope();
    }

    public void setActiveScope(String str) {
        for (Savable savable : this.savables) {
            if (savable instanceof Scope) {
                if (savable.getName().equals(str)) {
                    savable.setActiveStatus(true);
                } else {
                    savable.setActiveStatus(false);
                }
            }
        }
    }

    public Range getActiveRange() {
        for (Savable savable : this.savables) {
            if ((savable instanceof Range) && savable.getActiveStatus()) {
                return (Range) savable;
            }
        }
        return new Range();
    }

    public void setActiveRange(String str) {
        for (Savable savable : this.savables) {
            if (savable instanceof Range) {
                if (savable.getName().equals(str)) {
                    savable.setActiveStatus(true);
                } else {
                    savable.setActiveStatus(false);
                }
            }
        }
    }

    public ArrayList<Range> getRanges() {
        ArrayList<Range> arrayList = new ArrayList<>();
        for (Savable savable : this.savables) {
            if (savable instanceof Range) {
                arrayList.add((Range) savable);
            }
        }
        return arrayList;
    }

    public ArrayList<Scope> getScopes() {
        ArrayList<Scope> arrayList = new ArrayList<>();
        for (Savable savable : this.savables) {
            if (savable instanceof Scope) {
                arrayList.add((Scope) savable);
            }
        }
        return arrayList;
    }

    public boolean isAppImperial() {
        boolean isImperial = true;
        for (Savable savable : this.savables) {
            if (savable instanceof UnitStatus) {
                isImperial = ((UnitStatus) savable).getIsImperial();
            }
        }
        return isImperial;
    }

    public void setUnitsImperial() {
        convertSavablesToImperial();
    }

    public void setUnitsMetric() {
        convertSavablesToMetric();
    }

    public void calculate() {
        int i;
        int i2;
        Scope activeScope = getActiveScope();
        Range activeRange = getActiveRange();
        ArrayList<Hit> hits = this.t.getHits();
        float pixelDiameter = this.t.getPixelDiameter() / 2.0f;
        float x = 0.0f;
        float y = 0.0f;
        for (Hit hit : hits) {
            x += hit.getX();
            y += hit.getY();
        }
        float size = x / hits.size();
        float size2 = y / hits.size();
        if (size2 < pixelDiameter && activeScope.getClockwiseOffsetsUp()) {
            this.verticalRotation = "counter-clockwise";
        } else if ((size2 <= pixelDiameter || !activeScope.getClockwiseOffsetsUp()) && size2 > pixelDiameter && !activeScope.getClockwiseOffsetsUp()) {
            this.verticalRotation = "counter-clockwise";
        } else {
            this.verticalRotation = "clockwise";
        }
        if (size > pixelDiameter && activeScope.getClockwiseOffsetsLeft()) {
            this.horizontalRotation = "clockwise";
        } else if ((size >= pixelDiameter || !activeScope.getClockwiseOffsetsLeft()) && size < pixelDiameter && !activeScope.getClockwiseOffsetsLeft()) {
            this.horizontalRotation = "clockwise";
        } else {
            this.horizontalRotation = "counter-clockwise";
        }
        float fAbs = Math.abs((size - pixelDiameter) / this.t.getPixelDiameter());
        float fAbs2 = Math.abs((size2 - pixelDiameter) / this.t.getPixelDiameter());
        if (!isAppImperial()) {
            float targetDiameter = fAbs * activeRange.getTargetDiameter();
            float targetDiameter2 = fAbs2 * activeRange.getTargetDiameter();
            double offsetPerClick = activeScope.getOffsetPerClick();
            double distanceToTarget = activeRange.getDistanceToTarget();
            double distanceForAdjust = activeScope.getDistanceForAdjust();
            Double.isNaN(distanceToTarget);
            double d = offsetPerClick * (distanceToTarget / distanceForAdjust);
            double d2 = targetDiameter;
            Double.isNaN(d2);
            i = (int) (d2 / d);
            double d3 = targetDiameter2;
            Double.isNaN(d3);
            i2 = (int) (d3 / d);
        } else {
            float targetDiameter3 = fAbs * activeRange.getTargetDiameter();
            float targetDiameter4 = fAbs2 * activeRange.getTargetDiameter();
            double offsetPerClick2 = activeScope.getOffsetPerClick();
            double distanceToTarget2 = activeRange.getDistanceToTarget() / 3.0f;
            double distanceForAdjust2 = activeScope.getDistanceForAdjust();
            Double.isNaN(distanceToTarget2);
            double d4 = offsetPerClick2 * (distanceToTarget2 / distanceForAdjust2);
            double d5 = targetDiameter3;
            Double.isNaN(d5);
            i = (int) (d5 / d4);
            double d6 = targetDiameter4;
            Double.isNaN(d6);
            i2 = (int) (d6 / d4);
        }
        this.r = new Result(i, i2, this.horizontalRotation, this.verticalRotation);
    }

    public Result getResult() {
        return this.r;
    }

    public void updateSavables(Savable savable) {
        boolean z;
        boolean z2 = savable instanceof Range;
        boolean z3 = true;
        if (z2) {
            Range range = (Range) savable;
            Iterator<Savable> it = this.savables.iterator();
            while (true) {
                if (it.hasNext()) {
                    Savable next = it.next();
                    if (next instanceof Range) {
                        Range range2 = (Range) next;
                        if (range.equals(range2)) {
                            this.savables.remove(next);
                        } else if (range.getName().equals(range2.getName())) {
                            this.savables.remove(next);
                            this.savables.add(savable);
                            setActiveRange(next.getName());
                        }
                        z = true;
                    }
                } else {
                    z = false;
                }
            }
        } else {
            z = false;
        }
        if (!(savable instanceof Scope)) {
            z3 = z;
            break;
        }
        Scope scope = (Scope) savable;
        Iterator<Savable> it2 = this.savables.iterator();
        while (true) {
            if (!it2.hasNext()) {
                z3 = z;
                break;
            }
            Savable next2 = it2.next();
            if (next2 instanceof Scope) {
                Scope scope2 = (Scope) next2;
                if (scope.equals(scope2)) {
                    this.savables.remove(next2);
                    break;
                } else if (scope.getName().equals(scope2.getName())) {
                    this.savables.remove(next2);
                    this.savables.add(savable);
                    setActiveScope(next2.getName());
                    break;
                }
            }
        }
        if (!z3) {
            this.savables.add(savable);
            if (z2) {
                setActiveRange(savable.getName());
            } else {
                setActiveScope(savable.getName());
            }
        }
        ensurePopulated();
        ensureActives();
        writeSavables();
        loadSavables();
    }

    public void ensurePopulated() {
        ArrayList<Range> ranges = getRanges();
        if (ranges.size() == 0) {
            this.savables.add(new Range(0.0f, 0.0f, "Add a Range"));
            setActiveRange("Add a Range");
        }
        if (ranges.size() > 1) {
            Range range = new Range(0.0f, 0.0f, "Add a Range");
            for (Savable savable : this.savables) {
                if (savable instanceof Range) {
                    Range range2 = (Range) savable;
                    if (range2.equals(range)) {
                        this.savables.remove(range2);
                        break;
                    }
                }
            }
        }
        ArrayList<Scope> scopes = getScopes();
        if (scopes.size() == 0) {
            this.savables.add(new Scope("Add a Scope", 0.0d, 0.0d, true, true));
        }
        if (scopes.size() > 1) {
            Scope scope = new Scope("Add a Scope", 0.0d, 0.0d, true, true);
            for (Savable savable2 : this.savables) {
                if (savable2 instanceof Scope) {
                    Scope scope2 = (Scope) savable2;
                    if (scope2.equals(scope)) {
                        this.savables.remove(scope2);
                        return;
                    }
                }
            }
        }
    }

    private void ensureActives() {
        boolean z = false;
        boolean z2 = false;
        for (Savable savable : this.savables) {
            if (savable.getActiveStatus()) {
                if (savable instanceof Scope) {
                    z = true;
                } else {
                    z2 = true;
                }
            }
        }
        if (!z) {
            for (Savable savable2 : this.savables) {
                if (savable2 instanceof Scope) {
                    savable2.setActiveStatus(true);
                    break;
                }
            }
        }
        if (z2) {
            return;
        }
        for (Savable savable3 : this.savables) {
            if (savable3 instanceof Range) {
                savable3.setActiveStatus(true);
                return;
            }
        }
    }

    private void writeSavables() {
        try {
            new File(Environment.getExternalStorageDirectory(), "savables.ser").delete();
            FileOutputStream fileOutputStreamOpenFileOutput = getApplicationContext().openFileOutput("savables.ser", 0);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStreamOpenFileOutput);
            objectOutputStream.writeObject(this.savables);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStreamOpenFileOutput.close();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                return;
            }
            e.printStackTrace();
        }
    }

    private void loadSavables() {
        this.savables.clear();
        try {
            FileInputStream fileInputStreamOpenFileInput = openFileInput("savables.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStreamOpenFileInput);
            this.savables = (ArrayList) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStreamOpenFileInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    public void convertSavablesToMetric() {
        for (Savable savable : this.savables) {
            if (savable instanceof Range) {
                Range range = (Range) savable;
                range.setDistanceToTarget(range.getDistanceToTarget() * 0.3048f);
                range.setTargetDiameter(range.getTargetDiameter() * 2.54f);
            } else if (savable instanceof UnitStatus) {
                ((UnitStatus) savable).setUnitsMetric();
            } else {
                Scope scope = (Scope) savable;
                scope.setOffsetPerClick(scope.getOffsetPerClick() * 2.54d);
                scope.setDistanceForAdjust(scope.getDistanceForAdjust() * 0.9144d);
            }
        }
        writeSavables();
        loadSavables();
    }

    public void convertSavablesToImperial() {
        for (Savable savable : this.savables) {
            if (savable instanceof Range) {
                Range range = (Range) savable;
                range.setDistanceToTarget(range.getDistanceToTarget() / 0.3048f);
                range.setTargetDiameter(range.getTargetDiameter() / 2.54f);
            } else if (savable instanceof UnitStatus) {
                ((UnitStatus) savable).setUnitsImperial();
            } else {
                Scope scope = (Scope) savable;
                scope.setOffsetPerClick(scope.getOffsetPerClick() / 2.54d);
                scope.setDistanceForAdjust(scope.getDistanceForAdjust() / 0.9144d);
            }
        }
        writeSavables();
        loadSavables();
    }
}
