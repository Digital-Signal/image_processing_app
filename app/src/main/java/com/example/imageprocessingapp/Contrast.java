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

public class Contrast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Contrast

        Mat src = new Mat();
        Mat dst = new Mat();

        Utils.bitmapToMat(bitmap, src);

        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src, bitmap_dst);

        ImageView img = (ImageView) findViewById(R.id.image_effect);
        img.setImageBitmap(bitmap_dst);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Scalar contrast = null;
                if(i == 0) {
                    contrast = new Scalar(0.1);
                }
                else if (i == 1) {
                    contrast = new Scalar(0.2);
                }
                else if (i == 2) {
                    contrast = new Scalar(0.3);
                }
                else if (i == 3) {
                    contrast = new Scalar(0.4);
                }
                else if (i == 4) {
                    contrast = new Scalar(0.5);
                }
                else if (i == 5) {
                    contrast = new Scalar(0.6);
                }
                else if (i == 6) {
                    contrast = new Scalar(0.7);
                }
                else if (i == 7) {
                    contrast = new Scalar(0.8);
                }
                else if (i == 8) {
                    contrast = new Scalar(0.9);
                }
                else if (i == 9) {
                    contrast = new Scalar(1);
                }
                else if (i == 10) {
                    contrast = new Scalar(1.1);
                }
                else if (i == 11) {
                    contrast = new Scalar(1.2);
                }
                else if (i == 12) {
                    contrast = new Scalar(1.3);
                }
                else if (i == 13) {
                    contrast = new Scalar(1.4);
                }
                else if (i == 14) {
                    contrast = new Scalar(1.5);
                }
                else if (i == 15) {
                    contrast = new Scalar(1.6);
                }
                else if (i == 16) {
                    contrast = new Scalar(1.7);
                }
                else if (i == 17) {
                    contrast = new Scalar(1.8);
                }
                else if (i == 18) {
                    contrast = new Scalar(1.9);
                }
                else if (i == 19) {
                    contrast = new Scalar(2.0);
                }
                else if (i == 20) {
                    contrast = new Scalar(2.1);
                }
                else if (i == 21) {
                    contrast = new Scalar(2.2);
                }
                else if (i == 22) {
                    contrast = new Scalar(2.3);
                }
                else if (i == 23) {
                    contrast = new Scalar(2.4);
                }
                else if (i == 24) {
                    contrast = new Scalar(2.5);
                }

                Mat mat_YCrCb = new Mat();
                Imgproc.cvtColor(src, mat_YCrCb, Imgproc.COLOR_RGB2YCrCb);
                Mat mat_Y = new Mat();
                Mat mat_Cb = new Mat();
                Mat mat_Cr = new Mat();

                Core.extractChannel(mat_YCrCb , mat_Y, 0);
                Core.extractChannel(mat_YCrCb , mat_Cr, 1);
                Core.extractChannel(mat_YCrCb , mat_Cb, 2);

                Core.multiply(mat_Y, contrast, dst);

                List<Mat> YCrCb = new ArrayList<Mat>();
                YCrCb.add(dst);
                YCrCb.add(mat_Cr);
                YCrCb.add(mat_Cb);
                Core.merge(YCrCb, dst);
                Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2RGB);

                Utils.matToBitmap(dst, bitmap_dst);
                img.setImageBitmap(bitmap_dst);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button button_save = (Button) findViewById(R.id.save1);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Contrast");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}