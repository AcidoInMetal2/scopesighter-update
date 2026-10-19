package com.reddington.scopesighter;

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
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class SavableManagerActivity extends BaseActivity {
    Button backButton;
    Button clockwiseLeftToggleButton;
    Button clockwiseUpToggleButton;
    Button deleteRangeButton;
    Button deleteScopeButton;
    // Siempre con punto decimal, sin importar el idioma de la app (ver parseNumber)
    DecimalFormat df = new DecimalFormat("@####", DecimalFormatSymbols.getInstance(Locale.US));
    // Estado real de los toggles: ya no se deduce del texto del botón
    private boolean clockwiseLeft = true;
    private boolean clockwiseUp = true;
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
    Spinner targetBackgroundSpinner;

    private static final List<String> TARGET_BACKGROUND_VALUES = Arrays.asList("diana11cm", "diana7cm", "dianagenerico");

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
                SavableManagerActivity.this.clockwiseUp = !SavableManagerActivity.this.clockwiseUp;
                SavableManagerActivity.this.refreshToggleLabels();
            }
        });
        this.clockwiseLeftToggleButton = (Button) findViewById(R.id.clockwiseLeftToggleButton);
        this.clockwiseLeftToggleButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.SavableManagerActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SavableManagerActivity.this.clockwiseLeft = !SavableManagerActivity.this.clockwiseLeft;
                SavableManagerActivity.this.refreshToggleLabels();
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
        this.targetBackgroundSpinner = (Spinner) findViewById(R.id.targetBackgroundSpinner);
        setupTargetBackgroundSpinner();
        updateSpinners();
        setUnitLabels();
    }

    private void setupTargetBackgroundSpinner() {
        String[] displayNames = {
                getString(R.string.targetBackgroundOption1),
                getString(R.string.targetBackgroundOption2),
                getString(R.string.targetBackgroundOption3)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, displayNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.targetBackgroundSpinner.setAdapter(adapter);
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
        this.clockwiseLeft = activeScope.getClockwiseOffsetsLeft();
        this.clockwiseUp = activeScope.getClockwiseOffsetsUp();
        refreshToggleLabels();
        this.oneClickEqualsEditText.setText(this.df.format(activeScope.getOffsetPerClick()));
        this.yardsForAdjustEditText.setText(this.df.format(activeScope.getDistanceForAdjust()));
    }

    /** Pinta los botones según el estado booleano, en el idioma activo. */
    void refreshToggleLabels() {
        this.clockwiseLeftToggleButton.setText(getString(this.clockwiseLeft ? R.string.toggleLeft : R.string.toggleRight));
        this.clockwiseUpToggleButton.setText(getString(this.clockwiseUp ? R.string.toggleUp : R.string.toggleDown));
    }

    /** Acepta "0.25" y "0,25". Tira NumberFormatException si no es un número. */
    private static double parseNumber(EditText editText) {
        return Double.parseDouble(editText.getText().toString().trim().replace(',', '.'));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRangeControls() {
        Range activeRange = this.ssapp.getActiveRange();
        this.rangeNameEditText.setText(activeRange.getName());
        this.feetToTargetEditText.setText(this.df.format(activeRange.getDistanceToTarget()));
        this.targetDiameterEditText.setText(this.df.format(activeRange.getTargetDiameter()));
        int index = TARGET_BACKGROUND_VALUES.indexOf(activeRange.getTargetBackground());
        if (index < 0) {
            index = TARGET_BACKGROUND_VALUES.indexOf("dianagenerico");
        }
        this.targetBackgroundSpinner.setSelection(index);
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
        Toast.makeText(this, getString(R.string.toastCheckScopeInputs), Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean validateRangeAttributes() {
        if (!this.rangeNameEditText.getText().toString().equals("") && !this.feetToTargetEditText.getText().toString().equals("") && !this.targetDiameterEditText.getText().toString().equals("")) {
            return true;
        }
        Toast.makeText(this, getString(R.string.toastCheckRangeInputs), Toast.LENGTH_LONG).show();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void backButtonPressed() {
        startActivity(new Intent(this, (Class<?>) HomeActivity.class));
    }

    /** Arma la mira con lo que hay en pantalla; null (y aviso) si algún número es inválido. */
    private Scope buildScopeFromInputs() {
        try {
            return new Scope(this.scopeNameEditText.getText().toString(),
                    parseNumber(this.oneClickEqualsEditText),
                    parseNumber(this.yardsForAdjustEditText),
                    this.clockwiseLeft, this.clockwiseUp);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.toastInvalidNumber), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /** Arma el blanco con lo que hay en pantalla; null (y aviso) si algún número es inválido. */
    private Range buildRangeFromInputs() {
        try {
            String selectedBackground = TARGET_BACKGROUND_VALUES.get(this.targetBackgroundSpinner.getSelectedItemPosition());
            return new Range((float) parseNumber(this.feetToTargetEditText),
                    (float) parseNumber(this.targetDiameterEditText),
                    this.rangeNameEditText.getText().toString(), selectedBackground);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.toastInvalidNumber), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveScope() {
        if (validateScopeAttributes()) {
            Scope scope = buildScopeFromInputs();
            if (scope == null) {
                return;
            }
            Iterator<Scope> it = this.ssapp.getScopes().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (scope.equals(it.next())) {
                    z = true;
                }
            }
            if (z) {
                Toast.makeText(this, getString(R.string.toastScopeSaved), Toast.LENGTH_SHORT).show();
                return;
            }
            this.ssapp.updateSavables(scope);
            updateSpinners();
            Toast.makeText(this, getString(R.string.toastScopeSaved), Toast.LENGTH_SHORT).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteScope() {
        updateScopeControls();
        if (validateScopeAttributes()) {
            Scope scope = buildScopeFromInputs();
            if (scope == null) {
                return;
            }
            Iterator<Scope> it = this.ssapp.getScopes().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (scope.equals(it.next())) {
                    z = true;
                }
            }
            if (!z) {
                Toast.makeText(this, getString(R.string.toastScopeNotFound), Toast.LENGTH_SHORT).show();
                return;
            }
            this.ssapp.updateSavables(scope);
            updateSpinners();
            Toast.makeText(this, getString(R.string.toastScopeDeleted), Toast.LENGTH_SHORT).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveRange() {
        if (validateRangeAttributes()) {
            Range range = buildRangeFromInputs();
            if (range == null) {
                return;
            }
            Iterator<Range> it = this.ssapp.getRanges().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (range.equals(it.next())) {
                    z = true;
                }
            }
            if (z) {
                Toast.makeText(this, getString(R.string.toastRangeSaved), Toast.LENGTH_SHORT).show();
                return;
            }
            this.ssapp.updateSavables(range);
            updateRangeControls();
            updateSpinners();
            Toast.makeText(this, getString(R.string.toastRangeSaved), Toast.LENGTH_SHORT).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deleteRange() {
        updateRangeControls();
        if (validateRangeAttributes()) {
            Range range = buildRangeFromInputs();
            if (range == null) {
                return;
            }
            Iterator<Range> it = this.ssapp.getRanges().iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (range.equals(it.next())) {
                    z = true;
                }
            }
            if (!z) {
                Toast.makeText(this, getString(R.string.toastRangeNotFound), Toast.LENGTH_SHORT).show();
                return;
            }
            this.ssapp.updateSavables(range);
            updateSpinners();
            Toast.makeText(this, getString(R.string.toastRangeDeleted), Toast.LENGTH_SHORT).show();
        }
    }
}
