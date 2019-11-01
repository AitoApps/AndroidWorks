package com.sanji;

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
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.OverScroller;
import android.widget.Scroller;

public class TouchImageView extends ImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;

    public Context context;
    private ZoomVariables delayedZoomVariables;

    public OnDoubleTapListener doubleTapListener = null;

    public Fling fling;
    private boolean imageRenderedAtLeastOnce;

    public float[] m;

    public GestureDetector mGestureDetector;

    public ScaleGestureDetector mScaleDetector;
    private ScaleType mScaleType;
    private float matchViewHeight;
    private float matchViewWidth;

    public Matrix matrix;

    public float maxScale;

    public float minScale;

    public float normalizedScale;
    private boolean onDrawReady;
    private float prevMatchViewHeight;
    private float prevMatchViewWidth;
    private Matrix prevMatrix;
    private int prevViewHeight;
    private int prevViewWidth;

    public State state;
    private float superMaxScale;
    private float superMinScale;

    public OnTouchImageViewListener touchImageViewListener = null;

    public OnTouchListener userTouchListener = null;

    public int viewHeight;

    public int viewWidth;

    /* renamed from: com.sanji.TouchImageView$1 reason: invalid class name */
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
                isPreGingerbread = true;
                scroller = new Scroller(context);
                return;
            }
            isPreGingerbread = false;
            overScroller = new OverScroller(context);
        }

        public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
            if (isPreGingerbread) {
                scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            } else {
                overScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
            }
        }

        public void forceFinished(boolean finished) {
            if (isPreGingerbread) {
                scroller.forceFinished(finished);
            } else {
                overScroller.forceFinished(finished);
            }
        }

        public boolean isFinished() {
            if (isPreGingerbread) {
                return scroller.isFinished();
            }
            return overScroller.isFinished();
        }

        public boolean computeScrollOffset() {
            if (isPreGingerbread) {
                return scroller.computeScrollOffset();
            }
            overScroller.computeScrollOffset();
            return overScroller.computeScrollOffset();
        }

        public int getCurrX() {
            if (isPreGingerbread) {
                return scroller.getCurrX();
            }
            return overScroller.getCurrX();
        }

        public int getCurrY() {
            if (isPreGingerbread) {
                return scroller.getCurrY();
            }
            return overScroller.getCurrY();
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
            TouchImageView.setState(State.ANIMATE_ZOOM);
            startTime = System.currentTimeMillis();
            startZoom = TouchImageView.normalizedScale;
            targetZoom = targetZoom2;
            stretchImageToSuper = stretchImageToSuper2;
            PointF bitmapPoint = TouchImageView.transformCoordTouchToBitmap(focusX, focusY, false);
            bitmapX = bitmapPoint.x;
            bitmapY = bitmapPoint.y;
            startTouch = TouchImageView.transformCoordBitmapToTouch(bitmapX, bitmapY);
            endTouch = new PointF((float) (TouchImageView.viewWidth / 2), (float) (TouchImageView.viewHeight / 2));
        }

        public void run() {
            float t = interpolate();
            TouchImageView.scaleImage(calculateDeltaScale(t), bitmapX, bitmapY, stretchImageToSuper);
            translateImageToCenterTouchPosition(t);
            TouchImageView.fixScaleTrans();
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.setImageMatrix(touchImageView.matrix);
            if (TouchImageView.touchImageViewListener != null) {
                TouchImageView.touchImageViewListener.onMove();
            }
            if (t < 1.0f) {
                TouchImageView.compatPostOnAnimation(this);
            } else {
                TouchImageView.setState(State.NONE);
            }
        }

        private void translateImageToCenterTouchPosition(float t) {
            float targetX = startTouch.x + ((endTouch.x - startTouch.x) * t);
            float targetY = startTouch.y + ((endTouch.y - startTouch.y) * t);
            PointF curr = TouchImageView.transformCoordBitmapToTouch(bitmapX, bitmapY);
            TouchImageView.matrix.postTranslate(targetX - curr.x, targetY - curr.y);
        }

        private float interpolate() {
            return interpolator.getInterpolation(Math.min(1.0f, ((float) (System.currentTimeMillis() - startTime)) / ZOOM_TIME));
        }

        private double calculateDeltaScale(float t) {
            float f = startZoom;
            double zoom = (double) (f + ((targetZoom - f) * t));
            double access$700 = (double) TouchImageView.normalizedScale;
            Double.isNaN(zoom);
            Double.isNaN(access$700);
            return zoom / access$700;
        }
    }

    private class Fling implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;
        final /* synthetic */ TouchImageView this$0;

        Fling(TouchImageView touchImageView, int velocityX, int velocityY) {
            int maxX;
            int minX;
            int maxY;
            int maxY2;
            TouchImageView touchImageView2 = touchImageView;
            this$0 = touchImageView2;
            touchImageView2.setState(State.FLING);
            scroller = new CompatScroller(touchImageView.context);
            touchImageView.matrix.getValues(touchImageView.m);
            int startX = (int) touchImageView.m[2];
            int startY = (int) touchImageView.m[5];
            if (touchImageView.getImageWidth() > ((float) touchImageView.viewWidth)) {
                minX = touchImageView.viewWidth - ((int) touchImageView.getImageWidth());
                maxX = 0;
            } else {
                maxX = startX;
                minX = startX;
            }
            if (touchImageView.getImageHeight() > ((float) touchImageView.viewHeight)) {
                maxY = 0;
                maxY2 = touchImageView.viewHeight - ((int) touchImageView.getImageHeight());
            } else {
                maxY = startY;
                maxY2 = startY;
            }
            scroller.fling(startX, startY, velocityX, velocityY, minX, maxX, maxY2, maxY);
            currX = startX;
            currY = startY;
        }

        public void cancelFling() {
            if (scroller != null) {
                this$0.setState(State.NONE);
                scroller.forceFinished(true);
            }
        }

        public void run() {
            if (this$0.touchImageViewListener != null) {
                this$0.touchImageViewListener.onMove();
            }
            if (scroller.isFinished()) {
                scroller = null;
                return;
            }
            if (scroller.computeScrollOffset()) {
                int newX = scroller.getCurrX();
                int newY = scroller.getCurrY();
                int transX = newX - currX;
                int transY = newY - currY;
                currX = newX;
                currY = newY;
                this$0.matrix.postTranslate((float) transX, (float) transY);
                this$0.fixTrans();
                TouchImageView touchImageView = this$0;
                touchImageView.setImageMatrix(touchImageView.matrix);
                this$0.compatPostOnAnimation(this);
            }
        }
    }

    private class GestureListener extends SimpleOnGestureListener {
        private GestureListener() {
        }

        /* synthetic */ GestureListener(TouchImageView x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (TouchImageView.doubleTapListener != null) {
                return TouchImageView.doubleTapListener.onSingleTapConfirmed(e);
            }
            return TouchImageView.performClick();
        }

        public void onLongPress(MotionEvent e) {
            TouchImageView.performLongClick();
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (TouchImageView.fling != null) {
                TouchImageView.fling.cancelFling();
            }
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.fling = new Fling(touchImageView, (int) velocityX, (int) velocityY);
            TouchImageView touchImageView2 = TouchImageView.this;
            touchImageView2.compatPostOnAnimation(touchImageView2.fling);
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        public boolean onDoubleTap(MotionEvent e) {
            boolean consumed = false;
            if (TouchImageView.doubleTapListener != null) {
                consumed = TouchImageView.doubleTapListener.onDoubleTap(e);
            }
            if (TouchImageView.state != State.NONE) {
                return consumed;
            }
            DoubleTapZoom doubleTapZoom = new DoubleTapZoom(TouchImageView.normalizedScale == TouchImageView.minScale ? TouchImageView.maxScale : TouchImageView.minScale, e.getX(), e.getY(), false);
            TouchImageView.compatPostOnAnimation(doubleTapZoom);
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            if (TouchImageView.doubleTapListener != null) {
                return TouchImageView.doubleTapListener.onDoubleTapEvent(e);
            }
            return false;
        }
    }

    public interface OnTouchImageViewListener {
        void onMove();
    }

    private class PrivateOnTouchListener implements OnTouchListener {
        private PointF last;

        private PrivateOnTouchListener() {
            last = new PointF();
        }

        /* synthetic */ PrivateOnTouchListener(TouchImageView x0, AnonymousClass1 x1) {
            this();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x004a, code lost:
            if (r1 != 6) goto L_0x00c6;
         */
        public boolean onTouch(View v, MotionEvent event) {
            TouchImageView.mScaleDetector.onTouchEvent(event);
            TouchImageView.mGestureDetector.onTouchEvent(event);
            PointF curr = new PointF(event.getX(), event.getY());
            if (TouchImageView.state == State.NONE || TouchImageView.state == State.DRAG || TouchImageView.state == State.FLING) {
                int action = event.getAction();
                if (action != 0) {
                    if (action != 1) {
                        if (action == 2) {
                            if (TouchImageView.state == State.DRAG) {
                                float deltaX = curr.x - last.x;
                                float deltaY = curr.y - last.y;
                                TouchImageView touchImageView = TouchImageView.this;
                                float fixTransX = touchImageView.getFixDragTrans(deltaX, (float) touchImageView.viewWidth, TouchImageView.getImageWidth());
                                TouchImageView touchImageView2 = TouchImageView.this;
                                TouchImageView.matrix.postTranslate(fixTransX, touchImageView2.getFixDragTrans(deltaY, (float) touchImageView2.viewHeight, TouchImageView.getImageHeight()));
                                TouchImageView.fixTrans();
                                last.set(curr.x, curr.y);
                            }
                        }
                    }
                    TouchImageView.setState(State.NONE);
                } else {
                    last.set(curr);
                    if (TouchImageView.fling != null) {
                        TouchImageView.fling.cancelFling();
                    }
                    TouchImageView.setState(State.DRAG);
                }
            }
            TouchImageView touchImageView3 = TouchImageView.this;
            touchImageView3.setImageMatrix(touchImageView3.matrix);
            if (TouchImageView.userTouchListener != null) {
                TouchImageView.userTouchListener.onTouch(v, event);
            }
            if (TouchImageView.touchImageViewListener != null) {
                TouchImageView.touchImageViewListener.onMove();
            }
            return true;
        }
    }

    private class ScaleListener extends SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        /* synthetic */ ScaleListener(TouchImageView x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onScaleBegin(ScaleGestureDetector detector) {
            TouchImageView.setState(State.ZOOM);
            return true;
        }

        public boolean onScale(ScaleGestureDetector detector) {
            TouchImageView.scaleImage((double) detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY(), true);
            if (TouchImageView.touchImageViewListener != null) {
                TouchImageView.touchImageViewListener.onMove();
            }
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector detector) {
            float targetZoom;
            super.onScaleEnd(detector);
            TouchImageView.setState(State.NONE);
            boolean animateToZoomBoundary = false;
            float targetZoom2 = TouchImageView.normalizedScale;
            if (TouchImageView.normalizedScale > TouchImageView.maxScale) {
                animateToZoomBoundary = true;
                targetZoom = TouchImageView.maxScale;
            } else if (TouchImageView.normalizedScale < TouchImageView.minScale) {
                animateToZoomBoundary = true;
                targetZoom = TouchImageView.minScale;
            } else {
                targetZoom = targetZoom2;
            }
            if (animateToZoomBoundary) {
                TouchImageView touchImageView = TouchImageView.this;
                DoubleTapZoom doubleTap = new DoubleTapZoom(targetZoom, (float) (touchImageView.viewWidth / 2), (float) (TouchImageView.viewHeight / 2), true);
                TouchImageView.compatPostOnAnimation(doubleTap);
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
            scale = scale2;
            focusX = focusX2;
            focusY = focusY2;
            scaleType = scaleType2;
        }
    }

    public TouchImageView(Context context2) {
        super(context2);
        sharedConstructing(context2);
    }

    public TouchImageView(Context context2, AttributeSet attrs) {
        super(context2, attrs);
        sharedConstructing(context2);
    }

    public TouchImageView(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        sharedConstructing(context2);
    }

    private void sharedConstructing(Context context2) {
        super.setClickable(true);
        context = context2;
        mScaleDetector = new ScaleGestureDetector(context2, new ScaleListener(this, null));
        mGestureDetector = new GestureDetector(context2, new GestureListener(this, null));
        matrix = new Matrix();
        prevMatrix = new Matrix();
        m = new float[9];
        normalizedScale = 1.0f;
        if (mScaleType == null) {
            mScaleType = ScaleType.FIT_CENTER;
        }
        minScale = 1.0f;
        maxScale = 3.0f;
        superMinScale = minScale * SUPER_MIN_MULTIPLIER;
        superMaxScale = maxScale * SUPER_MAX_MULTIPLIER;
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        setState(State.NONE);
        onDrawReady = false;
        super.setOnTouchListener(new PrivateOnTouchListener(this, null));
    }

    public void setOnTouchListener(OnTouchListener l) {
        userTouchListener = l;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener l) {
        touchImageViewListener = l;
    }

    public void setOnDoubleTapListener(OnDoubleTapListener l) {
        doubleTapListener = l;
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
            throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        } else if (type == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);
        } else {
            mScaleType = type;
            if (onDrawReady) {
                setZoom(this);
            }
        }
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    public boolean isZoomed() {
        return normalizedScale != 1.0f;
    }

    public RectF getZoomedRect() {
        if (mScaleType != ScaleType.FIT_XY) {
            PointF topLeft = transformCoordTouchToBitmap(0.0f, 0.0f, true);
            PointF bottomRight = transformCoordTouchToBitmap((float) viewWidth, (float) viewHeight, true);
            float w = (float) getDrawable().getIntrinsicWidth();
            float h = (float) getDrawable().getIntrinsicHeight();
            return new RectF(topLeft.x / w, topLeft.y / h, bottomRight.x / w, bottomRight.y / h);
        }
        throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
    }

    private void savePreviousImageValues() {
        Matrix matrix2 = matrix;
        if (matrix2 != null && viewHeight != 0 && viewWidth != 0) {
            matrix2.getValues(m);
            prevMatrix.setValues(m);
            prevMatchViewHeight = matchViewHeight;
            prevMatchViewWidth = matchViewWidth;
            prevViewHeight = viewHeight;
            prevViewWidth = viewWidth;
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", normalizedScale);
        bundle.putFloat("matchViewHeight", matchViewHeight);
        bundle.putFloat("matchViewWidth", matchViewWidth);
        bundle.putInt("viewWidth", viewWidth);
        bundle.putInt("viewHeight", viewHeight);
        matrix.getValues(m);
        bundle.putFloatArray("matrix", m);
        bundle.putBoolean("imageRendered", imageRenderedAtLeastOnce);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable state2) {
        if (state2 instanceof Bundle) {
            Bundle bundle = (Bundle) state2;
            normalizedScale = bundle.getFloat("saveScale");
            m = bundle.getFloatArray("matrix");
            prevMatrix.setValues(m);
            prevMatchViewHeight = bundle.getFloat("matchViewHeight");
            prevMatchViewWidth = bundle.getFloat("matchViewWidth");
            prevViewHeight = bundle.getInt("viewHeight");
            prevViewWidth = bundle.getInt("viewWidth");
            imageRenderedAtLeastOnce = bundle.getBoolean("imageRendered");
            super.onRestoreInstanceState(bundle.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(state2);
    }
    public void onDraw(Canvas canvas) {
        onDrawReady = true;
        imageRenderedAtLeastOnce = true;
        ZoomVariables zoomVariables = delayedZoomVariables;
        if (zoomVariables != null) {
            setZoom(zoomVariables.scale, delayedZoomVariables.focusX, delayedZoomVariables.focusY, delayedZoomVariables.scaleType);
            delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        savePreviousImageValues();
    }

    public float getMaxZoom() {
        return maxScale;
    }

    public void setMaxZoom(float max) {
        maxScale = max;
        superMaxScale = maxScale * SUPER_MAX_MULTIPLIER;
    }

    public float getMinZoom() {
        return minScale;
    }

    public float getCurrentZoom() {
        return normalizedScale;
    }

    public void setMinZoom(float min) {
        minScale = min;
        superMinScale = minScale * SUPER_MIN_MULTIPLIER;
    }

    public void resetZoom() {
        normalizedScale = 1.0f;
        fitImageToView();
    }

    public void setZoom(float scale) {
        setZoom(scale, 0.5f, 0.5f);
    }

    public void setZoom(float scale, float focusX, float focusY) {
        setZoom(scale, focusX, focusY, mScaleType);
    }

    public void setZoom(float scale, float focusX, float focusY, ScaleType scaleType) {
        if (!onDrawReady) {
            ZoomVariables zoomVariables = new ZoomVariables(scale, focusX, focusY, scaleType);
            delayedZoomVariables = zoomVariables;
            return;
        }
        if (scaleType != mScaleType) {
            setScaleType(scaleType);
        }
        resetZoom();
        scaleImage((double) scale, (float) (viewWidth / 2), (float) (viewHeight / 2), true);
        matrix.getValues(m);
        m[2] = -((getImageWidth() * focusX) - (((float) viewWidth) * 0.5f));
        m[5] = -((getImageHeight() * focusY) - (((float) viewHeight) * 0.5f));
        matrix.setValues(m);
        fixTrans();
        setImageMatrix(matrix);
    }

    public void setZoom(TouchImageView img) {
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
        PointF point = transformCoordTouchToBitmap((float) (viewWidth / 2), (float) (viewHeight / 2), true);
        point.x /= (float) drawableWidth;
        point.y /= (float) drawableHeight;
        return point;
    }

    public void setScrollPosition(float focusX, float focusY) {
        setZoom(normalizedScale, focusX, focusY);
    }


    public void fixTrans() {
        matrix.getValues(m);
        float[] fArr = m;
        float transX = fArr[2];
        float transY = fArr[5];
        float fixTransX = getFixTrans(transX, (float) viewWidth, getImageWidth());
        float fixTransY = getFixTrans(transY, (float) viewHeight, getImageHeight());
        if (fixTransX != 0.0f || fixTransY != 0.0f) {
            matrix.postTranslate(fixTransX, fixTransY);
        }
    }


    public void fixScaleTrans() {
        fixTrans();
        matrix.getValues(m);
        float imageWidth = getImageWidth();
        int i = viewWidth;
        if (imageWidth < ((float) i)) {
            m[2] = (((float) i) - getImageWidth()) / 2.0f;
        }
        float imageHeight = getImageHeight();
        int i2 = viewHeight;
        if (imageHeight < ((float) i2)) {
            m[5] = (((float) i2) - getImageHeight()) / 2.0f;
        }
        matrix.setValues(m);
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


    public float getFixDragTrans(float delta, float viewSize, float contentSize) {
        if (contentSize <= viewSize) {
            return 0.0f;
        }
        return delta;
    }


    public float getImageWidth() {
        return matchViewWidth * normalizedScale;
    }


    public float getImageHeight() {
        return matchViewHeight * normalizedScale;
    }
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
        viewWidth = setViewSize(widthMode, widthSize, drawableWidth);
        viewHeight = setViewSize(heightMode, heightSize, drawableHeight);
        setMeasuredDimension(viewWidth, viewHeight);
        fitImageToView();
    }

    private void fitImageToView() {
        float scaleY;
        float scaleX;
        Drawable drawable = getDrawable();
        if (drawable != null && drawable.getIntrinsicWidth() != 0 && drawable.getIntrinsicHeight() != 0 && matrix != null && prevMatrix != null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            float scaleX2 = ((float) viewWidth) / ((float) drawableWidth);
            float scaleY2 = ((float) viewHeight) / ((float) drawableHeight);
            int i = AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[mScaleType.ordinal()];
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
                        throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
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
            int i2 = viewWidth;
            float redundantXSpace = ((float) i2) - (((float) drawableWidth) * scaleX);
            int i3 = viewHeight;
            float redundantYSpace = ((float) i3) - (((float) drawableHeight) * scaleY);
            matchViewWidth = ((float) i2) - redundantXSpace;
            matchViewHeight = ((float) i3) - redundantYSpace;
            if (isZoomed() || imageRenderedAtLeastOnce) {
                if (prevMatchViewWidth == 0.0f || prevMatchViewHeight == 0.0f) {
                    savePreviousImageValues();
                }
                prevMatrix.getValues(m);
                float[] fArr = m;
                float f = matchViewWidth / ((float) drawableWidth);
                float f2 = normalizedScale;
                fArr[0] = f * f2;
                fArr[4] = (matchViewHeight / ((float) drawableHeight)) * f2;
                float transX = fArr[2];
                float transY = fArr[5];
                float prevActualWidth = prevMatchViewWidth * f2;
                translateMatrixAfterRotate(2, transX, prevActualWidth, getImageWidth(), prevViewWidth, viewWidth, drawableWidth);
                float prevActualHeight = prevMatchViewHeight * normalizedScale;
                translateMatrixAfterRotate(5, transY, prevActualHeight, getImageHeight(), prevViewHeight, viewHeight, drawableHeight);
                matrix.setValues(m);
            } else {
                matrix.setScale(scaleX, scaleY);
                matrix.postTranslate(redundantXSpace / 2.0f, redundantYSpace / 2.0f);
                normalizedScale = 1.0f;
            }
            fixTrans();
            setImageMatrix(matrix);
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
            float[] fArr = m;
            fArr[axis] = (((float) viewSize) - (((float) drawableSize) * fArr[0])) * 0.5f;
        } else if (trans > 0.0f) {
            m[axis] = -((imageSize - ((float) viewSize)) * 0.5f);
        } else {
            m[axis] = -((((Math.abs(trans) + (((float) prevViewSize) * 0.5f)) / prevImageSize) * imageSize) - (((float) viewSize) * 0.5f));
        }
    }


    public void setState(State state2) {
        state = state2;
    }

    public boolean canScrollHorizontallyFroyo(int direction) {
        return canScrollHorizontally(direction);
    }

    public boolean canScrollHorizontally(int direction) {
        matrix.getValues(m);
        float x = m[2];
        if (getImageWidth() < ((float) viewWidth)) {
            return false;
        }
        if (x >= -1.0f && direction < 0) {
            return false;
        }
        if (Math.abs(x) + ((float) viewWidth) + 1.0f < getImageWidth() || direction <= 0) {
            return true;
        }
        return false;
    }


    public void scaleImage(double deltaScale, float focusX, float focusY, boolean stretchImageToSuper) {
        float upperScale;
        float lowerScale;
        if (stretchImageToSuper) {
            lowerScale = superMinScale;
            upperScale = superMaxScale;
        } else {
            lowerScale = minScale;
            upperScale = maxScale;
        }
        float origScale = normalizedScale;
        double d = (double) normalizedScale;
        Double.isNaN(d);
        normalizedScale = (float) (d * deltaScale);
        float f = normalizedScale;
        if (f > upperScale) {
            normalizedScale = upperScale;
            deltaScale = (double) (upperScale / origScale);
        } else if (f < lowerScale) {
            normalizedScale = lowerScale;
            deltaScale = (double) (lowerScale / origScale);
        }
        matrix.postScale((float) deltaScale, (float) deltaScale, focusX, focusY);
        fixScaleTrans();
    }


    public PointF transformCoordTouchToBitmap(float x, float y, boolean clipToBitmap) {
        matrix.getValues(m);
        float origW = (float) getDrawable().getIntrinsicWidth();
        float origH = (float) getDrawable().getIntrinsicHeight();
        float[] fArr = m;
        float transX = fArr[2];
        float finalX = ((x - transX) * origW) / getImageWidth();
        float finalY = ((y - fArr[5]) * origH) / getImageHeight();
        if (clipToBitmap) {
            finalX = Math.min(Math.max(finalX, 0.0f), origW);
            finalY = Math.min(Math.max(finalY, 0.0f), origH);
        }
        return new PointF(finalX, finalY);
    }


    public PointF transformCoordBitmapToTouch(float bx, float by) {
        matrix.getValues(m);
        return new PointF(m[2] + (getImageWidth() * (bx / ((float) getDrawable().getIntrinsicWidth()))), m[5] + (getImageHeight() * (by / ((float) getDrawable().getIntrinsicHeight()))));
    }


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
        matrix.getValues(n);
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
