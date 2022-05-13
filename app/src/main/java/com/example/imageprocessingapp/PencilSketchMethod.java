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
import org.opencv.photo.Photo;

import java.io.IOException;

public class PencilSketchMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pencil_sketch_method);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pencil Sketch (Method)

        Mat src = new Mat();
        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src,src,Imgproc.COLOR_RGBA2RGB);

        Mat dst1 = new Mat();
        Mat dst2 = new Mat();

        Photo.pencilSketch(src, dst1, dst2, 100, 0.2f, 0.1f);

        Bitmap bitmap_g = Bitmap.createBitmap(dst1.cols(), dst1.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst1, bitmap_g);

        Bitmap bitmap_c = Bitmap.createBitmap(dst1.cols(), dst1.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(dst2, bitmap_c);

        ImageView img1 = (ImageView) findViewById(R.id.Original);
        img1.setImageBitmap(bitmap);
        ImageView img2 = (ImageView) findViewById(R.id.PencilSketchG);
        img2.setImageBitmap(bitmap_g);
        ImageView img3 = (ImageView) findViewById(R.id.PencilSketchC);
        img3.setImageBitmap(bitmap_c);

        Button button_save = (Button) findViewById(R.id.button_save_);
        button_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SaveBitmap(bitmap_g, "Pencil Sketch (Method) - G");
                SaveBitmap(bitmap_c, "Pencil Sketch (Method) - C");
            }
        });

    }

    public void SaveBitmap(Bitmap bitmap, String temp) {
        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, temp, temp);
        Toast.makeText(this, "Saved to gallery.", Toast.LENGTH_LONG).show();
    }

}