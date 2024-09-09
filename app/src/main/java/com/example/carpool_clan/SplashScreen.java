package com.example.carpool_clan;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player = MediaPlayer.create(this, R.raw.song);
        player.start();
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, WelcomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }, 5000);

    }
    protected void onPause() {
        super.onPause();
        player.release();
        finish();
    }
}
