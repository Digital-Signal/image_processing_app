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
import android.widget.SeekBar;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HSV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsv);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // HSV

        Mat src = new Mat();
        Mat dst = new Mat();

        Utils.bitmapToMat(bitmap, src);

        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src, bitmap_dst);
        ImageView img = (ImageView) findViewById(R.id.image_hsv);
        img.setImageBitmap(bitmap_dst);

        SeekBar seekBar1 = (SeekBar) findViewById(R.id.hue);
        SeekBar seekBar2 = (SeekBar) findViewById(R.id.sat);
        SeekBar seekBar3 = (SeekBar) findViewById(R.id.val);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekBar2.setProgress(0);
                seekBar3.setProgress(0);

                Mat mat_HSV = new Mat();
                Imgproc.cvtColor(src, mat_HSV, Imgproc.COLOR_RGB2HSV);
                Mat mat_H = new Mat();
                Mat mat_S = new Mat();
                Mat mat_V = new Mat();

                Core.extractChannel(mat_HSV , mat_H, 0);
                Core.extractChannel(mat_HSV , mat_S, 1);
                Core.extractChannel(mat_HSV , mat_V, 2);

                Core.add(mat_H, new Scalar(i), mat_H);

                List<Mat> HSV = new ArrayList<Mat>();
                HSV.add(mat_H);
                HSV.add(mat_S);
                HSV.add(mat_V);
                Core.merge(HSV, dst);
                Imgproc.cvtColor(dst, dst, Imgproc.COLOR_HSV2RGB);

                Utils.matToBitmap(dst, bitmap_dst);
                img.setImageBitmap(bitmap_dst);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekBar1.setProgress(0);
                seekBar3.setProgress(0);

                Mat mat_HSV = new Mat();
                Imgproc.cvtColor(src, mat_HSV, Imgproc.COLOR_RGB2HSV);
                Mat mat_H = new Mat();
                Mat mat_S = new Mat();
                Mat mat_V = new Mat();

                Core.extractChannel(mat_HSV , mat_H, 0);
                Core.extractChannel(mat_HSV , mat_S, 1);
                Core.extractChannel(mat_HSV , mat_V, 2);

                Core.add(mat_S, new Scalar(i), mat_S);

                List<Mat> HSV = new ArrayList<Mat>();
                HSV.add(mat_H);
                HSV.add(mat_S);
                HSV.add(mat_V);
                Core.merge(HSV, dst);
                Imgproc.cvtColor(dst, dst, Imgproc.COLOR_HSV2RGB);

                Utils.matToBitmap(dst, bitmap_dst);
                img.setImageBitmap(bitmap_dst);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekBar1.setProgress(0);
                seekBar2.setProgress(0);

                Mat mat_HSV = new Mat();
                Imgproc.cvtColor(src, mat_HSV, Imgproc.COLOR_RGB2HSV);
                Mat mat_H = new Mat();
                Mat mat_S = new Mat();
                Mat mat_V = new Mat();

                Core.extractChannel(mat_HSV , mat_H, 0);
                Core.extractChannel(mat_HSV , mat_S, 1);
                Core.extractChannel(mat_HSV , mat_V, 2);

                Core.add(mat_V, new Scalar(i), mat_V);

                List<Mat> HSV = new ArrayList<Mat>();
                HSV.add(mat_H);
                HSV.add(mat_S);
                HSV.add(mat_V);
                Core.merge(HSV, dst);
                Imgproc.cvtColor(dst, dst, Imgproc.COLOR_HSV2RGB);

                Utils.matToBitmap(dst, bitmap_dst);
                img.setImageBitmap(bitmap_dst);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        Button button_save = (Button) findViewById(R.id.save_btn);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "HSV");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}