package com.example.mediapipeposetracking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//class LooperThread extends Thread{
//    public Handler mHandler;
//    public ImageView imageView;
//
//    public LooperThread(ImageView imageView) {
//        this.imageView = imageView;
//    }
//
//    public void sendMessage(int index) {
//        if (mHandler != null) { // mHandler가 null이 아닌 경우에만 메시지 전송
//            Message msg = mHandler.obtainMessage();
//            msg.what = index;
//            mHandler.sendMessage(msg);
//        } else {
//            Log.e("LooperThread", "mHandler is null");
//        }
//    }
//
//    @Override
//    public void run() {
//        Looper.prepare();
//        mHandler = new Handler(Looper.myLooper()){
//
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                int resid = msg.what;
//                imageView.setImageResource(resid);
//                super.handleMessage(msg);
//                // 메세지 처리
//            }
//        };
//        Looper.loop();
//    }
//
//}

// **LooperThread 나중에 사용할거 같음 일단 삭제 안하고 나중에 다시 학습하기

public class explainText extends AppCompatActivity {
//    private LooperThread looperThread;
    private ImageView imageView;
    private Button homeBtn;
    private final int[] drawables = {
            R.drawable.squatimg,
            R.drawable.benchimg,
            R.drawable.deadimg
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain);

        Intent preIntent = getIntent();
        Intent intent = new Intent(this, MainActivity.class);
        Button btnFront = findViewById(R.id.front);
        Button btnBack = findViewById(R.id.back);
        TextView textView = findViewById(R.id.text_view);
        homeBtn = findViewById(R.id.home);

        Intent homeInt = new Intent(this,home.class);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity((homeInt));
            }
        });

        int data = preIntent.getIntExtra("ex",-1);
        String bInfo = preIntent.getStringExtra("info");
        textView.setText(bInfo);
        intent.putExtra("ex",data);
        intent.putExtra("info",bInfo);

        int resId = preIntent.getIntExtra("resId",0);
        imageView = findViewById(R.id.exIMG);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(drawables[resId]);
            }
        });


//        looperThread = new LooperThread(imageView); // LooperThread 인스턴스 생성
//        looperThread.start(); // LooperThread 시작
//        looperThread.sendMessage(drawables[resId]); // LooperThread로 이미지 전송

        btnFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cameraSet","Front");
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cameraSet","Back");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
}
