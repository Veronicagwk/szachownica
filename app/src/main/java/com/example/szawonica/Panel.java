package com.example.szawonica;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import static java.lang.Math.min;

public class Panel extends View {

    private final Paint jasnePole = new Paint();
    private final Paint ciemnePole = new Paint();

    private boolean inneKolory = false;

    public Panel(Context context) {
        super(context);
        ustawKolory(false);
    }

    public void ustawKolory(boolean zmiana) {
        inneKolory = zmiana;

        if (zmiana) {
            jasnePole.setColor(Color.CYAN);
            ciemnePole.setColor(Color.BLUE);
        } else {
            jasnePole.setColor(Color.WHITE);
            ciemnePole.setColor(Color.BLACK);
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);

        float szerokosc = getWidth();
        float wysokosc = getHeight();

        float bokPlanszy = min(szerokosc, wysokosc);
        float bokPola = bokPlanszy / 8f;

        float startX = (szerokosc - bokPlanszy) / 2f;
        float startY = (wysokosc - bokPlanszy) / 2f;

        for (int wiersz = 0; wiersz < 8; wiersz++) {
            for (int kolumna = 0; kolumna < 8; kolumna++) {

                float lewo = startX + kolumna * bokPola;
                float gora = startY + wiersz * bokPola;

                Paint pedzel;

                if ((wiersz + kolumna) % 2 == 0) {
                    pedzel = jasnePole;
                } else {
                    pedzel = ciemnePole;
                }

                canvas.drawRect(
                        lewo,
                        gora,
                        lewo + bokPola,
                        gora + bokPola,
                        pedzel
                );
            }
        }
    }
}