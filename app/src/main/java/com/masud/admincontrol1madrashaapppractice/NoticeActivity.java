package com.masud.admincontrol1madrashaapppractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class NoticeActivity extends AppCompatActivity {
    private MaterialCardView galleryImagePick;
    private ImageView imagePost;
    private AppCompatButton submitBtn;
    TextInputLayout textInputLayout;

    private DatabaseReference database;

    String postTitle;

    Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance().getReference().child("Notice");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        galleryImagePick = findViewById(R.id.imagePick);
        imagePost = findViewById(R.id.imagePost);
        submitBtn = findViewById(R.id.submitBtn);
        textInputLayout= findViewById(R.id.textInputLayout);
        galleryImagePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryImageIntent, 1);
            }

        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 postTitle = textInputLayout.getEditText().getText().toString().trim();
                if (postTitle.isEmpty()){
                    textInputLayout.getEditText().setError("Write here");
                    textInputLayout.getEditText().requestFocus();
                } else if (imageBitmap == null) {
                    gotoDatabase ();

                }


            }
        });

    }

    private void gotoDatabase() {
        String uniqueId = database.push().getKey();
        NoticeData noticeData = new NoticeData(postTitle,"4 sep 2022","4:45 pm",uniqueId,"");
        database.child(uniqueId).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(NoticeActivity.this, "Note Create", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK);{
           Uri imageUri =  data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePost.setImageURI(imageUri);

        }
    }
}