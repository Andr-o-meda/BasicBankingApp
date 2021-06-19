package com.example.sparks;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

public class Splash_screen extends AppCompatActivity {
    TextView t1;
    Animation rotate_anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Intent i3 = getIntent();
        if(i3.getBooleanExtra("splash",false)){
            Intent i4 = new Intent(this, MainActivity.class);
            startActivity(i4);
            finish();
        }
        t1 = findViewById(R.id.t121);
        rotate_anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        t1.startAnimation(rotate_anim);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(Splash_screen.this, MainActivity.class);
            i.putExtra("splash",false);
            startActivity(i);
            finish();
        }, 6*1000);
    }
}
