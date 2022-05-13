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

public class CannyEdgeDetector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canny_edge_detector);

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
         * Canny Edge Detector
         */
        Mat image = new Mat();
        Mat edges = new Mat();

        Utils.bitmapToMat(bitmap, image);
        Bitmap bitmap_dst = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);

        Imgproc.Canny(image, edges, 150, 200);

        Utils.matToBitmap(edges, bitmap_dst);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_dst);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Canny Edge Detector");
            }
        });

    }
    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}