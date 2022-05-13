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

public class YCbCr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ycb_cr);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // YCbCr

        Mat mat = new Mat();
        Mat mat_YCrCb = new Mat();

        Utils.bitmapToMat(bitmap, mat);
        Imgproc.cvtColor(mat, mat_YCrCb, Imgproc.COLOR_RGB2YCrCb);

        Bitmap bitmap_YCrCb = Bitmap.createBitmap(mat_YCrCb.cols(), mat_YCrCb.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat_YCrCb, bitmap_YCrCb);

        Mat mat_Y = new Mat();
        Mat mat_Cb = new Mat();
        Mat mat_Cr = new Mat();
        Bitmap bitmap_Y = Bitmap.createBitmap(mat_YCrCb.cols(), mat_YCrCb.rows(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap_Cr = Bitmap.createBitmap(mat_YCrCb.cols(), mat_YCrCb.rows(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap_Cb = Bitmap.createBitmap(mat_YCrCb.cols(), mat_YCrCb.rows(), Bitmap.Config.ARGB_8888);
        Core.extractChannel(mat_YCrCb , mat_Y, 0);
        Core.extractChannel(mat_YCrCb , mat_Cr, 1);
        Core.extractChannel(mat_YCrCb , mat_Cb, 2);
        Utils.matToBitmap(mat_Y, bitmap_Y);
        Utils.matToBitmap(mat_Cr, bitmap_Cr);
        Utils.matToBitmap(mat_Cb, bitmap_Cb);

        ImageView img1 = (ImageView) findViewById(R.id.original);
        img1.setImageBitmap(bitmap);

        ImageView img2 = (ImageView) findViewById(R.id.ycbcr);
        img2.setImageBitmap(bitmap_YCrCb);

        ImageView Y = (ImageView) findViewById(R.id.y);
        Y.setImageBitmap(bitmap_Y);

        ImageView Cb = (ImageView) findViewById(R.id.cb);
        Cb.setImageBitmap(bitmap_Cb);

        ImageView Cr = (ImageView) findViewById(R.id.cr);
        Cr.setImageBitmap(bitmap_Cr);

        Button button_save = (Button) findViewById(R.id.ButtonSave);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_YCrCb, "YCrCb");
                SaveBitmap(bitmap_Y, "Y");
                SaveBitmap(bitmap_Cb, "Cb");
                SaveBitmap(bitmap_Cr, "Cr");
            }
        });

    }


    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}