package com.reddington.scopesighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
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

    private Bitmap targetBitmap;
    private Paint targetPaint;
    private float lastBitmapRadius = -1f;

    public TargetView(Context context) {
        this(context, "dianagenerico");
    }

    public TargetView(Context context, String targetBackgroundName) {
        super(context);
        this.hits = new ArrayList<>();
        this.ringsPaint = new Paint();
        this.hitPaint = new Paint();
        this.ringsPaint.setColor(Color.RED);
        this.ringsPaint.setStyle(Paint.Style.STROKE);
        this.hitPaint.setColor(Color.BLACK);
        this.hitPaint.setStyle(Paint.Style.FILL);
        initTargetBitmap(context, targetBackgroundName);
    }

    public TargetView(float f, float f2, float f3, Context context) {
        this(f, f2, f3, context, "dianagenerico");
    }

    public TargetView(float f, float f2, float f3, Context context, String targetBackgroundName) {
        super(context);
        this.hits = new ArrayList<>();
        this.ringsPaint = new Paint();
        this.hitPaint = new Paint();
        this.x = f2;
        this.y = f3;
        this.ringIncrement = f / 6.0f;
        this.ringsPaint.setColor(Color.RED);
        this.ringsPaint.setStyle(Paint.Style.STROKE);
        this.hitPaint.setColor(Color.BLACK);
        this.hitPaint.setStyle(Paint.Style.FILL);
        initTargetBitmap(context, targetBackgroundName);
    }

    private void initTargetBitmap(Context context, String targetBackgroundName) {
        this.targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (targetBackgroundName == null) {
            targetBackgroundName = "dianagenerico";
        }
        int resId = context.getResources().getIdentifier(targetBackgroundName, "drawable", context.getPackageName());
        if (resId == 0) {
            resId = context.getResources().getIdentifier("dianagenerico", "drawable", context.getPackageName());
        }
        if (resId != 0) {
            this.targetBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        }
    }

    private void updateShaderScale(float outerRadius) {
        if (this.targetBitmap == null || outerRadius <= 0f) {
            return;
        }
        if (this.lastBitmapRadius == outerRadius) {
            return;
        }
        this.lastBitmapRadius = outerRadius;

        BitmapShader shader = new BitmapShader(this.targetBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        float diameter = outerRadius * 2.0f;
        float scale = diameter / (float) this.targetBitmap.getWidth();

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(this.x - outerRadius, this.y - outerRadius);
        shader.setLocalMatrix(matrix);

        this.targetPaint.setShader(shader);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float outerRadius = this.ringIncrement * 3.0f;

        if (this.targetBitmap != null) {
            updateShaderScale(outerRadius);
            canvas.drawCircle(this.x, this.y, outerRadius, this.targetPaint);
        }

        for (Hit hit : this.hits) {
            canvas.drawCircle(hit.getX(), hit.getY(), 10.0f, this.hitPaint);
        }
    }

    public void addHit(float f, float f2) {
        this.hits.add(new Hit(f, f2));
    }
}
