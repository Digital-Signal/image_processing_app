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
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnsharpMasking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsharp_masking);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Unsharp Masking

        Mat src = new Mat();
        Mat src_gaussian_blur = new Mat();
        Mat middle1 = new Mat();
        Mat middle2 = new Mat();
        Scalar s = new Scalar(1);
        Mat dst = new Mat();

        Utils.bitmapToMat(bitmap, src);
        Mat temp_src = src.clone();

        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src, bitmap_dst);

        ImageView img = (ImageView) findViewById(R.id.image_effect);
        img.setImageBitmap(bitmap_dst);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(i == 0) {
                    Utils.matToBitmap(temp_src, bitmap_dst);
                    img.setImageBitmap(bitmap_dst);
                }
                else {
                    Scalar s = new Scalar(i);

                    Mat mat_YCrCb = new Mat();
                    Imgproc.cvtColor(src, mat_YCrCb, Imgproc.COLOR_RGB2YCrCb);
                    Mat mat_Y = new Mat();
                    Mat mat_Cb = new Mat();
                    Mat mat_Cr = new Mat();

                    Core.extractChannel(mat_YCrCb, mat_Y, 0);
                    Core.extractChannel(mat_YCrCb, mat_Cr, 1);
                    Core.extractChannel(mat_YCrCb, mat_Cb, 2);

                    Imgproc.GaussianBlur(mat_Y, src_gaussian_blur, new Size(55, 55), 0, 0);
                    Core.subtract(mat_Y, src_gaussian_blur, middle1);
                    Core.multiply(middle1, s, middle2);
                    Core.add(mat_Y, middle2, dst);

                    List<Mat> YCrCb = new ArrayList<Mat>();
                    YCrCb.add(dst);
                    YCrCb.add(mat_Cr);
                    YCrCb.add(mat_Cb);
                    Core.merge(YCrCb, dst);
                    Imgproc.cvtColor(dst, dst, Imgproc.COLOR_YCrCb2RGB);

                    Utils.matToBitmap(dst, bitmap_dst);
                    img.setImageBitmap(bitmap_dst);
                }
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
                SaveBitmap(bitmap_dst, "Unsharp Masking");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}