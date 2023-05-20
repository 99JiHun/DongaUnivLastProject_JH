package com.example.mediapipeposetracking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class explainText extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explain);
        Intent preIntent = getIntent();
        Intent intent = new Intent(this, MainActivity.class);
        Button btnFront = findViewById(R.id.front);
        Button btnBack = findViewById(R.id.back);
        TextView textView = findViewById(R.id.text_view);

        int data = preIntent.getIntExtra("ex",-1);
        String bInfo = preIntent.getStringExtra("info");
        textView.setText(bInfo);
        intent.putExtra("ex",data);
        intent.putExtra("info",bInfo);

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
