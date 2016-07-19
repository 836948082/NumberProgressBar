package com.runtai.numberprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private NumberProgressBar progress;
    private Button bt;
    private int progressbarstart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (NumberProgressBar) findViewById(R.id.progress);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressbarstart == 100){
                    progressbarstart = 0;
                }
                new Thread() {
                    public void run() {
                        while (true) {
                            if (progressbarstart >= 100) {
                                return;
                            }
                            doWork();
                            progressbarstart++;
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                            Log.e("progressbarstart",""+progressbarstart);
                            if (progressbarstart < 100) {
                                bt.setClickable(false);
                            } else {
                                bt.setClickable(true);
                            }
                        }
                    }
                }.start();
            }

            //模拟耗时操作
            private void doWork() {
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Log.e("设置",""+progressbarstart);
                progress.setProgress(progressbarstart);
            }
        }
    };
}
