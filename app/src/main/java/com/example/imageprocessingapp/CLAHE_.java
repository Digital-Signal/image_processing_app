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
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CLAHE_ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clahe);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // CLAHE
        Mat src = new Mat();
        Mat dst = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2YCrCb);

        Mat Y = new Mat();
        Mat Cr = new Mat();
        Mat Cb = new Mat();

        Core.extractChannel(src , Y, 0);
        Core.extractChannel(src , Cr, 1);
        Core.extractChannel(src , Cb, 2);

        CLAHE clahe = Imgproc.createCLAHE();
        clahe.apply(Y, Y);

        List<Mat> YCrCb = new ArrayList<Mat>();
        YCrCb.add(Y);
        YCrCb.add(Cr);
        YCrCb.add(Cb);
        Core.merge(YCrCb, dst);

        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2RGB);
        Bitmap bitmap_dst = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888);

        Utils.matToBitmap(dst, bitmap_dst);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_dst);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "CLAHE");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}