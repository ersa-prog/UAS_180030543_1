package com.aa183.ErsaPutra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class splash extends AppCompatActivity {

    ImageView foto1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        foto1 = (ImageView) findViewById(R.id.foto1);
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent( splash.this , MainActivity.class ));
                    finish();
                }
            }
        };
        thread.start();
    }
}
