package com.example.imageprocessingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class AddSaltAndPepperNoise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_salt_and_pepper_noise);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Add Salt-And-Pepper Noise
         */

        Mat src = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);

        Mat noise = new Mat(src.rows(), src.cols(), src.type());

        Core.randu(noise, 0, 255);

        for(int i = 0; i < src.rows(); i++) {
            for(int j = 0; j < src.cols(); j++) {
                double temp = noise.get(i,j)[0];
                if(temp > 250) {
                    src.put(i, j, 255);
                }
                if(temp < 5) {
                    src.put(i, j, 0);
                }
            }
        }

        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src, bitmap_dst);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_dst);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Add Salt-And-Pepper Noise");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}