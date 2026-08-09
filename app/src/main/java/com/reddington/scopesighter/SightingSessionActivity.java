package com.reddington.scopesighter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class SightingSessionActivity extends BaseActivity {
    private float diameter;
    private Button dispResultButton;
    private float screenHeight;
    private float screenWidth;
    private ScopeSighterApplication ssapp;
    private Target t;
    private RelativeLayout targetLayout;
    private TargetView targetView;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sightingsessionlayout);
        this.ssapp = (ScopeSighterApplication) getApplication();
        String targetBackground = this.ssapp.getActiveRange().getTargetBackground();
        this.targetView = new TargetView(this, targetBackground);
        this.targetLayout = (RelativeLayout) findViewById(R.id.targetLayout);
        this.t = this.ssapp.getTarget();
        this.dispResultButton = (Button) findViewById(R.id.displayResultButton);
        this.dispResultButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SightingSessionActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SightingSessionActivity.this.dispResultButtonClicked();
            }
        });
        this.screenWidth = this.ssapp.getDeviceWidth();
        this.screenHeight = this.ssapp.getDeviceHeight();
        if (this.screenWidth < this.screenHeight) {
            this.diameter = this.screenWidth;
        } else {
            this.diameter = this.screenHeight;
        }
        this.targetView = new TargetView(this.diameter, this.screenWidth / 2.0f, this.screenWidth / 2.0f, this, targetBackground);
        this.targetView.setOnTouchListener(new View.OnTouchListener() { // from class: com.reddington.scopesighter.SightingSessionActivity.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 0) {
                    return true;
                }
                SightingSessionActivity.this.t.addHit(new Hit(motionEvent.getX(), motionEvent.getY()));
                SightingSessionActivity.this.targetView.addHit(motionEvent.getX(), motionEvent.getY());
                SightingSessionActivity.this.targetView.invalidate();
                return true;
            }
        });
        this.t.setCenterX(this.screenWidth / 2.0f);
        this.t.setCenterY(this.targetLayout.getHeight() / 2);
        this.t.setPixelDiameter(this.diameter);
        this.targetLayout.addView(this.targetView);
        this.dispResultButton.bringToFront();
    }

    public void dispResultButtonClicked() {
        this.ssapp.setTarget(this.t);
        this.ssapp.calculate(this);
        startActivity(new Intent(this, (Class<?>) SessionResultsActivity.class));
    }
}
