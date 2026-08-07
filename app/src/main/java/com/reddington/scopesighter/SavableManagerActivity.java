package com.reddington.scopesighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class SavableManagerActivity extends BaseActivity {
    Button backButton;
    Button clockwiseLeftToggleButton;
    Button clockwiseUpToggleButton;
    Button deleteRangeButton;
    Button deleteScopeButton;
    DecimalFormat df = new DecimalFormat("@####");
    EditText feetToTargetEditText;
    EditText oneClickEqualsEditText;
    EditText rangeNameEditText;
    Spinner rangeSpinner;
    Button saveRangeButton;
    Button saveScopeButton;
    EditText scopeNameEditText;
    Spinner scopeSpinner;
    ScopeSighterApplication ssapp;
    EditText targetDiameterEditText;
    EditText yardsForAdjustEditText;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.ssapp = (ScopeSighterApplication) getApplication();
        setContentView(R.layout.saveablemanagerlayout);
        this.scopeSpinner = (Spinner) findViewById(R.id.scopeListSpinner);
        this.scopeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                SavableManagerActivity.this.ssapp.setActiveScope((String) adapterView.getItemAtPosition(i));
                SavableManagerActivity.this.updateScopeControls();
            }
        });
        this.rangeSpinner = (Spinner) findViewById(R.id.rangeListSpinner);
        this.rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.2
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                SavableManagerActivity.this.ssapp.setActiveRange((String) adapterView.getItemAtPosition(i));
                SavableManagerActivity.this.updateRangeControls();
            }
        });
        this.clockwiseUpToggleButton = (Button) findViewById(R.id.clockwiseUpToggleButton);
        this.clockwiseUpToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SavableManagerActivity.this.clockwiseUpToggleButton.getText().toString().equals("Up")) {
                    SavableManagerActivity.this.clockwiseUpToggleButton.setText("Down");
                } else {
                    SavableManagerActivity.this.clockwiseUpToggleButton.setText("Up");
                }
            }
        });
        this.clockwiseLeftToggleButton = (Button) findViewById(R.id.clockwiseLeftToggleButton);
        this.clockwiseLeftToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SavableManagerActivity.this.clockwiseLeftToggleButton.getText().toString().equals("Left")) {
                    SavableManagerActivity.this.clockwiseLeftToggleButton.setText("Right");
                } else {
                    SavableManagerActivity.this.clockwiseLeftToggleButton.setText("Left");
                }
            }
        });
        this.saveScopeButton = (Button) findViewById(R.id.saveScopeButton);
        this.saveScopeButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SavableManagerActivity.this.saveScope();
            }
        });
        this.deleteScopeButton = (Button) findViewById(R.id.deleteScopeButton);
        this.deleteScopeButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SavableManagerActivity.this.deleteScope();
            }
        });
        this.saveRangeButton = (Button) findViewById(R.id.saveRangeButton);
        this.saveRangeButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SavableManagerActivity.this.saveRange();
            }
        });
        this.deleteRangeButton = (Button) findViewById(R.id.deleteRangeButton);
        this.deleteRangeButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SavableManagerActivity.this.deleteRange();
            }
        });
        this.backButton = (Button) findViewById(R.id.backButton);
        this.backButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SavableManagerActivity.this.backButtonPressed();
            }
        });
        this.scopeNameEditText = (EditText) findViewById(R.id.scopeNameEditText);
        this.oneClickEqualsEditText = (EditText) findViewById(R.id.oneClickEqualsEditText);
        this.yardsForAdjustEditText = (EditText) findViewById(R.id.yardsForAdjustEditText);
        this.rangeNameEditText = (EditText) findViewById(R.id.rangeNameEditText);
        this.feetToTargetEditText = (EditText) findViewById(R.id.feetToTargetEditText);
        this.targetDiameterEditText = (EditText) findViewById(R.id.diameterOfTargetEditText);
        updateSpinners();
        setUnitLabels();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        setUnitLabels();
        updateScopeControls();
        updateRangeControls();
    }

    private void setUnitLabels() {
        if (this.ssapp.isAppImperial()) {
            return;
        }
        ((TextView) findViewById(R.id.oneClickEqualsTextView)).setText(getString(R.string.metricOneClickEqualsText));
        ((TextView) findViewById(R.id.inchesTextView)).setText(getString(R.string.metricDistanceForScopeText));
        ((TextView) findViewById(R.id.feetToTargetTextView)).setText(getString(R.string.metersToTargetText));
        ((TextView) findViewById(R.id.diameterOfTargetTextView)).setText(getString(R.string.metricDiameterOfTargetText));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateScopeControls() {
        Scope activeScope = this.ssapp.getActiveScope();
        this.scopeNameEditText.setText(activeScope.getName());
        if (activeScope.getClockwiseOffsetsLeft()) {
            this.clockwiseLeftToggleButton.setText(getString(R.string.toggleLeft));
        } else {
            this.clockwiseLeftToggleButton.setText(getString(R.string.toggleRight));
        }
        if (activeScope.getClockwiseOffsetsUp()) {
            this.clockwiseUpToggleButton.setText(getString(R.string.toggleUp));
        } else {
            this.clockwiseUpToggleButton.setText(getString(R.string.toggleDown));
        }
        this.oneClickEqualsEditText.setText(this.df.format(activeScope.getOffsetPerClick()));
        this.yardsForAdjustEditText.setText(this.df.format(activeScope.getDistanceForAdjust()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRangeControls() {
        Range activeRange = this.ssapp.getActiveRange();
        this.rangeNameEditText.setText(activeRange.getName());
        this.feetToTargetEditText.setText(this.df.format(activeRange.getDistanceToTarget()));
        this.targetDiameterEditText.setText(this.df.format(activeRange.getTargetDiameter()));
    }

    private void updateSpinners() {
        ArrayList<Range> ranges = this.ssapp.getRanges();
        ArrayList<Scope> scopes = this.ssapp.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<Range> it = ranges.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<Scope> it2 = scopes.iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.rangeSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.rangeSpinner.setSelection(arrayList.indexOf(this.ssapp.getActiveRange().getName()));
        updateRangeControls();
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.scopeSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        this.scopeSpinner.setSelection(arrayList2.indexOf(this.ssapp.getActiveScope().getName()));
        arrayAdapter2.notifyDataSetChanged();
        updateScopeControls();
    }

    private boolean validateScopeAttributes() {
        if (!this.scopeNameEditText.getText().toString().equals("") && !this.yardsForAdjustEditText.getText().toString().equals("") && !this.oneClickEqualsEditText.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Check Scope Inputs", 1).show();
        return false;
    }

    private boolean validateRangeAttributes() {
        if (!this.rangeNameEditText.getText().toString().equals("") && !this.feetToTargetEditText.getText().toString().equals("") && !this.targetDiameterEditText.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(getApplicationContext(), "Check Range Inputs", 1).show();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backButtonPressed() {
        startActivity(new Intent(this, (Class<?>) HomeActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveScope() {
        if (validateScopeAttributes()) {
            boolean zEquals = this.clockwiseUpToggleButton.getText().equals("Up");
            Scope scope = new Scope(this.scopeNameEditText.getText().toString(), Double.parseDouble(this.oneClickEqualsEditText.getText().toString()), Double.parseDouble(this.yardsForAdjustEditText.getText().toString()), this.clockwiseLeftToggleButton.getText().equals("Left"), zEquals);
            Iterator<Scope> it = this.ssapp.getScopes().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (scope.equals(it.next())) {
                    z = true;
                }
            }
            if (z) {
                Toast.makeText(getApplicationContext(), "Scope Saved", 0).show();
                return;
            }
            this.ssapp.updateSavables(scope);
            updateSpinners();
            Toast.makeText(getApplicationContext(), "Scope Saved", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteScope() {
        updateScopeControls();
        if (validateScopeAttributes()) {
            boolean zEquals = this.clockwiseUpToggleButton.getText().equals("Up");
            Scope scope = new Scope(this.scopeNameEditText.getText().toString(), Double.parseDouble(this.oneClickEqualsEditText.getText().toString()), Double.parseDouble(this.yardsForAdjustEditText.getText().toString()), this.clockwiseLeftToggleButton.getText().equals("Left"), zEquals);
            Iterator<Scope> it = this.ssapp.getScopes().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (scope.equals(it.next())) {
                    z = true;
                }
            }
            if (!z) {
                Toast.makeText(getApplicationContext(), "No scope with specified attributes exists. Reselect scope and press delete.", 0).show();
                return;
            }
            this.ssapp.updateSavables(scope);
            updateSpinners();
            Toast.makeText(getApplicationContext(), "Scope Deleted", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveRange() {
        if (validateRangeAttributes()) {
            Range range = new Range(Float.parseFloat(this.feetToTargetEditText.getText().toString()), Float.parseFloat(this.targetDiameterEditText.getText().toString()), this.rangeNameEditText.getText().toString());
            Iterator<Range> it = this.ssapp.getRanges().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (range.equals(it.next())) {
                    z = true;
                }
            }
            if (z) {
                Toast.makeText(getApplicationContext(), "Range Saved", 0).show();
                return;
            }
            this.ssapp.updateSavables(range);
            updateRangeControls();
            updateSpinners();
            Toast.makeText(getApplicationContext(), "Range Saved", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteRange() {
        updateRangeControls();
        if (validateRangeAttributes()) {
            Range range = new Range(Float.parseFloat(this.feetToTargetEditText.getText().toString()), Float.parseFloat(this.targetDiameterEditText.getText().toString()), this.rangeNameEditText.getText().toString());
            Iterator<Range> it = this.ssapp.getRanges().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (range.equals(it.next())) {
                    z = true;
                }
            }
            if (!z) {
                Toast.makeText(getApplicationContext(), "No range with specified attributes exists. Reselect range and press delete.", 0).show();
                return;
            }
            this.ssapp.updateSavables(range);
            updateSpinners();
            Toast.makeText(getApplicationContext(), "Range Deleted", 0).show();
        }
    }
}
