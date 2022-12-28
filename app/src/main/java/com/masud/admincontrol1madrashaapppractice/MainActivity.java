package com.masud.admincontrol1madrashaapppractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {
    MaterialCardView noticeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noticeId = findViewById(R.id.noticeId);
        noticeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent noticeIdIntent = new Intent(MainActivity.this,NoticeActivity.class);
                startActivity(noticeIdIntent);

            }
        });
    }

}