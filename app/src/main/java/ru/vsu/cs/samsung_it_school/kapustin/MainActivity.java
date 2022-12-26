package ru.vsu.cs.samsung_it_school.kapustin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import ru.vsu.cs.samsung_it_school.kapustin.Snake.Settings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Settings.screenWidth = dm.widthPixels;
        Settings.screenHeight = dm.heightPixels;

        setContentView(R.layout.activity_main);
    }
}