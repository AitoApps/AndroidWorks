package com.mal_suthra;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;

import androidx.appcompat.widget.AppCompatImageView;

public class Zoomable_ImageView extends AppCompatImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    /* access modifiers changed from: private */
    public OnTouchImageListener TouchImageListener = null;
    /* access modifiers changed from: private */
    public Context context;
    private ZoomVariables delayedZoomVariables;
    /* access modifiers changed from: private */
    public OnDoubleTapListener doubleTapListener = null;
    /* access modifiers changed from: private */
    public Fling fling;
    private boolean imageRenderedAtLeastOnce;
    /* access modifiers changed from: private */
    public float[] m;
    /* access modifiers changed from: private */
    public GestureDetector mGestureDetector;
    /* access modifiers changed from: private */
    public ScaleGestureDetector mScaleDetector;
    private ScaleType mScaleType;
    private float matchViewHeight;
    private float matchViewWidth;
    /* access modifiers changed from: private */
    public Matrix matrix;
    /* access modifiers changed from: private */
    public float maxScale;
    /* access modifiers changed from: private */
    public float minScale;
    /* access modifiers changed from: private */
    public float normalizedScale;
    private boolean onDrawReady;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;
    /* access modifiers changed from: private */
    public State state;
    private float superMaxScale;
    private float superMinScale;
    /* access modifiers changed from: private */
    public OnTouchListener userTouchListener = null;
    /* access modifiers changed from: private */
    public int viewHeight;
    /* access modifiers changed from: private */
    public int viewWidth;

    /* renamed from: com.suthra_malayalam_web.Zoomable_ImageView$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_XY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    @TargetApi(9)
    private class CompatScroller {
        boolean isPreGingerbread;
        OverScroller overScroller;
        Scroller scroller;

        public CompatScroller(Context context) {
            if (VERSION.SDK_INT < 9) {
                this.isPreGingerbread = true;
                this.scroller = new Scroller(context);
                return;
            }
            this.isPreGingerbread = false;
            this.overScroller = new OverScroller(context);
        }

        public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
            if (this.isPreGingerbread) {
                this.scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            } else {
                this.overScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            }
        }

        public void forceFinished(boolean finished) {
            if (this.isPreGingerbread) {
                this.scroller.forceFinished(finished);
            } else {
                this.overScroller.forceFinished(finished);
            }
        }

        public boolean isFinished() {
            if (this.isPreGingerbread) {
                return this.scroller.isFinished();
            }
            return this.overScroller.isFinished();
        }

        public boolean computeScrollOffset() {
            if (this.isPreGingerbread) {
                return this.scroller.computeScrollOffset();
            }
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
        }

        public int getCurrX() {
            if (this.isPreGingerbread) {
                return this.scroller.getCurrX();
            }
            return this.overScroller.getCurrX();
        }

        public int getCurrY() {
            if (this.isPreGingerbread) {
                return this.scroller.getCurrY();
            }
            return this.overScroller.getCurrY();
        }
    }

    private class DoubleTapZoom implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        DoubleTapZoom(float targetZoom2, float focusX, float focusY, boolean stretchImageToSuper2) {
            Zoomable_ImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = Zoomable_ImageView.this.normalizedScale;
            this.targetZoom = targetZoom2;
            this.stretchImageToSuper = stretchImageToSuper2;
            PointF bitmapPoint = Zoomable_ImageView.this.transformCoordTouchToBitmap(focusX, focusY, false);
            this.bitmapX = bitmapPoint.x;
            this.bitmapY = bitmapPoint.y;
            this.startTouch = Zoomable_ImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            this.endTouch = new PointF((float) (Zoomable_ImageView.this.viewWidth / 2), (float) (Zoomable_ImageView.this.viewHeight / 2));
        }

        public void run() {
            float t = interpolate();
            Zoomable_ImageView.this.scaleImage(calculateDeltaScale(t), this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            translateImageToCenterTouchPosition(t);
            Zoomable_ImageView.this.fixScaleTrans();
            Zoomable_ImageView zoomable_ImageView = Zoomable_ImageView.this;
            zoomable_ImageView.setImageMatrix(zoomable_ImageView.matrix);
            if (Zoomable_ImageView.this.TouchImageListener != null) {
                Zoomable_ImageView.this.TouchImageListener.onMove();
            }
            if (t < 1.0f) {
                Zoomable_ImageView.this.compatPostOnAnimation(this);
            } else {
                Zoomable_ImageView.this.setState(State.NONE);
            }
        }

        private void translateImageToCenterTouchPosition(float t) {
            float targetX = this.startTouch.x + ((this.endTouch.x - this.startTouch.x) * t);
            float targetY = this.startTouch.y + ((this.endTouch.y - this.startTouch.y) * t);
            PointF curr = Zoomable_ImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            Zoomable_ImageView.this.matrix.postTranslate(targetX - curr.x, targetY - curr.y);
        }

        private float interpolate() {
            return this.interpolator.getInterpolation(Math.min(1.0f, ((float) (System.currentTimeMillis() - this.startTime)) / ZOOM_TIME));
        }

        private double calculateDeltaScale(float t) {
            float f = this.startZoom;
            double zoom = (double) (f + ((this.targetZoom - f) * t));
            double access$700 = (double) Zoomable_ImageView.this.normalizedScale;
            Double.isNaN(zoom);
            Double.isNaN(access$700);
            return zoom / access$700;
        }
    }

    private class Fling implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;
        final /* synthetic */ Zoomable_ImageView this$0;

        Fling(Zoomable_ImageView zoomable_ImageView, int velocityX, int velocityY) {
            int maxX;
            int minX;
            int maxY;
            int maxY2;
            Zoomable_ImageView zoomable_ImageView2 = zoomable_ImageView;
            this.this$0 = zoomable_ImageView2;
            zoomable_ImageView2.setState(State.FLING);
            this.scroller = new CompatScroller(zoomable_ImageView.context);
            zoomable_ImageView.matrix.getValues(zoomable_ImageView.m);
            int startX = (int) zoomable_ImageView.m[2];
            int startY = (int) zoomable_ImageView.m[5];
            if (zoomable_ImageView.getImageWidth() > ((float) zoomable_ImageView.viewWidth)) {
                minX = zoomable_ImageView.viewWidth - ((int) zoomable_ImageView.getImageWidth());
                maxX = 0;
            } else {
                maxX = startX;
                minX = startX;
            }
            if (zoomable_ImageView.getImageHeight() > ((float) zoomable_ImageView.viewHeight)) {
                maxY = 0;
                maxY2 = zoomable_ImageView.viewHeight - ((int) zoomable_ImageView.getImageHeight());
            } else {
                maxY = startY;
                maxY2 = startY;
            }
            this.scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, maxY2, maxY);
            this.currX = startX;
            this.currY = startY;
        }

        public void cancelFling() {
            if (this.scroller != null) {
                this.this$0.setState(State.NONE);
                this.scroller.forceFinished(true);
            }
        }

        public void run() {
            if (this.this$0.TouchImageListener != null) {
                this.this$0.TouchImageListener.onMove();
            }
            if (this.scroller.isFinished()) {
                this.scroller = null;
                return;
            }
            if (this.scroller.computeScrollOffset()) {
                int newX = this.scroller.getCurrX();
                int newY = this.scroller.getCurrY();
                int transX = newX - this.currX;
                int transY = newY - this.currY;
                this.currX = newX;
                this.currY = newY;
                this.this$0.matrix.postTranslate((float) transX, (float) transY);
                this.this$0.fixTrans();
                Zoomable_ImageView zoomable_ImageView = this.this$0;
                zoomable_ImageView.setImageMatrix(zoomable_ImageView.matrix);
                this.this$0.compatPostOnAnimation(this);
            }
        }
    }

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
        }

        /* synthetic */ GestureListener(Zoomable_ImageView x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (Zoomable_ImageView.this.doubleTapListener != null) {
                return Zoomable_ImageView.this.doubleTapListener.onSingleTapConfirmed(e);
            }
            return Zoomable_ImageView.this.performClick();
        }

        public void onLongPress(MotionEvent e) {
            Zoomable_ImageView.this.performLongClick();
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Zoomable_ImageView.this.fling != null) {
                Zoomable_ImageView.this.fling.cancelFling();
            }
            Zoomable_ImageView zoomable_ImageView = Zoomable_ImageView.this;
            zoomable_ImageView.fling = new Fling(zoomable_ImageView, (int) velocityX, (int) velocityY);
            Zoomable_ImageView zoomable_ImageView2 = Zoomable_ImageView.this;
            zoomable_ImageView2.compatPostOnAnimation(zoomable_ImageView2.fling);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        public boolean onDoubleTap(MotionEvent e) {
            boolean consumed = false;
            if (Zoomable_ImageView.this.doubleTapListener != null) {
                consumed = Zoomable_ImageView.this.doubleTapListener.onDoubleTap(e);
            }
            if (Zoomable_ImageView.this.state != State.NONE) {
                return consumed;
            }
            DoubleTapZoom doubleTapZoom = new DoubleTapZoom(Zoomable_ImageView.this.normalizedScale == Zoomable_ImageView.this.minScale ? Zoomable_ImageView.this.maxScale : Zoomable_ImageView.this.minScale, e.getX(), e.getY(), false);
            Zoomable_ImageView.this.compatPostOnAnimation(doubleTapZoom);
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            if (Zoomable_ImageView.this.doubleTapListener != null) {
                return Zoomable_ImageView.this.doubleTapListener.onDoubleTapEvent(e);
            }
            return false;
        }
    }

    public interface OnTouchImageListener {
        void onMove();
    }

    private class PrivateOnTouchListener implements OnTouchListener {
        private PointF last;

        private PrivateOnTouchListener() {
            this.last = new PointF();
        }

        /* synthetic */ PrivateOnTouchListener(Zoomable_ImageView x0, AnonymousClass1 x1) {
            this();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x004a, code lost:
            if (r1 != 6) goto L_0x00c6;
         */
        public boolean onTouch(View v, MotionEvent event) {
            Zoomable_ImageView.this.mScaleDetector.onTouchEvent(event);
            Zoomable_ImageView.this.mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());
            if (Zoomable_ImageView.this.state == State.NONE || Zoomable_ImageView.this.state == State.DRAG || Zoomable_ImageView.this.state == State.FLING) {
                int action = event.getAction();
                if (action != 0) {
                    if (action != 1) {
                        if (action == 2) {
                            if (Zoomable_ImageView.this.state == State.DRAG) {
                                float deltaX = curr.x - this.last.x;
                                float deltaY = curr.y - this.last.y;
                                Zoomable_ImageView zoomable_ImageView = Zoomable_ImageView.this;
                                float fixTransX = zoomable_ImageView.getFixDragTrans(deltaX, (float) zoomable_ImageView.viewWidth, Zoomable_ImageView.this.getImageWidth());
                                Zoomable_ImageView zoomable_ImageView2 = Zoomable_ImageView.this;
                                Zoomable_ImageView.this.matrix.postTranslate(fixTransX, zoomable_ImageView2.getFixDragTrans(deltaY, (float) zoomable_ImageView2.viewHeight, Zoomable_ImageView.this.getImageHeight()));
                                Zoomable_ImageView.this.fixTrans();
                                this.last.set(curr.x, curr.y);
                            }
                        }
                    }
                    Zoomable_ImageView.this.setState(State.NONE);
                } else {
                    this.last.set(curr);
                    if (Zoomable_ImageView.this.fling != null) {
                        Zoomable_ImageView.this.fling.cancelFling();
                    }
                    Zoomable_ImageView.this.setState(State.DRAG);
                }
            }
            Zoomable_ImageView zoomable_ImageView3 = Zoomable_ImageView.this;
            zoomable_ImageView3.setImageMatrix(zoomable_ImageView3.matrix);
            if (Zoomable_ImageView.this.userTouchListener != null) {
                Zoomable_ImageView.this.userTouchListener.onTouch(v, event);
            }
            if (Zoomable_ImageView.this.TouchImageListener != null) {
                Zoomable_ImageView.this.TouchImageListener.onMove();
            }
            return true;
        }
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        /* synthetic */ ScaleListener(Zoomable_ImageView x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            Zoomable_ImageView.this.setState(State.ZOOM);
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            Zoomable_ImageView.this.scaleImage((double) detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY(), true);
            if (Zoomable_ImageView.this.TouchImageListener != null) {
                Zoomable_ImageView.this.TouchImageListener.onMove();
            }
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            float targetZoom;
            super.onScaleEnd(detector);
            Zoomable_ImageView.this.setState(State.NONE);
            boolean animateToZoomBoundary = false;
            float targetZoom2 = Zoomable_ImageView.this.normalizedScale;
            if (Zoomable_ImageView.this.normalizedScale > Zoomable_ImageView.this.maxScale) {
                animateToZoomBoundary = true;
                targetZoom = Zoomable_ImageView.this.maxScale;
            } else if (Zoomable_ImageView.this.normalizedScale < Zoomable_ImageView.this.minScale) {
                animateToZoomBoundary = true;
                targetZoom = Zoomable_ImageView.this.minScale;
            } else {
                targetZoom = targetZoom2;
            }
            if (animateToZoomBoundary) {
                Zoomable_ImageView zoomable_ImageView = Zoomable_ImageView.this;
                DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom, (float) (zoomable_ImageView.viewWidth / 2), (float) (Zoomable_ImageView.this.viewHeight / 2), true);
                Zoomable_ImageView.this.compatPostOnAnimation(doubleTap);
            }
        }
    }

    private enum State {
        NONE,
        DRAG,
        ZOOM,
        FLING,
        ANIMATE_ZOOM
    }

    private class ZoomVariables {
        public float focusX;
        public float focusY;
        public float scale;
        public ScaleType scaleType;

        public ZoomVariables(float scale2, float focusX2, float focusY2, ScaleType scaleType2) {
            this.scale = scale2;
            this.focusX = focusX2;
            this.focusY = focusY2;
            this.scaleType = scaleType2;
        }
    }

    public Zoomable_ImageView(Context context2) {
        super(context2);
        sharedConstructing(context2);
    }

    public Zoomable_ImageView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        sharedConstructing(context2);
    }

    public Zoomable_ImageView(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        sharedConstructing(context2);
    }

    private void sharedConstructing(Context context2) {
        super.setClickable(true);
        this.context = context2;
        this.mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener(this, null));
        this.mGestureDetector = new GestureDetector(context2, new GestureListener(this, null));
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = this.minScale * SUPER_MIN_MULTIPLIER;
        this.superMaxScale = this.maxScale * SUPER_MAX_MULTIPLIER;
        setImageMatrix(this.matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener(this, null));
    }

    public void setOnTouchListener(OnTouchListener l) {
        this.userTouchListener = l;
    }

    public void setOnTouchImageListener(OnTouchImageListener l) {
        this.TouchImageListener = l;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener l) {
        this.doubleTapListener = l;
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        savePreviousImageValues();
        fitImageToView();
    }

    public void setScaleType(ScaleType type) {
        if (type == ScaleType.FIT_START || type == ScaleType.FIT_END) {
            throw new UnsupportedOperationException("TouchImage does not support FIT_START or FIT_END");
        } else if (type == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);
        } else {
            this.mScaleType = type;
            if (this.onDrawReady) {
                setZoom(this);
            }
        }
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public boolean isZoomed() {
        return this.normalizedScale != 1.0f;
    }

    public RectF getZoomedRect() {
        if (this.mScaleType != ScaleType.FIT_XY) {
            PointF topLeft = transformCoordTouchToBitmap(0.0f, 0.0f, true);
            PointF bottomRight = transformCoordTouchToBitmap((float) this.viewWidth, (float) this.viewHeight, true);
            float w = (float) getDrawable().getIntrinsicWidth();
            float h = (float) getDrawable().getIntrinsicHeight();
            return new RectF(topLeft.x / w, topLeft.y / h, bottomRight.x / w, bottomRight.y / h);
        }
        throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
    }

    private void savePreviousImageValues() {
        Matrix matrix2 = this.matrix;
        if (matrix2 != null && this.viewHeight != 0 && this.viewWidth != 0) {
            matrix2.getValues(this.m);
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = this.matchViewHeight;
            this.prevMatchViewWidth = this.matchViewWidth;
            this.prevViewHeight = this.viewHeight;
            this.prevViewWidth = this.viewWidth;
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.m);
        bundle.putFloatArray("matrix", this.m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state2) {
        if (state2 instanceof Bundle) {
            Bundle bundle = (Bundle) state2;
            this.normalizedScale = bundle.getFloat("saveScale");
            this.m = bundle.getFloatArray("matrix");
            this.prevMatrix.setValues(this.m);
            this.prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            this.prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            this.prevViewHeight = bundle.getInt("viewHeight");
            this.prevViewWidth = bundle.getInt("viewWidth");
            this.imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        ZoomVariables zoomVariables = this.delayedZoomVariables;
        if (zoomVariables != null) {
            setZoom(zoomVariables.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        savePreviousImageValues();
    }

    public float getMaxZoom() {
        return this.maxScale;
    }

    public void setMaxZoom(float max) {
        this.maxScale = max;
        this.superMaxScale = this.maxScale * SUPER_MAX_MULTIPLIER;
    }

    public float getMinZoom() {
        return this.minScale;
    }

    public float getCurrentZoom() {
        return this.normalizedScale;
    }

    public void setMinZoom(float min) {
        this.minScale = min;
        this.superMinScale = this.minScale * SUPER_MIN_MULTIPLIER;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        fitImageToView();
    }

    public void setZoom(float scale) {
        setZoom(scale, 0.5f, 0.5f);
    }

    public void setZoom(float scale, float focusX, float focusY) {
        setZoom(scale, focusX, focusY, this.mScaleType);
    }

    public void setZoom(float scale, float focusX, float focusY, ScaleType scaleType) {
        if (!this.onDrawReady) {
            ZoomVariables zoomVariables = new ZoomVariables(scale, focusX, focusY, scaleType);
            this.delayedZoomVariables = zoomVariables;
            return;
        }
        if (scaleType != this.mScaleType) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage((double) scale, (float) (this.viewWidth / 2), (float) (this.viewHeight / 2), true);
        this.matrix.getValues(this.m);
        this.m[2] = -((getImageWidth() * focusX) - (((float) this.viewWidth) * 0.5f));
        this.m[5] = -((getImageHeight() * focusY) - (((float) this.viewHeight) * 0.5f));
        this.matrix.setValues(this.m);
        fixTrans();
        setImageMatrix(this.matrix);
    }

    public void setZoom(Zoomable_ImageView img) {
        PointF center = img.getScrollPosition();
        setZoom(img.getCurrentZoom(), center.x, center.y, img.getScaleType());
    }

    public PointF getScrollPosition() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        PointF point = transformCoordTouchToBitmap((float) (this.viewWidth / 2), (float) (this.viewHeight / 2), true);
        point.x /= (float) drawableWidth;
        point.y /= (float) drawableHeight;
        return point;
    }

    public void setScrollPosition(float focusX, float focusY) {
        setZoom(this.normalizedScale, focusX, focusY);
    }

    /* access modifiers changed from: private */
    public void fixTrans() {
        this.matrix.getValues(this.m);
        float[] fArr = this.m;
        float transX = fArr[2];
        float transY = fArr[5];
        float fixTransX = getFixTrans(transX, (float) this.viewWidth, getImageWidth());
        float fixTransY = getFixTrans(transY, (float) this.viewHeight, getImageHeight());
        if (fixTransX != 0.0f || fixTransY != 0.0f) {
            this.matrix.postTranslate(fixTransX, fixTransY);
        }
    }

    /* access modifiers changed from: private */
    public void fixScaleTrans() {
        fixTrans();
        this.matrix.getValues(this.m);
        float imageWidth = getImageWidth();
        int i = this.viewWidth;
        if (imageWidth < ((float) i)) {
            this.m[2] = (((float) i) - getImageWidth()) / 2.0f;
        }
        float imageHeight = getImageHeight();
        int i2 = this.viewHeight;
        if (imageHeight < ((float) i2)) {
            this.m[5] = (((float) i2) - getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.m);
    }

    private float getFixTrans(float trans, float viewSize, float contentSize) {
        float maxTrans;
        float minTrans;
        if (contentSize <= viewSize) {
            minTrans = 0.0f;
            maxTrans = viewSize - contentSize;
        } else {
            minTrans = viewSize - contentSize;
            maxTrans = 0.0f;
        }
        if (trans < minTrans) {
            return (-trans) + minTrans;
        }
        if (trans > maxTrans) {
            return (-trans) + maxTrans;
        }
        return 0.0f;
    }

    /* access modifiers changed from: private */
    public float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0.0f;
        }
        return delta;
    }

    /* access modifiers changed from: private */
    public float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    /* access modifiers changed from: private */
    public float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        this.viewWidth = setViewSize(widthMode, widthSize, drawableWidth);
        this.viewHeight = setViewSize(heightMode, heightSize, drawableHeight);
        setMeasuredDimension(this.viewWidth, this.viewHeight);
        fitImageToView();
    }

    private void fitImageToView() {
        float scaleY;
        float scaleX;
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0 && this.matrix != null && this.prevMatrix != null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            float scaleX2 = ((float) this.viewWidth) / ((float) drawableWidth);
            float scaleY2 = ((float) this.viewHeight) / ((float) drawableHeight);
            int i = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
            if (i == 1) {
                scaleX = 1.0f;
                scaleY = 1.0f;
            } else if (i != 2) {
                if (i == 3) {
                    float min = Math.min(1.0f, Math.min(scaleX2, scaleY2));
                    scaleY2 = min;
                    scaleX2 = min;
                } else if (i != 4) {
                    if (i == 5) {
                        scaleX = scaleX2;
                        scaleY = scaleY2;
                    } else {
                        throw new UnsupportedOperationException("TouchImage does not support FIT_START or FIT_END");
                    }
                }
                float min2 = Math.min(scaleX2, scaleY2);
                scaleX = min2;
                scaleY = min2;
            } else {
                float max = Math.max(scaleX2, scaleY2);
                scaleX = max;
                scaleY = max;
            }
            int i2 = this.viewWidth;
            float redundantXSpace = ((float) i2) - (((float) drawableWidth) * scaleX);
            int i3 = this.viewHeight;
            float redundantYSpace = ((float) i3) - (((float) drawableHeight) * scaleY);
            this.matchViewWidth = ((float) i2) - redundantXSpace;
            this.matchViewHeight = ((float) i3) - redundantYSpace;
            if (isZoomed() || this.imageRenderedAtLeastOnce) {
                if (this.prevMatchViewWidth == 0.0f || this.prevMatchViewHeight == 0.0f) {
                    savePreviousImageValues();
                }
                this.prevMatrix.getValues(this.m);
                float[] fArr = this.m;
                float f = this.matchViewWidth / ((float) drawableWidth);
                float f2 = this.normalizedScale;
                fArr[0] = f * f2;
                fArr[4] = (this.matchViewHeight / ((float) drawableHeight)) * f2;
                float transX = fArr[2];
                float transY = fArr[5];
                float prevActualWidth = this.prevMatchViewWidth * f2;
                translateMatrixAfterRotate(2, transX, prevActualWidth, getImageWidth(), this.prevViewWidth, this.viewWidth, drawableWidth);
                float prevActualHeight = this.prevMatchViewHeight * this.normalizedScale;
                translateMatrixAfterRotate(5, transY, prevActualHeight, getImageHeight(), this.prevViewHeight, this.viewHeight, drawableHeight);
                this.matrix.setValues(this.m);
            } else {
                this.matrix.setScale(scaleX, scaleY);
                this.matrix.postTranslate(redundantXSpace / 2.0f, redundantYSpace / 2.0f);
                this.normalizedScale = 1.0f;
            }
            fixTrans();
            setImageMatrix(this.matrix);
        }
    }

    private int setViewSize(int mode, int size, int drawableWidth) {
        if (mode == Integer.MIN_VALUE) {
            return Math.min(drawableWidth, size);
        }
        if (mode == 0) {
            return drawableWidth;
        }
        if (mode != 1073741824) {
            return size;
        }
        return size;
    }

    private void translateMatrixAfterRotate(int axis, float trans, float prevImageSize, float imageSize, int prevViewSize, int viewSize, int drawableSize) {
        if (imageSize < ((float) viewSize)) {
            float[] fArr = this.m;
            fArr[axis] = (((float) viewSize) - (((float) drawableSize) * fArr[0])) * 0.5f;
        } else if (trans > 0.0f) {
            this.m[axis] = -((imageSize - ((float) viewSize)) * 0.5f);
        } else {
            this.m[axis] = -((((Math.abs(trans) + (((float) prevViewSize) * 0.5f)) / prevImageSize) * imageSize) - (((float) viewSize) * 0.5f));
        }
    }

    /* access modifiers changed from: private */
    public void setState(State state2) {
        this.state = state2;
    }

    public boolean canScrollHorizontallyFroyo(int direction) {
        return canScrollHorizontally(direction);
    }

    public boolean canScrollHorizontally(int direction) {
        this.matrix.getValues(this.m);
        float x = this.m[2];
        if (getImageWidth() < ((float) this.viewWidth)) {
            return false;
        }
        if (x >= -1.0f && direction < 0) {
            return false;
        }
        if (Math.abs(x) + ((float) this.viewWidth) + 1.0f < getImageWidth() || direction <= 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void scaleImage(double deltaScale, float focusX, float focusY, boolean stretchImageToSuper) {
        float upperScale;
        float lowerScale;
        if (stretchImageToSuper) {
            lowerScale = this.superMinScale;
            upperScale = this.superMaxScale;
        } else {
            lowerScale = this.minScale;
            upperScale = this.maxScale;
        }
        float origScale = this.normalizedScale;
        double d = (double) this.normalizedScale;
        Double.isNaN(d);
        this.normalizedScale = (float) (d * deltaScale);
        float f = this.normalizedScale;
        if (f > upperScale) {
            this.normalizedScale = upperScale;
            deltaScale = (double) (upperScale / origScale);
        } else if (f < lowerScale) {
            this.normalizedScale = lowerScale;
            deltaScale = (double) (lowerScale / origScale);
        }
        this.matrix.postScale((float) deltaScale, (float) deltaScale, focusX, focusY);
        fixScaleTrans();
    }

    /* access modifiers changed from: private */
    public PointF transformCoordTouchToBitmap(float x, float y, boolean clipToBitmap) {
        this.matrix.getValues(this.m);
        float origW = (float) getDrawable().getIntrinsicWidth();
        float origH = (float) getDrawable().getIntrinsicHeight();
        float[] fArr = this.m;
        float transX = fArr[2];
        float finalX = ((x - transX) * origW) / getImageWidth();
        float finalY = ((y - fArr[5]) * origH) / getImageHeight();
        if (clipToBitmap) {
            finalX = Math.min(Math.max(finalX, 0.0f), origW);
            finalY = Math.min(Math.max(finalY, 0.0f), origH);
        }
        return new PointF(finalX, finalY);
    }

    /* access modifiers changed from: private */
    public PointF transformCoordBitmapToTouch(float bx, float by) {
        this.matrix.getValues(this.m);
        return new PointF(this.m[2] + (getImageWidth() * (bx / ((float) getDrawable().getIntrinsicWidth()))), this.m[5] + (getImageHeight() * (by / ((float) getDrawable().getIntrinsicHeight()))));
    }

    /* access modifiers changed from: private */
    @TargetApi(16)
    public void compatPostOnAnimation(Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            postOnAnimation(runnable);
        } else {
            postDelayed(runnable, 16);
        }
    }

    private void printMatrixInfo() {
        float[] n = new float[9];
        this.matrix.getValues(n);
        StringBuilder sb = new StringBuilder();
        sb.append("Scale: ");
        sb.append(n[0]);
        sb.append(" TransX: ");
        sb.append(n[2]);
        sb.append(" TransY: ");
        sb.append(n[5]);
        Log.d(DEBUG, sb.toString());
    }
}
