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

public class ColourMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_map);

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
         * Colour Map
         */
        Mat src = new Mat();
        Mat dst1 = new Mat();
        Mat dst5 = new Mat();
        Mat dst10 = new Mat();
        Mat dst17 = new Mat();
        Mat dst0 = new Mat();
        Mat dst11 = new Mat();
        Mat dst18 = new Mat();
        Mat dst19 = new Mat();
        Mat dst2 = new Mat();
        Mat dst20 = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);

        Imgproc.applyColorMap(src, dst1, 1);
        Imgproc.applyColorMap(src, dst5, 5);
        Imgproc.applyColorMap(src, dst10, 10);
        Imgproc.applyColorMap(src, dst17, 17);
        Imgproc.applyColorMap(src, dst0, 0);
        Imgproc.applyColorMap(src, dst11, 11);
        Imgproc.applyColorMap(src, dst18, 18);
        Imgproc.applyColorMap(src, dst19, 19);
        Imgproc.applyColorMap(src, dst2, 2);
        Imgproc.applyColorMap(src, dst20, 20);

        Bitmap bitmap1 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst1, bitmap1);
        Bitmap bitmap5 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst5, bitmap5);
        Bitmap bitmap10 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst10, bitmap10);
        Bitmap bitmap17 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst17, bitmap17);
        Bitmap bitmap0 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst0, bitmap0);
        Bitmap bitmap11 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst11, bitmap11);
        Bitmap bitmap18 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst18, bitmap18);
        Bitmap bitmap19 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst19, bitmap19);
        Bitmap bitmap2 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst2, bitmap2);
        Bitmap bitmap20 = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst20, bitmap20);

        ImageView img = (ImageView) findViewById(R.id.initial);
        img.setImageBitmap(bitmap);

        ImageView img1 = (ImageView) findViewById(R.id.a1);
        img1.setImageBitmap(bitmap1);
        ImageView img5 = (ImageView) findViewById(R.id.a5);
        img5.setImageBitmap(bitmap5);
        ImageView img10 = (ImageView) findViewById(R.id.a10);
        img10.setImageBitmap(bitmap10);
        ImageView img17 = (ImageView) findViewById(R.id.a17);
        img17.setImageBitmap(bitmap17);
        ImageView img0 = (ImageView) findViewById(R.id.a0);
        img0.setImageBitmap(bitmap0);
        ImageView img11 = (ImageView) findViewById(R.id.a11);
        img11.setImageBitmap(bitmap11);
        ImageView img18 = (ImageView) findViewById(R.id.a18);
        img18.setImageBitmap(bitmap18);
        ImageView img19 = (ImageView) findViewById(R.id.a19);
        img19.setImageBitmap(bitmap19);
        ImageView img2 = (ImageView) findViewById(R.id.a2);
        img2.setImageBitmap(bitmap2);
        ImageView img20 = (ImageView) findViewById(R.id.a20);
        img20.setImageBitmap(bitmap20);

        Button button_save = (Button) findViewById(R.id.save_images);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SaveBitmap(bitmap1, "Colour Map");
                SaveBitmap(bitmap5, "Colour Map");
                SaveBitmap(bitmap10, "Colour Map");
                SaveBitmap(bitmap17, "Colour Map");
                SaveBitmap(bitmap0, "Colour Map");
                SaveBitmap(bitmap11, "Colour Map");
                SaveBitmap(bitmap18, "Colour Map");
                SaveBitmap(bitmap19, "Colour Map");
                SaveBitmap(bitmap2, "Colour Map");
                SaveBitmap(bitmap20, "Colour Map");

            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }
}