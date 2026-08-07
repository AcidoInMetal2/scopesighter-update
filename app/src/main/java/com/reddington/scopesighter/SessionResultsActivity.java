package com.reddington.scopesighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class SessionResultsActivity extends BaseActivity {
    Button homeButton;
    Button newSessionButton;
    Result r;
    ScopeSighterApplication ssapp = (ScopeSighterApplication) getApplication();

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.resultslayout);
        this.ssapp = (ScopeSighterApplication) getApplication();
        this.r = this.ssapp.getResult();
        TextView textView = (TextView) findViewById(R.id.elevationClicks);
        TextView textView2 = (TextView) findViewById(R.id.elevationDirection);
        TextView textView3 = (TextView) findViewById(R.id.windageClicks);
        TextView textView4 = (TextView) findViewById(R.id.windageDirection);
        textView.setText("" + this.r.getElevationClicks());
        textView3.setText("" + this.r.getWindageClicks());
        textView2.setText(this.r.getElevationRotationDirection());
        textView4.setText(this.r.getWindageRotationDirection());
        this.homeButton = (Button) findViewById(R.id.homeButton);
        this.homeButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SessionResultsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SessionResultsActivity.this.homeButtonClicked();
            }
        });
        this.newSessionButton = (Button) findViewById(R.id.newSessionButton);
        this.newSessionButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SessionResultsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SessionResultsActivity.this.newSessionButtonClicked();
            }
        });
    }

    public void homeButtonClicked() {
        startActivity(new Intent(this, (Class<?>) HomeActivity.class));
    }

    public void newSessionButtonClicked() {
        this.ssapp.setTarget(new Target());
        startActivity(new Intent(this, (Class<?>) SightingSessionActivity.class));
    }
}
