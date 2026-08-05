package com.reddington.scopesighter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.ViewCompat;
import android.view.View;
import java.util.ArrayList;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class TargetView extends View {
    Paint hitPaint;
    private ArrayList<Hit> hits;
    private float ringIncrement;
    Paint ringsPaint;
    private float x;
    private float y;

    public TargetView(Context context) {
        super(context);
        this.hits = new ArrayList<>();
        this.ringsPaint = new Paint();
        this.hitPaint = new Paint();
        this.ringsPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.ringsPaint.setStyle(Paint.Style.STROKE);
        this.hitPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.hitPaint.setStyle(Paint.Style.FILL);
    }

    public TargetView(float f, float f2, float f3, Context context) {
        super(context);
        this.hits = new ArrayList<>();
        this.ringsPaint = new Paint();
        this.hitPaint = new Paint();
        this.x = f2;
        this.y = f3;
        this.ringIncrement = f / 6.0f;
        this.ringsPaint.setColor(SupportMenu.CATEGORY_MASK);
        this.ringsPaint.setStyle(Paint.Style.STROKE);
        this.hitPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.hitPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(this.x, this.y, this.ringIncrement * 3.0f, this.ringsPaint);
        canvas.drawCircle(this.x, this.y, this.ringIncrement * 2.0f, this.ringsPaint);
        canvas.drawCircle(this.x, this.y, this.ringIncrement, this.ringsPaint);
        for (Hit hit : this.hits) {
            canvas.drawCircle(hit.getX(), hit.getY(), 10.0f, this.hitPaint);
        }
    }

    public void addHit(float f, float f2) {
        this.hits.add(new Hit(f, f2));
    }
}