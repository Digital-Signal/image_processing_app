package com.example.imageprocessingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class ASCIIArt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asciiart);

        OpenCVLoader.initDebug();

        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("Uri");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView img = (ImageView) findViewById(R.id.ascii_img);
        img.setImageBitmap(bitmap);

        TextView art = (TextView) findViewById(R.id.ascii_art);

        /**
         * ASCII Art
         */
        Mat mat = new Mat();
        Mat gray = new Mat();
        Mat smaller = new Mat();

        Utils.bitmapToMat(bitmap, mat);

        Imgproc.cvtColor(mat, gray, Imgproc.COLOR_RGB2GRAY);

        Size size = new Size(140,140*gray.height()/gray.width());
        Imgproc.resize(gray, smaller, size);
        int row = smaller.rows();
        int col = smaller.cols();
        for(int i = 0; i < row; i++) {
            art.append("|");
            for(int j = 0; j < col; j++) {
                art.append( Int2String( (int)(smaller.get(i,j)[0]) ) );
            }
            art.append("|\n");
        }

    }

    public String Int2String(int intensity) {

        if (intensity >= 0 && intensity < 16)
            return "@";
        else if (intensity >= 16 && intensity < 32)
            return "@";
        else if (intensity >= 32 && intensity < 48)
            return "#";
        else if (intensity >= 48 && intensity < 64)
            return "%";
        else if (intensity >= 64 && intensity < 80)
            return "W";
        else if (intensity >= 80 && intensity < 96)
            return "M";
        else if (intensity >= 96 && intensity < 112)
            return "X";
        else if (intensity >= 112 && intensity < 128)
            return "V";
        else if (intensity >= 128 && intensity < 144)
            return "v";
        else if (intensity >= 144 && intensity < 160)
            return "^";
        else if (intensity >= 160 && intensity < 176)
            return "*";
        else if (intensity >= 176 && intensity < 192)
            return "=";
        else if (intensity >= 192 && intensity < 208)
            return "~";
        else if (intensity >= 208 && intensity < 224)
            return "-";
        else if (intensity >= 224 && intensity < 240)
            return ".";
        else if (intensity >= 240 && intensity < 256)
            return " ";
        else {
            return " ";
        }

    }

}