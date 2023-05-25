package com.example.mediapipeposetracking;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.io.Serializable;




public class home extends AppCompatActivity {

    private String[] dataInfo;
    private Button[] buttons = new Button[3];


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        buttons[0] = findViewById(R.id.squat);
        buttons[1] = findViewById(R.id.bench);
        buttons[2] = findViewById(R.id.deadlift);

        dataInfo = getResources().getStringArray(R.array.exerciseInfo);

        Intent intent = new Intent(this, explainText.class);




        for(int i=0;i<dataInfo.length;i++){
            final int index = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent.putExtra("ex",index);
                    intent.putExtra("info",dataInfo[index]);
                    intent.putExtra("resId",index);
                    //여기에 버튼에 따른 drawable 이미지 할당 필요함

                    startActivity(intent);
                }
            });
        }

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
