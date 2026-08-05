package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: E:\ScopSighter Proyect\Scope_Sighter_v1.3.3.apk\classes.dex */
public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface OrientationMode {
    }

    int getChildrenSkipCount(View view, int i) {
        return 0;
    }

    int getLocationOffset(View view) {
        return 0;
    }

    int getNextLocationOffset(View view) {
        return 0;
    }

    int measureNullChild(int i) {
        return 0;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, i, 0);
        int i2 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        int i3 = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i3 >= 0) {
            setGravity(i3);
        }
        boolean z = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = tintTypedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = tintTypedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(drawable == null);
        requestLayout();
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int bottom;
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((LayoutParams) virtualChildAt.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = virtualChildAt2.getBottom() + ((LayoutParams) virtualChildAt2.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas) {
        int right;
        int left;
        int virtualChildCount = getVirtualChildCount();
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < virtualChildCount; i++) {
            View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (zIsLayoutRtl) {
                    left = virtualChildAt.getRight() + layoutParams.rightMargin;
                } else {
                    left = (virtualChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, left);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 != null) {
                LayoutParams layoutParams2 = (LayoutParams) virtualChildAt2.getLayoutParams();
                if (zIsLayoutRtl) {
                    right = (virtualChildAt2.getLeft() - layoutParams2.leftMargin) - this.mDividerWidth;
                } else {
                    right = virtualChildAt2.getRight() + layoutParams2.rightMargin;
                }
            } else if (zIsLayoutRtl) {
                right = getPaddingLeft();
            } else {
                right = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, right);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(this.mBaselineAlignedChildIndex);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int bottom = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor) != 48) {
            if (i == 16) {
                bottom += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
            } else if (i == 80) {
                bottom = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
            }
        }
        return bottom + ((LayoutParams) childAt.getLayoutParams()).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    View getVirtualChildAt(int i) {
        return getChildAt(i);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2);
        } else {
            measureHorizontal(i, i2);
        }
    }

    protected boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            return (this.mShowDividers & 1) != 0;
        }
        if (i == getChildCount()) {
            return (this.mShowDividers & 4) != 0;
        }
        if ((this.mShowDividers & 2) == 0) {
            return false;
        }
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (getChildAt(i2).getVisibility() != 8) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:151:0x0337  */
    /* JADX WARN: Code duplicated, block: B:157:0x0345  */
    /* JADX WARN: Code duplicated, block: B:60:0x0166  */
    /* JADX WARN: Code duplicated, block: B:63:0x016d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:65:0x0172  */
    /* JADX WARN: Code duplicated, block: B:67:0x017a  */
    /* JADX WARN: Code duplicated, block: B:69:0x017e  */
    void measureVertical(int i, int i2) {
        int i3;
        int i4;
        int iMax;
        int i5;
        int i6;
        int i7;
        boolean z;
        boolean z2;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int iMax2;
        LayoutParams layoutParams;
        View view;
        int iMax3;
        int i14;
        boolean z3;
        int i15;
        int measuredWidth;
        boolean z4;
        int iMax4;
        int iCombineMeasuredStates = 0;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i16 = this.mBaselineAlignedChildIndex;
        boolean z5 = this.mUseLargestChild;
        float f = 0.0f;
        int i17 = 0;
        int i18 = Integer.MIN_VALUE;
        int i19 = 0;
        int childrenSkipCount = 0;
        boolean z6 = false;
        boolean z7 = true;
        boolean z8 = false;
        int i20 = 0;
        while (childrenSkipCount < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount);
            } else {
                int i21 = i17;
                if (virtualChildAt.getVisibility() == 8) {
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                    i17 = i21;
                } else {
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt.getLayoutParams();
                    float f2 = f + layoutParams2.weight;
                    if (mode2 == 1073741824 && layoutParams2.height == 0 && layoutParams2.weight > 0.0f) {
                        int i22 = this.mTotalLength;
                        iMax3 = i18;
                        this.mTotalLength = Math.max(i22, layoutParams2.topMargin + i22 + layoutParams2.bottomMargin);
                        i13 = i19;
                        view = virtualChildAt;
                        virtualChildCount = virtualChildCount;
                        mode2 = mode2;
                        iMax2 = i20;
                        i11 = i21;
                        z6 = true;
                        i12 = childrenSkipCount;
                        layoutParams = layoutParams2;
                    } else {
                        i9 = i18;
                        if (layoutParams2.height != 0 || layoutParams2.weight <= 0.0f) {
                            i10 = Integer.MIN_VALUE;
                        } else {
                            layoutParams2.height = -2;
                            i10 = 0;
                        }
                        i11 = i21;
                        int i23 = i10;
                        virtualChildCount = virtualChildCount;
                        mode2 = mode2;
                        i12 = childrenSkipCount;
                        i13 = i19;
                        iMax2 = i20;
                        layoutParams = layoutParams2;
                        measureChildBeforeLayout(virtualChildAt, childrenSkipCount, i, 0, i2, f2 == 0.0f ? this.mTotalLength : 0);
                        if (i23 != Integer.MIN_VALUE) {
                            layoutParams.height = i23;
                        }
                        int measuredHeight = virtualChildAt.getMeasuredHeight();
                        int i24 = this.mTotalLength;
                        view = virtualChildAt;
                        this.mTotalLength = Math.max(i24, i24 + measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
                        if (z5) {
                            iMax3 = Math.max(measuredHeight, i9);
                        }
                    }
                    if (i16 >= 0) {
                        iMax3 = i9;
                        if (i16 == i12 + 1) {
                            this.mBaselineChildTop = this.mTotalLength;
                        }
                    }
                    if (i12 < i16 && layoutParams.weight > 0.0f) {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode != 1073741824) {
                        i14 = -1;
                        if (layoutParams.width == -1) {
                            z3 = true;
                            z8 = true;
                        }
                        i15 = layoutParams.leftMargin + layoutParams.rightMargin;
                        measuredWidth = view.getMeasuredWidth() + i15;
                        int iMax5 = Math.max(i11, measuredWidth);
                        int iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, view.getMeasuredState());
                        if (z7 || layoutParams.width != i14) {
                            z4 = false;
                        } else {
                            z4 = true;
                        }
                        if (layoutParams.weight > 0.0f) {
                            if (!z3) {
                                i15 = measuredWidth;
                            }
                            iMax4 = Math.max(i13, i15);
                        } else {
                            iMax4 = i13;
                            if (z3) {
                                measuredWidth = i15;
                            }
                            iMax2 = Math.max(iMax2, measuredWidth);
                        }
                        int childrenSkipCount2 = getChildrenSkipCount(view, i12) + i12;
                        z7 = z4;
                        i17 = iMax5;
                        iCombineMeasuredStates = iCombineMeasuredStates2;
                        i19 = iMax4;
                        i18 = iMax3;
                        i20 = iMax2;
                        childrenSkipCount = childrenSkipCount2;
                        f = f2;
                    } else {
                        i14 = -1;
                    }
                    z3 = false;
                    i15 = layoutParams.leftMargin + layoutParams.rightMargin;
                    measuredWidth = view.getMeasuredWidth() + i15;
                    int iMax6 = Math.max(i11, measuredWidth);
                    int iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates, view.getMeasuredState());
                    if (z7) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    if (layoutParams.weight > 0.0f) {
                        if (!z3) {
                            i15 = measuredWidth;
                        }
                        iMax4 = Math.max(i13, i15);
                    } else {
                        iMax4 = i13;
                        if (z3) {
                            measuredWidth = i15;
                        }
                        iMax2 = Math.max(iMax2, measuredWidth);
                    }
                    int childrenSkipCount3 = getChildrenSkipCount(view, i12) + i12;
                    z7 = z4;
                    i17 = iMax6;
                    iCombineMeasuredStates = iCombineMeasuredStates3;
                    i19 = iMax4;
                    i18 = iMax3;
                    i20 = iMax2;
                    childrenSkipCount = childrenSkipCount3;
                    f = f2;
                }
                childrenSkipCount++;
                mode2 = mode2;
                virtualChildCount = virtualChildCount;
            }
            childrenSkipCount++;
            mode2 = mode2;
            virtualChildCount = virtualChildCount;
        }
        int iMax7 = i17;
        int i25 = i18;
        int i26 = virtualChildCount;
        int i27 = mode2;
        int iMax8 = i20;
        int i28 = i19;
        if (this.mTotalLength > 0) {
            i3 = i26;
            if (hasDividerBeforeChildAt(i3)) {
                this.mTotalLength += this.mDividerHeight;
            }
        } else {
            i3 = i26;
        }
        if (z5) {
            i4 = i27;
            if (i4 == Integer.MIN_VALUE || i4 == 0) {
                this.mTotalLength = 0;
                int childrenSkipCount4 = 0;
                while (childrenSkipCount4 < i3) {
                    View virtualChildAt2 = getVirtualChildAt(childrenSkipCount4);
                    if (virtualChildAt2 == null) {
                        this.mTotalLength += measureNullChild(childrenSkipCount4);
                    } else if (virtualChildAt2.getVisibility() == 8) {
                        childrenSkipCount4 += getChildrenSkipCount(virtualChildAt2, childrenSkipCount4);
                    } else {
                        LayoutParams layoutParams3 = (LayoutParams) virtualChildAt2.getLayoutParams();
                        int i29 = this.mTotalLength;
                        this.mTotalLength = Math.max(i29, i29 + i25 + layoutParams3.topMargin + layoutParams3.bottomMargin + getNextLocationOffset(virtualChildAt2));
                    }
                    childrenSkipCount4++;
                }
            }
        } else {
            i4 = i27;
        }
        this.mTotalLength += getPaddingTop() + getPaddingBottom();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), i2, 0);
        int i30 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
        if (z6 || (i30 != 0 && f > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f = this.mWeightSum;
            }
            this.mTotalLength = 0;
            float f3 = f;
            int i31 = 0;
            while (i31 < i3) {
                View virtualChildAt3 = getVirtualChildAt(i31);
                if (virtualChildAt3.getVisibility() == 8) {
                    i6 = i30;
                } else {
                    LayoutParams layoutParams4 = (LayoutParams) virtualChildAt3.getLayoutParams();
                    float f4 = layoutParams4.weight;
                    if (f4 > 0.0f) {
                        int i32 = (int) ((i30 * f4) / f3);
                        float f5 = f3 - f4;
                        int i33 = i30 - i32;
                        int childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight() + layoutParams4.leftMargin + layoutParams4.rightMargin, layoutParams4.width);
                        if (layoutParams4.height == 0) {
                            i8 = 1073741824;
                            if (i4 == 1073741824) {
                                if (i32 <= 0) {
                                    i32 = 0;
                                }
                                virtualChildAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(i32, 1073741824));
                            }
                            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt3.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                            f3 = f5;
                            i5 = i33;
                        } else {
                            i8 = 1073741824;
                        }
                        int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i32;
                        if (measuredHeight2 < 0) {
                            measuredHeight2 = 0;
                        }
                        virtualChildAt3.measure(childMeasureSpec, View.MeasureSpec.makeMeasureSpec(measuredHeight2, i8));
                        iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, virtualChildAt3.getMeasuredState() & InputDeviceCompat.SOURCE_ANY);
                        f3 = f5;
                        i5 = i33;
                    } else {
                        i5 = i30;
                    }
                    int i34 = layoutParams4.leftMargin + layoutParams4.rightMargin;
                    int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i34;
                    iMax7 = Math.max(iMax7, measuredWidth2);
                    if (mode != 1073741824) {
                        i6 = i5;
                        i7 = -1;
                        z = layoutParams4.width == -1;
                        if (!z) {
                            i34 = measuredWidth2;
                        }
                        iMax8 = Math.max(iMax8, i34);
                        if (z7 || layoutParams4.width != i7) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        int i35 = this.mTotalLength;
                        this.mTotalLength = Math.max(i35, i35 + virtualChildAt3.getMeasuredHeight() + layoutParams4.topMargin + layoutParams4.bottomMargin + getNextLocationOffset(virtualChildAt3));
                        z7 = z2;
                    } else {
                        i6 = i5;
                        i7 = -1;
                    }
                    if (!z) {
                        i34 = measuredWidth2;
                    }
                    iMax8 = Math.max(iMax8, i34);
                    if (z7) {
                        z2 = false;
                    } else {
                        z2 = false;
                    }
                    int i36 = this.mTotalLength;
                    this.mTotalLength = Math.max(i36, i36 + virtualChildAt3.getMeasuredHeight() + layoutParams4.topMargin + layoutParams4.bottomMargin + getNextLocationOffset(virtualChildAt3));
                    z7 = z2;
                }
                i31++;
                i4 = i4;
                i30 = i6;
            }
            this.mTotalLength += getPaddingTop() + getPaddingBottom();
            iMax = iMax8;
        } else {
            iMax = Math.max(iMax8, i28);
            if (z5 && i4 != 1073741824) {
                for (int i37 = 0; i37 < i3; i37++) {
                    View virtualChildAt4 = getVirtualChildAt(i37);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i25, 1073741824));
                    }
                }
            }
        }
        if (z7 || mode == 1073741824) {
            iMax = iMax7;
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(iMax + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), i, iCombineMeasuredStates), iResolveSizeAndState);
        if (z8) {
            forceUniformWidth(i3, i2);
        }
    }

    private void forceUniformWidth(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = virtualChildAt.getMeasuredHeight();
                    measureChildWithMargins(virtualChildAt, iMakeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:199:0x045f  */
    /* JADX WARN: Code duplicated, block: B:60:0x0179  */
    /* JADX WARN: Code duplicated, block: B:67:0x019a  */
    /* JADX WARN: Code duplicated, block: B:68:0x019d  */
    /* JADX WARN: Code duplicated, block: B:75:0x01c9  */
    /* JADX WARN: Code duplicated, block: B:78:0x01d0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:80:0x01d5  */
    /* JADX WARN: Code duplicated, block: B:83:0x01de  */
    /* JADX WARN: Code duplicated, block: B:85:0x01e2  */
    void measureHorizontal(int i, int i2) {
        int[] iArr;
        int i3;
        int iMax;
        int i4;
        float f;
        int i5;
        int baseline;
        int i6;
        int i7;
        int i8;
        boolean z;
        boolean z2;
        LayoutParams layoutParams;
        int i9;
        View view;
        int i10;
        boolean z3;
        int i11;
        int measuredHeight;
        boolean z4;
        int baseline2;
        int i12;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr2 = this.mMaxAscent;
        int[] iArr3 = this.mMaxDescent;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        boolean z5 = this.mBaselineAligned;
        boolean z6 = this.mUseLargestChild;
        int i13 = 1073741824;
        boolean z7 = mode == 1073741824;
        int childrenSkipCount = 0;
        int iMax2 = Integer.MIN_VALUE;
        float f2 = 0.0f;
        int i14 = 0;
        boolean z8 = false;
        int iMax3 = 0;
        int iMax4 = 0;
        int i15 = 0;
        boolean z9 = true;
        boolean z10 = false;
        while (true) {
            iArr = iArr3;
            i3 = 8;
            if (childrenSkipCount >= virtualChildCount) {
                break;
            }
            View virtualChildAt = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(childrenSkipCount);
            } else {
                if (virtualChildAt.getVisibility() == 8) {
                    childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                } else {
                    if (hasDividerBeforeChildAt(childrenSkipCount)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt.getLayoutParams();
                    f2 += layoutParams2.weight;
                    if (mode != i13 || layoutParams2.width != 0 || layoutParams2.weight <= 0.0f) {
                        if (layoutParams2.width != 0 || layoutParams2.weight <= 0.0f) {
                            i7 = Integer.MIN_VALUE;
                        } else {
                            layoutParams2.width = -2;
                            i7 = 0;
                        }
                        i8 = childrenSkipCount;
                        int i16 = i7;
                        z = z6;
                        z2 = z5;
                        layoutParams = layoutParams2;
                        i9 = -1;
                        measureChildBeforeLayout(virtualChildAt, i8, i, f2 == 0.0f ? this.mTotalLength : 0, i2, 0);
                        if (i16 != Integer.MIN_VALUE) {
                            layoutParams.width = i16;
                        }
                        int measuredWidth = virtualChildAt.getMeasuredWidth();
                        if (z7) {
                            view = virtualChildAt;
                            this.mTotalLength += layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(view);
                        } else {
                            view = virtualChildAt;
                            int i17 = this.mTotalLength;
                            this.mTotalLength = Math.max(i17, i17 + measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin + getNextLocationOffset(view));
                        }
                        if (z) {
                            iMax2 = Math.max(measuredWidth, iMax2);
                        }
                    } else {
                        if (z7) {
                            this.mTotalLength += layoutParams2.leftMargin + layoutParams2.rightMargin;
                        } else {
                            int i18 = this.mTotalLength;
                            this.mTotalLength = Math.max(i18, layoutParams2.leftMargin + i18 + layoutParams2.rightMargin);
                        }
                        if (z5) {
                            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                            virtualChildAt.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                            i8 = childrenSkipCount;
                            z = z6;
                            z2 = z5;
                            layoutParams = layoutParams2;
                            view = virtualChildAt;
                            i9 = -1;
                        } else {
                            i8 = childrenSkipCount;
                            z = z6;
                            z2 = z5;
                            layoutParams = layoutParams2;
                            view = virtualChildAt;
                            i10 = 1073741824;
                            i9 = -1;
                            z8 = true;
                        }
                        if (mode2 == i10 && layoutParams.height == i9) {
                            z3 = true;
                            z10 = true;
                        } else {
                            z3 = false;
                        }
                        i11 = layoutParams.topMargin + layoutParams.bottomMargin;
                        measuredHeight = view.getMeasuredHeight() + i11;
                        int iCombineMeasuredStates = View.combineMeasuredStates(i15, view.getMeasuredState());
                        if (z2 && (baseline2 = view.getBaseline()) != i9) {
                            if (layoutParams.gravity < 0) {
                                i12 = this.mGravity;
                            } else {
                                i12 = layoutParams.gravity;
                            }
                            int i19 = (((i12 & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor) >> 4) & (-2)) >> 1;
                            iArr2[i19] = Math.max(iArr2[i19], baseline2);
                            iArr[i19] = Math.max(iArr[i19], measuredHeight - baseline2);
                        }
                        int iMax5 = Math.max(i14, measuredHeight);
                        if (z9 || layoutParams.height != i9) {
                            z4 = false;
                        } else {
                            z4 = true;
                        }
                        if (layoutParams.weight > 0.0f) {
                            if (!z3) {
                                i11 = measuredHeight;
                            }
                            iMax4 = Math.max(iMax4, i11);
                        } else {
                            int i20 = iMax4;
                            if (z3) {
                                measuredHeight = i11;
                            }
                            iMax3 = Math.max(iMax3, measuredHeight);
                            iMax4 = i20;
                        }
                        int i21 = i8;
                        i14 = iMax5;
                        childrenSkipCount = getChildrenSkipCount(view, i21) + i21;
                        i15 = iCombineMeasuredStates;
                        z9 = z4;
                    }
                    i10 = 1073741824;
                    if (mode2 == i10) {
                        z3 = false;
                    } else {
                        z3 = false;
                    }
                    i11 = layoutParams.topMargin + layoutParams.bottomMargin;
                    measuredHeight = view.getMeasuredHeight() + i11;
                    int iCombineMeasuredStates2 = View.combineMeasuredStates(i15, view.getMeasuredState());
                    if (z2) {
                        if (layoutParams.gravity < 0) {
                            i12 = this.mGravity;
                        } else {
                            i12 = layoutParams.gravity;
                        }
                        int i110 = (((i12 & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor) >> 4) & (-2)) >> 1;
                        iArr2[i110] = Math.max(iArr2[i110], baseline2);
                        iArr[i110] = Math.max(iArr[i110], measuredHeight - baseline2);
                    }
                    int iMax6 = Math.max(i14, measuredHeight);
                    if (z9) {
                        z4 = false;
                    } else {
                        z4 = false;
                    }
                    if (layoutParams.weight > 0.0f) {
                        if (!z3) {
                            i11 = measuredHeight;
                        }
                        iMax4 = Math.max(iMax4, i11);
                    } else {
                        int i22 = iMax4;
                        if (z3) {
                            measuredHeight = i11;
                        }
                        iMax3 = Math.max(iMax3, measuredHeight);
                        iMax4 = i22;
                    }
                    int i23 = i8;
                    i14 = iMax6;
                    childrenSkipCount = getChildrenSkipCount(view, i23) + i23;
                    i15 = iCombineMeasuredStates2;
                    z9 = z4;
                }
                childrenSkipCount++;
                iArr3 = iArr;
                z6 = z;
                z5 = z2;
                i13 = 1073741824;
            }
            z = z6;
            z2 = z5;
            childrenSkipCount++;
            iArr3 = iArr;
            z6 = z;
            z5 = z2;
            i13 = 1073741824;
        }
        boolean z11 = z6;
        boolean z12 = z5;
        int iMax7 = i14;
        int i24 = iMax3;
        int i25 = iMax4;
        int iCombineMeasuredStates3 = i15;
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (iArr2[1] != -1 || iArr2[0] != -1 || iArr2[2] != -1 || iArr2[3] != -1) {
            iMax7 = Math.max(iMax7, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
        }
        if (z11 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0;
            int childrenSkipCount2 = 0;
            while (childrenSkipCount2 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(childrenSkipCount2);
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(childrenSkipCount2);
                } else if (virtualChildAt2.getVisibility() == i3) {
                    childrenSkipCount2 += getChildrenSkipCount(virtualChildAt2, childrenSkipCount2);
                } else {
                    LayoutParams layoutParams3 = (LayoutParams) virtualChildAt2.getLayoutParams();
                    if (z7) {
                        this.mTotalLength += layoutParams3.leftMargin + iMax2 + layoutParams3.rightMargin + getNextLocationOffset(virtualChildAt2);
                    } else {
                        int i26 = this.mTotalLength;
                        this.mTotalLength = Math.max(i26, i26 + iMax2 + layoutParams3.leftMargin + layoutParams3.rightMargin + getNextLocationOffset(virtualChildAt2));
                    }
                }
                childrenSkipCount2++;
                i3 = 8;
            }
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), i, 0);
        int i27 = (16777215 & iResolveSizeAndState) - this.mTotalLength;
        if (z8 || (i27 != 0 && f2 > 0.0f)) {
            float f3 = this.mWeightSum > 0.0f ? this.mWeightSum : f2;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            this.mTotalLength = 0;
            int i28 = i24;
            int iMax8 = -1;
            float f4 = f3;
            int i29 = 0;
            while (i29 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i29);
                if (virtualChildAt3 == null || virtualChildAt3.getVisibility() == 8) {
                    i4 = i27;
                } else {
                    LayoutParams layoutParams4 = (LayoutParams) virtualChildAt3.getLayoutParams();
                    float f5 = layoutParams4.weight;
                    if (f5 > 0.0f) {
                        int i30 = (int) ((i27 * f5) / f4);
                        f = f4 - f5;
                        i4 = i27 - i30;
                        int childMeasureSpec = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom() + layoutParams4.topMargin + layoutParams4.bottomMargin, layoutParams4.height);
                        if (layoutParams4.width == 0) {
                            i6 = 1073741824;
                            if (mode == 1073741824) {
                                if (i30 <= 0) {
                                    i30 = 0;
                                }
                                virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(i30, 1073741824), childMeasureSpec);
                            }
                            iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, virtualChildAt3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                        } else {
                            i6 = 1073741824;
                        }
                        int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i30;
                        if (measuredWidth2 < 0) {
                            measuredWidth2 = 0;
                        }
                        virtualChildAt3.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth2, i6), childMeasureSpec);
                        iCombineMeasuredStates3 = View.combineMeasuredStates(iCombineMeasuredStates3, virtualChildAt3.getMeasuredState() & ViewCompat.MEASURED_STATE_MASK);
                    } else {
                        f = f4;
                        i4 = i27;
                    }
                    if (z7) {
                        this.mTotalLength += virtualChildAt3.getMeasuredWidth() + layoutParams4.leftMargin + layoutParams4.rightMargin + getNextLocationOffset(virtualChildAt3);
                    } else {
                        int i31 = this.mTotalLength;
                        this.mTotalLength = Math.max(i31, virtualChildAt3.getMeasuredWidth() + i31 + layoutParams4.leftMargin + layoutParams4.rightMargin + getNextLocationOffset(virtualChildAt3));
                    }
                    boolean z13 = mode2 != 1073741824 && layoutParams4.height == -1;
                    int i32 = layoutParams4.topMargin + layoutParams4.bottomMargin;
                    int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i32;
                    iMax8 = Math.max(iMax8, measuredHeight2);
                    if (!z13) {
                        i32 = measuredHeight2;
                    }
                    int iMax9 = Math.max(i28, i32);
                    if (z9) {
                        i5 = -1;
                        boolean z14 = layoutParams4.height == -1;
                        if (!z12 && (baseline = virtualChildAt3.getBaseline()) != i5) {
                            int i33 = ((((layoutParams4.gravity < 0 ? this.mGravity : layoutParams4.gravity) & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor) >> 4) & (-2)) >> 1;
                            iArr2[i33] = Math.max(iArr2[i33], baseline);
                            iArr[i33] = Math.max(iArr[i33], measuredHeight2 - baseline);
                        }
                        i28 = iMax9;
                        z9 = z14;
                        f4 = f;
                    } else {
                        i5 = -1;
                    }
                    if (!z12) {
                    }
                    i28 = iMax9;
                    z9 = z14;
                    f4 = f;
                }
                i29++;
                i27 = i4;
            }
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            iMax7 = (iArr2[1] == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) ? iMax8 : Math.max(iMax8, Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))) + Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))));
            iMax = i28;
        } else {
            iMax = Math.max(i24, i25);
            if (z11 && mode != 1073741824) {
                for (int i34 = 0; i34 < virtualChildCount; i34++) {
                    View virtualChildAt4 = getVirtualChildAt(i34);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(View.MeasureSpec.makeMeasureSpec(iMax2, 1073741824), View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
        }
        if (!z9 && mode2 != 1073741824) {
            iMax7 = iMax;
        }
        setMeasuredDimension(iResolveSizeAndState | ((-16777216) & iCombineMeasuredStates3), View.resolveSizeAndState(Math.max(iMax7 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight()), i2, iCombineMeasuredStates3 << 16));
        if (z10) {
            forceUniformHeight(virtualChildCount, i);
        }
    }

    private void forceUniformHeight(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i4 = layoutParams.width;
                    layoutParams.width = virtualChildAt.getMeasuredWidth();
                    measureChildWithMargins(virtualChildAt, i2, 0, iMakeMeasureSpec, 0);
                    layoutParams.width = i4;
                }
            }
        }
    }

    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
    }

    void layoutVertical(int i, int i2, int i3, int i4) {
        int paddingTop;
        int i5;
        int paddingLeft = getPaddingLeft();
        int i6 = i3 - i;
        int paddingRight = i6 - getPaddingRight();
        int paddingRight2 = (i6 - paddingLeft) - getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i7 = this.mGravity & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor;
        int i8 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if (i7 == 16) {
            paddingTop = (((i4 - i2) - this.mTotalLength) / 2) + getPaddingTop();
        } else if (i7 == 80) {
            paddingTop = ((getPaddingTop() + i4) - i2) - this.mTotalLength;
        } else {
            paddingTop = getPaddingTop();
        }
        int childrenSkipCount = 0;
        while (childrenSkipCount < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(childrenSkipCount);
            if (virtualChildAt == null) {
                paddingTop += measureNullChild(childrenSkipCount);
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                int i9 = layoutParams.gravity;
                if (i9 < 0) {
                    i9 = i8;
                }
                int absoluteGravity = GravityCompat.getAbsoluteGravity(i9, ViewCompat.getLayoutDirection(this)) & 7;
                if (absoluteGravity == 1) {
                    i5 = ((((paddingRight2 - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin;
                } else if (absoluteGravity == 5) {
                    i5 = (paddingRight - measuredWidth) - layoutParams.rightMargin;
                } else {
                    i5 = layoutParams.leftMargin + paddingLeft;
                }
                int i10 = i5;
                if (hasDividerBeforeChildAt(childrenSkipCount)) {
                    paddingTop += this.mDividerHeight;
                }
                int i11 = paddingTop + layoutParams.topMargin;
                setChildFrame(virtualChildAt, i10, i11 + getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                int nextLocationOffset = i11 + measuredHeight + layoutParams.bottomMargin + getNextLocationOffset(virtualChildAt);
                childrenSkipCount += getChildrenSkipCount(virtualChildAt, childrenSkipCount);
                paddingTop = nextLocationOffset;
            }
            childrenSkipCount++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:33:0x00bf  */
    /* JADX WARN: Code duplicated, block: B:35:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:37:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:39:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:41:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:43:0x00e3  */
    /* JADX WARN: Code duplicated, block: B:45:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:46:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f6  */
    /* JADX WARN: Code duplicated, block: B:51:0x010a  */
    void layoutHorizontal(int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        int i7;
        int i8;
        int baseline;
        int i9;
        int i10;
        int i11;
        int i12;
        int measuredHeight;
        boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int i13 = i4 - i2;
        int paddingBottom = i13 - getPaddingBottom();
        int paddingBottom2 = (i13 - paddingTop) - getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i14 = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i15 = this.mGravity & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor;
        boolean z = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        int absoluteGravity = GravityCompat.getAbsoluteGravity(i14, ViewCompat.getLayoutDirection(this));
        if (absoluteGravity == 1) {
            paddingLeft = (((i3 - i) - this.mTotalLength) / 2) + getPaddingLeft();
        } else if (absoluteGravity == 5) {
            paddingLeft = ((getPaddingLeft() + i3) - i) - this.mTotalLength;
        } else {
            paddingLeft = getPaddingLeft();
        }
        if (zIsLayoutRtl) {
            i5 = virtualChildCount - 1;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
        }
        int childrenSkipCount = 0;
        while (childrenSkipCount < virtualChildCount) {
            int i16 = i5 + (i6 * childrenSkipCount);
            View virtualChildAt = getVirtualChildAt(i16);
            if (virtualChildAt == null) {
                paddingLeft += measureNullChild(i16);
            } else {
                if (virtualChildAt.getVisibility() != 8) {
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    int measuredHeight2 = virtualChildAt.getMeasuredHeight();
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                    if (z) {
                        i7 = childrenSkipCount;
                        i8 = virtualChildCount;
                        baseline = layoutParams.height != -1 ? virtualChildAt.getBaseline() : -1;
                        i9 = layoutParams.gravity;
                        if (i9 < 0) {
                            i9 = i15;
                        }
                        i10 = i9 & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor;
                        if (i10 != 16) {
                            i11 = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + layoutParams.topMargin) - layoutParams.bottomMargin;
                        } else if (i10 != 48) {
                            i12 = layoutParams.topMargin + paddingTop;
                            if (baseline != -1) {
                                i12 += iArr[1] - baseline;
                            }
                            i11 = i12;
                        } else if (i10 != 80) {
                            i11 = paddingTop;
                        } else {
                            measuredHeight = (paddingBottom - measuredHeight2) - layoutParams.bottomMargin;
                            if (baseline != -1) {
                                measuredHeight -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - baseline);
                            }
                            i11 = measuredHeight;
                        }
                        if (hasDividerBeforeChildAt(i16)) {
                            paddingLeft += this.mDividerWidth;
                        }
                        int i17 = layoutParams.leftMargin + paddingLeft;
                        setChildFrame(virtualChildAt, i17 + getLocationOffset(virtualChildAt), i11, measuredWidth, measuredHeight2);
                        int nextLocationOffset = i17 + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(virtualChildAt);
                        childrenSkipCount = i7 + getChildrenSkipCount(virtualChildAt, i16);
                        paddingLeft = nextLocationOffset;
                    } else {
                        i7 = childrenSkipCount;
                        i8 = virtualChildCount;
                    }
                    i9 = layoutParams.gravity;
                    if (i9 < 0) {
                        i9 = i15;
                    }
                    i10 = i9 & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor;
                    if (i10 != 16) {
                        i11 = ((((paddingBottom2 - measuredHeight2) / 2) + paddingTop) + layoutParams.topMargin) - layoutParams.bottomMargin;
                    } else if (i10 != 48) {
                        i12 = layoutParams.topMargin + paddingTop;
                        if (baseline != -1) {
                            i12 += iArr[1] - baseline;
                        }
                        i11 = i12;
                    } else if (i10 != 80) {
                        i11 = paddingTop;
                    } else {
                        measuredHeight = (paddingBottom - measuredHeight2) - layoutParams.bottomMargin;
                        if (baseline != -1) {
                            measuredHeight -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - baseline);
                        }
                        i11 = measuredHeight;
                    }
                    if (hasDividerBeforeChildAt(i16)) {
                        paddingLeft += this.mDividerWidth;
                    }
                    int i18 = layoutParams.leftMargin + paddingLeft;
                    setChildFrame(virtualChildAt, i18 + getLocationOffset(virtualChildAt), i11, measuredWidth, measuredHeight2);
                    int nextLocationOffset2 = i18 + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(virtualChildAt);
                    childrenSkipCount = i7 + getChildrenSkipCount(virtualChildAt, i16);
                    paddingLeft = nextLocationOffset2;
                }
                childrenSkipCount++;
                virtualChildCount = i8;
                i15 = i15;
                paddingTop = paddingTop;
            }
            i8 = virtualChildCount;
            childrenSkipCount++;
            virtualChildCount = i8;
            i15 = i15;
            paddingTop = paddingTop;
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= GravityCompat.START;
            }
            if ((i & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((8388615 & this.mGravity) != i2) {
            this.mGravity = i2 | (this.mGravity & (-8388616));
            requestLayout();
        }
    }

    public void setVerticalGravity(int i) {
        int i2 = i & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor;
        if ((this.mGravity & com.reddington.scopesighter.R.styleable.AppCompatTheme_windowFixedHeightMajor) != i2) {
            this.mGravity = i2 | (this.mGravity & (-113));
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (Build.VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
            this.weight = typedArrayObtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = typedArrayObtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            typedArrayObtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2);
            this.gravity = -1;
            this.weight = f;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = -1;
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }
    }
}
