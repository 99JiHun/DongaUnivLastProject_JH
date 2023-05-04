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
        Button button = findViewById(R.id.apt);
        Intent preIntent = getIntent();
        Intent intent = new Intent(this, MainActivity.class);

        TextView textView = findViewById(R.id.text_view);

        int data = preIntent.getIntExtra("ex",-1);
        String bInfo = preIntent.getStringExtra("info");
        textView.setText(bInfo);
        intent.putExtra("ex",data);
        intent.putExtra("info",bInfo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
