package com.example.szawonica;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Panel widokPlanszy;
    private boolean koloryZmienione;
    private SharedPreferences ustawienia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ustawienia = getSharedPreferences(
                "ustawienia_szachownicy",
                MODE_PRIVATE
        );

        koloryZmienione = ustawienia.getBoolean("kolory", false);

        LinearLayout ekran = new LinearLayout(this);
        ekran.setOrientation(LinearLayout.VERTICAL);

        ViewCompat.setOnApplyWindowInsetsListener(ekran, (v, windowInsets) -> {
            Insets marginesy = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout()
            );

            v.setPadding(
                    marginesy.left,
                    marginesy.top,
                    marginesy.right,
                    marginesy.bottom
            );

            return windowInsets;
        });

        widokPlanszy = new Panel(this);
        widokPlanszy.ustawKolory(koloryZmienione);

        ekran.addView(
                widokPlanszy,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                )
        );

        LinearLayout pasek = new LinearLayout(this);
        pasek.setOrientation(LinearLayout.HORIZONTAL);

        Button kolory = new Button(this);
        kolory.setText("Kolory");

        Button tworca = new Button(this);
        tworca.setText("Twórca");

        Button wyjscie = new Button(this);
        wyjscie.setText("Wyjście");

        pasek.addView(kolory,
                new LinearLayout.LayoutParams(0, -2, 1));

        pasek.addView(tworca,
                new LinearLayout.LayoutParams(0, -2, 1));

        pasek.addView(wyjscie,
                new LinearLayout.LayoutParams(0, -2, 1));

        ekran.addView(pasek);

        setContentView(ekran);
        ViewCompat.requestApplyInsets(ekran);

        kolory.setOnClickListener(v -> {
            String[] opcje = {
                    "Czarno-biała",
                    "Niebieska"
            };

            new AlertDialog.Builder(this)
                    .setTitle("Wybierz kolory")
                    .setItems(opcje, (dialog, wybor) -> {
                        koloryZmienione = wybor == 1;
                        widokPlanszy.ustawKolory(koloryZmienione);
                        zapiszKolory();

                        Toast.makeText(
                                this,
                                "Zmieniono kolory",
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .show();
        });

        tworca.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Twórca")
                    .setMessage("Weronika Gawlak")
                    .setPositiveButton("OK", null)
                    .show();
        });

        wyjscie.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int opcja = item.getItemId();

        if (opcja == R.id.opcja_zmien_kolory) {

            koloryZmienione = !koloryZmienione;
            widokPlanszy.ustawKolory(koloryZmienione);
            zapiszKolory();
            return true;

        } else if (opcja == R.id.opcja_reset) {

            koloryZmienione = false;
            widokPlanszy.ustawKolory(false);
            zapiszKolory();
            return true;

        } else if (opcja == R.id.opcja_info) {

            Toast.makeText(
                    this,
                    "Szachownica - Laboratorium 2",
                    Toast.LENGTH_LONG
            ).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void zapiszKolory() {
        ustawienia.edit()
                .putBoolean("kolory", koloryZmienione)
                .apply();
    }
}