package com.medicinetake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.medicinetake.users_activities.UsersChoosing;

public class MainActivity extends AppCompatActivity {

    private static final int COUNT_DOWN = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= 21) {

            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorWelcomeScreen));

        }

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_text_welcome);
        findViewById(R.id.text_welcome_screen).setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), UsersChoosing.class);
                startActivity(intent);
                finish();
            }
        }, COUNT_DOWN);

    }
}