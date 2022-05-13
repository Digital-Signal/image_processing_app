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
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OldPhotographPipeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_photograph_pipeline);

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
         * Old Photograph (Pipeline)
         */
        Mat src = new Mat();
        Mat dst = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);
        Imgproc.applyColorMap(src, dst, 1);

        Mat mat_HSV = new Mat();
        Imgproc.cvtColor(dst, mat_HSV, Imgproc.COLOR_RGB2HSV);
        Mat mat_H = new Mat();
        Mat mat_S = new Mat();
        Mat mat_V = new Mat();

        Core.extractChannel(mat_HSV , mat_H, 0);
        Core.extractChannel(mat_HSV , mat_S, 1);
        Core.extractChannel(mat_HSV , mat_V, 2);

        Core.multiply(mat_V, new Scalar(0.5), mat_V);
        Core.add(mat_V, new Scalar(15), mat_V);

        List<Mat> HSV = new ArrayList<Mat>();
        HSV.add(mat_H);
        HSV.add(mat_S);
        HSV.add(mat_V);
        Core.merge(HSV, dst);
        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_HSV2RGB);

        Imgproc.blur(dst, dst, new Size(15,15));
        Mat noise = dst.clone();
        Core.randn(noise, 0, 30);
        Core.add(dst, noise, dst);

        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst, bitmap_dst);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_dst);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Old Photograph (Pipeline)");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}