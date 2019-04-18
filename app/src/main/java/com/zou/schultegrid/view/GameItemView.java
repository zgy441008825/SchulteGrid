package com.zou.schultegrid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.zou.schultegrid.R;

public class GameItemView extends View {

    private static final String TAG = "GameItemView";

    private int textSize = 20;

    private int textColor = Color.WHITE;

    private int strokeColor = Color.GREEN;

    private int strokeWidth = 1;

    private Bitmap textViewBgMap;

    private String drawText = "";

    private int width, height;

    private TextPaint paint;

    private Matrix matrix;

    public GameItemView(Context context) {
        this(context, null);
    }

    public GameItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public GameItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GameItemView);
            textSize = typedArray.getDimensionPixelSize(R.styleable.GameItemView_textSize, 20);
            textColor = typedArray.getColor(R.styleable.GameItemView_textColor, Color.WHITE);
            drawText = typedArray.getString(R.styleable.GameItemView_text);
            strokeWidth = typedArray.getInteger(R.styleable.GameItemView_strokeWidth, 1);
            strokeColor = typedArray.getColor(R.styleable.GameItemView_strokeColor, Color.GREEN);
            typedArray.recycle();
        }
        paint = new TextPaint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        textViewBgMap = BitmapFactory.decodeResource(getResources(), R.drawable.img_game_item_bg);
        float scaleX = (float) width / textViewBgMap.getWidth();
        float scaleY = (float) height / textViewBgMap.getHeight();
        matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawBitmap(textViewBgMap, matrix, paint);
        canvas.restore();
        float textWidth = paint.measureText(drawText);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float y = (height - (metrics.descent + metrics.ascent)) / 2;
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawText(drawText, (width - textWidth) / 2, y, paint);
    }

    public String getDrawText() {
        return drawText;
    }

    public void setDrawText(String drawText) {
        this.drawText = drawText;
        invalidate();
    }
}
