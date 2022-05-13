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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartoonEffectPipeline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_effect_pipeline);

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
         * Cartoon Effect (Pipeline)
         */

        Mat src = new Mat();
        Mat blur = new Mat();
        Mat color = new Mat();
        Mat dst = new Mat();
        Mat edge = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGRA2RGB);

        Imgproc.bilateralFilter(src, blur, 40, 100, 100);
        Imgproc.pyrMeanShiftFiltering(blur, color, 5, 5);

        Imgproc.GaussianBlur(src, src, new Size(5,5), 0, 0);
        edge = src.clone();

        Imgproc.cvtColor(edge, edge, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(edge, edge, 100, 150);

        Mat BB = new Mat();
        Mat GG = new Mat();
        Mat RR = new Mat();

        Core.extractChannel(color , BB, 0);
        Core.extractChannel(color , GG, 1);
        Core.extractChannel(color , RR, 2);

        double pixel_intensity;
        for(int i = 0; i < edge.rows(); i++) {
            for(int j = 0; j < edge.cols(); j++) {
                pixel_intensity = edge.get(i,j)[0];
                if(pixel_intensity > 10) {
                    BB.put(i, j, 0);
                    GG.put(i, j, 0);
                    RR.put(i, j, 0);
                }
            }
        }

        List<Mat> BGR = new ArrayList<Mat>();
        BGR.add(BB);
        BGR.add(GG);
        BGR.add(RR);
        Core.merge(BGR, dst);

        Imgproc.cvtColor(dst, dst, Imgproc.COLOR_BGR2RGB);
        Bitmap bitmap_dst = Bitmap.createBitmap(dst.cols(), dst.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst, bitmap_dst);

        ImageView img1 = (ImageView) findViewById(R.id.original1);
        img1.setImageBitmap(bitmap);

        ImageView img2 = (ImageView) findViewById(R.id.effect1);
        img2.setImageBitmap(bitmap_dst);

        Button button_save = (Button) findViewById(R.id.save);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Cartoon Effect (Pipeline)");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }


}