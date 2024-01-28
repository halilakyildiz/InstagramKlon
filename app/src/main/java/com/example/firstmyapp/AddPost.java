package com.example.firstmyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.firstmyapp.databinding.ActivityAddPostBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class AddPost extends AppCompatActivity {
    ActivityAddPostBinding design;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    Bitmap selected_;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        design=ActivityAddPostBinding.inflate(getLayoutInflater());
        setContentView(design.getRoot());
        registerLauncher();
        database=new Database(AddPost.this);
        design.selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
                    if(ContextCompat.checkSelfPermission(AddPost.this, Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(AddPost.this,Manifest.permission.READ_MEDIA_IMAGES)){
                            Snackbar.make(view,"Galeriye erişim için izniniz gerekli",Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                                }
                            }).show();
                        }
                        else{
                            // request permission
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                        }
                    }
                    else{
                        // gallery
                        Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activityResultLauncher.launch(intentToGallery);
                    }
                }
                else{
                    if(ContextCompat.checkSelfPermission(AddPost.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(AddPost.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                            Snackbar.make(view,"Galeriye erişim için izniniz gerekli",Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                }
                            }).show();
                        }
                        else{
                            // request permission
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }
                    else{
                        // gallery
                        Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activityResultLauncher.launch(intentToGallery);
                    }
                }

            }
        });
        design.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap image = makeSmallerImage(selected_,600);
                String userName =design.tbUserName.getText().toString();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,50,outputStream);
                byte[] byteImage = outputStream.toByteArray();
                database.SavePost(byteImage,userName);
            }
        });
        design.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddPost.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private Bitmap makeSmallerImage(Bitmap image,int maxSize){
        int width = design.selectedImage.getWidth();
        int height=design.selectedImage.getHeight();

        float bitmapRatio = (float)width / (float)height;
        if(bitmapRatio>1){
            // landscape image
            width=maxSize;
            height=(int)(width/bitmapRatio);
        }
        else{
            // portrait image
            height=maxSize;
            width=(int)(height*bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }
    private void registerLauncher(){
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()==RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult!=null){
                        Uri imageData = intentFromResult.getData();
                        try {
                            if(Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(AddPost.this.getContentResolver(), imageData);
                                selected_ = ImageDecoder.decodeBitmap(source);
                                design.selectedImage.setImageBitmap(selected_);
                            }
                            else{
                                selected_=MediaStore.Images.Media.getBitmap(AddPost.this.getContentResolver(),imageData);
                                design.selectedImage.setImageBitmap(selected_);
                            }
                        }catch (Exception e){
                            Toast.makeText(AddPost.this,"Seçilen resim işlenemedi! :"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result){
                    //permission granted
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }
                else{
                    //permission denied
                    Toast.makeText(AddPost.this,"İzin Gerekli",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}