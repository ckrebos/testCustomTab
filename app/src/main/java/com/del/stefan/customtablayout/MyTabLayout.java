package com.del.stefan.customtablayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by stefan on 3/29/17.
 */

public class MyTabLayout extends TabLayout {

    private MyTabSettings settings;

    private int height = 0;

    private int width = 0;

    private Path clipPath;

    public MyTabLayout(Context context) {
        super(context);
        init(context, null);
    }


    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs) {
        settings = new MyTabSettings(context, attrs);
        settings.setElevation(ViewCompat.getElevation(this));
    }

    private Path createClipPath() {
        final Path path = new Path();

        float arcHeight = settings.getArcHeight();

        switch (settings.getPosition()) {
            case MyTabSettings.POSITION_BOTTOM: {
                if (settings.isCropInside()) {
                    path.moveTo(0, 0);
                    path.lineTo(0, height);
                    path.quadTo(width / 2, height - 2 * arcHeight, width, height);
                    path.lineTo(width, 0);
                    path.close();
                } else {
                    path.moveTo(0, 0);
                    path.lineTo(0, height - arcHeight);
                    path.quadTo(width / 2, height + arcHeight, width, height - arcHeight);
                    path.lineTo(width, 0);
                    path.close();
                }
                break;
            }
        }

        return path;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            calculateLayout();
        }
    }

    private void calculateLayout() {
        if (settings == null) {
            return;
        }
        height = getMeasuredHeight();
        width = getMeasuredWidth();
        if (width > 0 && height > 0) {

            clipPath = createClipPath();
            ViewCompat.setElevation(this, settings.getElevation());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !settings.isCropInside()) {
                ViewCompat.setElevation(this, settings.getElevation());
                setOutlineProvider(new ViewOutlineProvider() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setConvexPath(clipPath);
                    }
                });
            }
        }
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();

        canvas.clipPath(clipPath);
        super.dispatchDraw(canvas);

        canvas.restore();
    }
}
