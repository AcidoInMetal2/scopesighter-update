package com.reddington.scopesighter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class AboutActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.aboutlayout);
        ((Button) findViewById(R.id.aboutHomeButton)).setOnClickListener(new View.OnClickListener() { // from class: com.reddington.scopesighter.AboutActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AboutActivity.this.homeButtonClicked();
            }
        });
    }

    private void donateButtonClicked() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=6FDKJT9CS7J6L"));
        startActivity(intent);
    }

    public void homeButtonClicked() {
        startActivity(new Intent(this, (Class<?>) HomeActivity.class));
    }
}
