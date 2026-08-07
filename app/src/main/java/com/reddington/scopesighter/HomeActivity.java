package com.reddington.scopesighter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class HomeActivity extends BaseActivity {
    Button aboutButton;
    Context context;
    Button helpButton;
    Button newSessionButton;
    ArrayList<Range> rangeList;
    Spinner rangeSpinner;
    Button scopeAndRangeManagerButton;
    ArrayList<Scope> scopeList;
    Spinner scopeSpinner;
    Button selectLanguageButton;
    private ScopeSighterApplication ssapp;
    TextView title;
    Button unitsButton;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = this;
        this.ssapp = (ScopeSighterApplication) getApplication();
        setContentView(R.layout.scopesighterhomelayout);
        this.aboutButton = (Button) findViewById(R.id.aboutButton);
        this.aboutButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.HomeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HomeActivity.this.lauchAboutActivity();
            }
        });
        this.scopeAndRangeManagerButton = (Button) findViewById(R.id.scopeAndRangeManagerButton);
        this.scopeAndRangeManagerButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.HomeActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HomeActivity.this.launchScopeAndRangeManager();
            }
        });
        this.rangeSpinner = (Spinner) findViewById(R.id.rangeSpinner);
        this.rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.reddington.scopesighter.HomeActivity.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                HomeActivity.this.setSavableActive(HomeActivity.this.rangeList.get(i));
            }
        });
        this.scopeSpinner = (Spinner) findViewById(R.id.scopeSpinner);
        this.scopeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.reddington.scopesighter.HomeActivity.4
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                HomeActivity.this.setSavableActive(HomeActivity.this.scopeList.get(i));
            }
        });
        this.newSessionButton = (Button) findViewById(R.id.newSessionButton);
        this.newSessionButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.HomeActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HomeActivity.this.launchNewSession();
            }
        });
        this.helpButton = (Button) findViewById(R.id.helpButton);
        this.helpButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.HomeActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                final Dialog dialog = new Dialog(HomeActivity.this.context);
                dialog.setContentView(R.layout.dialoglayout);
                ((Button) dialog.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.HomeActivity.6.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        dialog.dismiss();
                    }
                });
                dialog.setTitle(HomeActivity.this.getString(R.string.dialogTitleText));
                dialog.show();
            }
        });
        this.unitsButton = (Button) findViewById(R.id.unitsButton);
        this.unitsButton.setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.HomeActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (HomeActivity.this.ssapp.isAppImperial()) {
                    HomeActivity.this.unitsButton.setText(HomeActivity.this.getString(R.string.unitsButtonMetricText));
                    HomeActivity.this.ssapp.setUnitsMetric();
                } else {
                    HomeActivity.this.unitsButton.setText(HomeActivity.this.getString(R.string.unitsButtonImperialText));
                    HomeActivity.this.ssapp.setUnitsImperial();
                }
            }
        });
        this.selectLanguageButton = (Button) findViewById(R.id.selectLanguageButton);
        this.selectLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.this.showLanguageDialog();
            }
        });
        this.ssapp.ensurePopulated();
        updateSpinners();
    }

    private void showLanguageDialog() {
        final String[] languageNames = {getString(R.string.languageSpanish), getString(R.string.languageEnglish)};
        final String[] languageCodes = {"es", "en"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.selectLanguageButtonText));
        builder.setItems(languageNames, new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialogInterface, int which) {
                BaseActivity.setAppLanguage(HomeActivity.this, languageCodes[which]);
                dialogInterface.dismiss();
                HomeActivity.this.recreate();
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lauchAboutActivity() {
        startActivity(new Intent(this, (Class<?>) AboutActivity.class));
    }

    public void launchNewSession() {
        if (!this.ssapp.getActiveScope().getName().equals("Add a Scope") && !this.ssapp.getActiveRange().getName().equals("Add a Range")) {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.mainTableLayout);
            this.ssapp.setDeviceWidth(tableLayout.getWidth());
            this.ssapp.setDeviceHeight(tableLayout.getHeight());
            this.ssapp.setTarget(new Target());
            startActivity(new Intent(this, (Class<?>) SightingSessionActivity.class));
            return;
        }
        Toast.makeText(getApplicationContext(), "Please choose or create your scope and range!", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSavableActive(Savable savable) {
        if (savable instanceof Range) {
            this.ssapp.setActiveRange(savable.getName());
        } else {
            this.ssapp.setActiveScope(savable.getName());
        }
    }

    private void updateSpinners() {
        this.rangeList = this.ssapp.getRanges();
        this.scopeList = this.ssapp.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<Range> it = this.rangeList.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator<Scope> it2 = this.scopeList.iterator();
        while (it2.hasNext()) {
            arrayList2.add(it2.next().getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.rangeSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
        this.rangeSpinner.setSelection(arrayList.indexOf(this.ssapp.getActiveRange().getName()));
        this.rangeSpinner.invalidate();
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.scopeSpinner.setAdapter((SpinnerAdapter) arrayAdapter2);
        this.scopeSpinner.setSelection(arrayList2.indexOf(this.ssapp.getActiveScope().getName()));
        this.scopeSpinner.invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void launchScopeAndRangeManager() {
        startActivity(new Intent(this, (Class<?>) SavableManagerActivity.class));
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
        updateSpinners();
        setUnitStrings();
    }

    private void setUnitStrings() {
        if (!this.ssapp.isAppImperial()) {
            this.unitsButton.setText(getString(R.string.unitsButtonMetricText));
        } else {
            this.unitsButton.setText(getString(R.string.unitsButtonImperialText));
        }
    }
}
