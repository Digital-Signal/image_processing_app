package com.example.imageprocessingapp;

import static org.opencv.core.CvType.CV_32F;

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

public class Sharpen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharpen);

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
         * Sharpen
         */
        Mat src = new Mat();
        Mat dst = new Mat();
        Mat kernel = new Mat(3, 3, CV_32F);
        double[][] data = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };

        Utils.bitmapToMat(bitmap, src);
        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                kernel.put(i, j, data[i][j]);
            }
        }
        Imgproc.filter2D(src, dst, src.depth(), kernel);

        Utils.matToBitmap(dst, bitmap_dst);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_dst);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Sharpen");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}
