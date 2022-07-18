package com.hikki.katamereka;

import android.animation.Animator;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView splash1,splash2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        lottie = findViewById(R.id.splashLottie);
        splash1 = findViewById(R.id.splash1);
        splash2 = findViewById(R.id.splash2);
        lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                splash1.animate().alpha(1);
                splash2.animate().alpha(1);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }
        },4000);
    }
}