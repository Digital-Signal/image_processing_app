package com.example.imageprocessingapp;

import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8UC3;

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

public class EdgeDetectionRoberts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edge_detection_roberts);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Edge Detection - Roberts

        Mat src = new Mat();
        Mat dstx = new Mat();
        Mat dsty = new Mat();
        Mat kernelx = new Mat(3, 3, CV_32F);
        Mat kernely = new Mat(3, 3, CV_32F);
        Mat magnitude = new Mat();

        double[][] datax  = {
                {1, 0, 0},
                {0, -1, 0},
                {0, 0, 0}
        };

        double[][] datay  = {
                {0, 1, 0},
                {-1, 0, 0},
                {0, 0, 0}
        };

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                kernelx.put(i, j, datax[i][j]);
                kernely.put(i, j, datay[i][j]);
            }
        }

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGB2GRAY);

        src.convertTo(src, kernelx.type());

        Imgproc.filter2D(src, dstx, src.depth(), kernelx);
        Imgproc.filter2D(src, dsty, src.depth(), kernely);

        Core.magnitude(dstx, dsty, magnitude);

        magnitude.convertTo(magnitude, CV_8UC3);

        Bitmap bitmap_effect = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(magnitude, bitmap_effect);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_effect);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_effect, "Edge Detection - Roberts");
            }
        });
    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}