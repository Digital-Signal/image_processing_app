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
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class Thresholding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thresholding);

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
         * Thresholding
         */
        Mat src = new Mat();
        Mat dst1 = new Mat();
        Mat dst2 = new Mat();
        Mat dst3 = new Mat();
        Mat dst4 = new Mat();

        Utils.bitmapToMat(bitmap, src);

        Bitmap bitmap_dst1 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap_dst2 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap_dst3 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap_dst4 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);

        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);

        Imgproc.threshold(src, dst1, 127, 255, Imgproc.THRESH_BINARY);
        Imgproc.threshold(src, dst2, 127, 255, Imgproc.THRESH_TOZERO);
        Imgproc.threshold(src, dst3, 127, 255, Imgproc.THRESH_TRUNC);
        Imgproc.adaptiveThreshold(src, dst4, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 25, 5);

        Utils.matToBitmap(dst1, bitmap_dst1);
        Utils.matToBitmap(dst2, bitmap_dst2);
        Utils.matToBitmap(dst3, bitmap_dst3);
        Utils.matToBitmap(dst4, bitmap_dst4);

        ImageView img = (ImageView) findViewById(R.id.initial1);
        img.setImageBitmap(bitmap);
        ImageView img1 = (ImageView) findViewById(R.id.t1);
        img1.setImageBitmap(bitmap_dst1);
        ImageView img2 = (ImageView) findViewById(R.id.t2);
        img2.setImageBitmap(bitmap_dst2);
        ImageView img3 = (ImageView) findViewById(R.id.t3);
        img3.setImageBitmap(bitmap_dst3);
        ImageView img4 = (ImageView) findViewById(R.id.t4);
        img4.setImageBitmap(bitmap_dst4);

        Button button_save = (Button) findViewById(R.id.button_save_img);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst1, "Thresholding");
                SaveBitmap(bitmap_dst2, "Thresholding");
                SaveBitmap(bitmap_dst3, "Thresholding");
                SaveBitmap(bitmap_dst4, "Thresholding");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}