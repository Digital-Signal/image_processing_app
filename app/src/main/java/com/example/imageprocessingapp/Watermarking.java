package com.example.imageprocessingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class Watermarking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watermarking);

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
         * Watermarking
         */

        Mat src = new Mat();

        Utils.bitmapToMat(bitmap, src);

        Bitmap bitmap_dst = Bitmap.createBitmap(src.cols(), src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src, bitmap_dst);

        ImageView img = (ImageView) findViewById(R.id.img1);
        img.setImageBitmap(bitmap_dst);

        EditText edit_Text = (EditText) findViewById(R.id.editText1);
        Button button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Mat initial = src.clone();
                String s = edit_Text.getText().toString();
                Imgproc.putText(initial, s, new Point(50, src.rows() - 50), Imgproc.FONT_HERSHEY_SIMPLEX, 2, new Scalar(0), 7);
                Utils.matToBitmap(initial, bitmap_dst);
                img.setImageBitmap(bitmap_dst);
            }
        });

        Button button_save = (Button) findViewById(R.id.button_s);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_dst, "Watermarking");

            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}