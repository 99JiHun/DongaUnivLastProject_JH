package com.example.mediapipeposetracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;


public class home extends AppCompatActivity {
//    public static class ButtonInfo implements Serializable {
//        private int buttonId;
//        private String description;
//
//        public ButtonInfo(int buttonId, String description) {
//            this.buttonId = buttonId;
//            this.description = description;
//        }
//
//        public int getButtonId() {
//            return buttonId;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        Button button1 = findViewById(R.id.squat);
        Button button2 = findViewById(R.id.bench);
        Button button3 = findViewById(R.id.deadlift);
        String buInfo1 = "squatInfo";
        String buInfo2 = "benchInfo";
        String buInfo3 = "deadliftInfo";
//        ButtonInfo buInfo1 = new ButtonInfo(R.id.squat,"squatInfo");
//        ButtonInfo buInfo2 = new ButtonInfo(R.id.bench,"benchInfo");
//        ButtonInfo buInfo3 = new ButtonInfo(R.id.deadlift,"deadliftInfo");

        Intent intent = new Intent(this, explainText.class);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("ex",0);
                intent.putExtra("info",buInfo1);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("ex",1);
                intent.putExtra("info",buInfo2);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("ex",2);
                intent.putExtra("info",buInfo3);
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
