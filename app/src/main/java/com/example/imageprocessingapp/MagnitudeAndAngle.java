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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class MagnitudeAndAngle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnitude_and_angle);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Magnitude and Angle
        Mat src = new Mat();
        Mat dx = new Mat();
        Mat dy = new Mat();
        Mat magnitude = new Mat();
        Mat angle = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);

        Imgproc.Sobel(src, dx, CvType.CV_64F, 1, 0, 3);
        Imgproc.Sobel(src, dy, CvType.CV_64F, 0, 1, 3);
        Core.cartToPolar(dx, dy, magnitude, angle, true);

        Core.add(magnitude, new Scalar(128), magnitude);
        Core.add(angle, new Scalar(128), angle);

        Bitmap bitmap_mag = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap_angle = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);

        magnitude.convertTo(magnitude, src.type());
        angle.convertTo(angle, src.type());

        Utils.matToBitmap(magnitude, bitmap_mag);

        Utils.matToBitmap(angle, bitmap_angle);

        ImageView img1 = (ImageView) findViewById(R.id.img);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.magnitude);
        img2.setImageBitmap(bitmap_mag);
        ImageView img3 = (ImageView) findViewById(R.id.angle);
        img3.setImageBitmap(bitmap_angle);

        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_mag, "Magnitude");
                SaveBitmap(bitmap_angle, "Angle");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }
}