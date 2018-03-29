package com.icode.jiling.na517demo_mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动页
 * ted by jiling on 2018/3/29.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Timer timer = new Timer();
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                try{
                     runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            finish();
                            overridePendingTransition(R.anim.fade_in,0);
                        }
                    });

                }catch (Exception e){
                    timer.cancel();
                }
            }
        };
        timer.schedule(task,2000);
    }
}
