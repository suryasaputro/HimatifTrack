package com.example.himatiftrack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

public class ShadowTextView extends AppCompatTextView {

    private Paint textPaint;
    private final String fullText = "Selamat Datang di HimatifTrack";
    private Typeface comfortaa;

    public ShadowTextView(Context context) {
        super(context);
        init(context);
    }

    public ShadowTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShadowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(getTextSize());

        // Load font Comfortaa dari res/font
        comfortaa = ResourcesCompat.getFont(context, R.font.comfortaa_bold);
        textPaint.setTypeface(comfortaa != null ? comfortaa : Typeface.DEFAULT_BOLD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float y = getBaseline();

        // Hitung total lebar teks
        String awal = "Selamat Datang di ";
        String himatif = "Himatif";
        String track = "Track";

        float widthAwal = textPaint.measureText(awal);
        float widthHimatif = textPaint.measureText(himatif);
        float widthTrack = textPaint.measureText(track);
        float totalWidth = widthAwal + widthHimatif + widthTrack;

        // Hitung titik X agar berada di tengah view
        float x = (getWidth() - totalWidth) / 2f;

        // Teks awal: tanpa shadow
        textPaint.setColor(getCurrentTextColor());
        textPaint.clearShadowLayer();
        canvas.drawText(awal, x, y, textPaint);
        x += widthAwal;

// Gambar "Himatif" dengan outline putih manual
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(3f); // tebal garis putih
        textPaint.setColor(Color.WHITE);
        textPaint.setShadowLayer(0, 0, 0, Color.TRANSPARENT); // tidak pakai shadow
        canvas.drawText(himatif, x, y, textPaint);

// Gambar isi "Himatif" biru di atas outline
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#111184"));
        canvas.drawText(himatif, x, y, textPaint);
        x += textPaint.measureText(himatif);

// Gambar "Track" dengan outline putih manual
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(3f);
        textPaint.setColor(Color.WHITE);
        canvas.drawText(track, x, y, textPaint);

// Gambar isi "Track" biru muda
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.parseColor("#4AA3DF"));
        canvas.drawText(track, x, y, textPaint);
    }
}
