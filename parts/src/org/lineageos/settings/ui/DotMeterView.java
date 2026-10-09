package org.lineageos.settings.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.lineageos.settings.R;

/** 24x7 dot grid from Shrinky. Last filled dot is red. Use for battery level / charge limit. */
public class DotMeterView extends View {
    private static final int COLS = 24, ROWS = 7;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float fraction = 0f;

    public DotMeterView(Context c) { super(c); }
    public DotMeterView(Context c, AttributeSet a) { super(c, a); }

    public void setFraction(float f) {
        fraction = Math.max(0f, Math.min(1f, f));
        invalidate();
    }

    @Override
    protected void onMeasure(int w, int h) {
        int width = MeasureSpec.getSize(w);
        setMeasuredDimension(width, Math.round(width * ROWS / (float) COLS));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cell = getWidth() / (float) COLS;
        int total = COLS * ROWS;
        int filled = Math.max(0, Math.min(total, Math.round(fraction * total)));
        int on = getContext().getColor(R.color.topex_dot_on);
        int off = getContext().getColor(R.color.topex_dot_off);
        int accent = getContext().getColor(R.color.topex_primary);
        for (int i = 0; i < total; i++) {
            int col = i / ROWS, row = i % ROWS;
            paint.setColor(i == filled - 1 ? accent : (i < filled ? on : off));
            canvas.drawCircle(cell * (col + 0.5f), cell * (row + 0.5f), cell * 0.27f, paint);
        }
    }
}
