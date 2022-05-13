package com.example.imageprocessingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Effects extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        Intent intent = getIntent();
        uri = intent.getParcelableExtra("Uri");

        ArrayList<String> effectNames = new ArrayList<>();
        effectNames.add("RGB to Grayscale");
        effectNames.add("Negative (RGB)");
        effectNames.add("Negative (Grayscale)");
        effectNames.add("Magnitude and Angle");
        effectNames.add("Edge Detection - Prewitt");
        effectNames.add("Edge Detection - Sobel");
        effectNames.add("Edge Detection - Roberts");
        effectNames.add("Laplacian");
        effectNames.add("Histogram Equalization");
        effectNames.add("CLAHE");
        effectNames.add("Median Blur");
        effectNames.add("Gaussian Blur");
        effectNames.add("Box Blur");
        effectNames.add("Bilateral Filter");
        effectNames.add("Unsharp Masking");
        effectNames.add("Add Gaussian Noise");
        effectNames.add("Add Salt-And-Pepper Noise");
        effectNames.add("Motion Blur Filter");
        effectNames.add("YCbCr");
        effectNames.add("HSV");
        effectNames.add("Thresholding");
        effectNames.add("Contrast");
        effectNames.add("Brightness");
        effectNames.add("ASCII Art");
        effectNames.add("Watermarking");
        effectNames.add("Pencil Sketch (Pipeline)");
        effectNames.add("Cartoon Effect (Pipeline)");
        effectNames.add("Pencil Sketch (Method)");
        effectNames.add("Stylization (Method)");
        effectNames.add("Difference of Gaussians");
        effectNames.add("Canny Edge Detector");
        effectNames.add("Emboss");
        effectNames.add("Colour Map");
        effectNames.add("Old Photograph (Pipeline)");
        effectNames.add("Sharpen");

        RecyclerView recyclerView = findViewById(R.id.effect_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, effectNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "" + adapter.getItem(position), Toast.LENGTH_SHORT).show();

        if(adapter.getItem(position).equals("RGB to Grayscale")) {
            Intent intent = new Intent(view.getContext(), RGB2GRAY.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Negative (RGB)")) {
            Intent intent = new Intent(view.getContext(), NegativeRGB.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Negative (Grayscale)")) {
            Intent intent = new Intent(view.getContext(), NegativeGrayscale.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Magnitude and Angle")) {
            Intent intent = new Intent(view.getContext(), MagnitudeAndAngle.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Edge Detection - Prewitt")) {
            Intent intent = new Intent(view.getContext(), EdgeDetectionPrewitt.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Edge Detection - Sobel")) {
            Intent intent = new Intent(view.getContext(), EdgeDetectionSobel.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Edge Detection - Roberts")) {
            Intent intent = new Intent(view.getContext(), EdgeDetectionRoberts.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Laplacian")) {
            Intent intent = new Intent(view.getContext(), Laplacian.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Histogram Equalization")) {
            Intent intent = new Intent(view.getContext(), HistogramEqualization.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("CLAHE")) {
            Intent intent = new Intent(view.getContext(), CLAHE_.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Median Blur")) {
            Intent intent = new Intent(view.getContext(), MedianBlur.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Gaussian Blur")) {
            Intent intent = new Intent(view.getContext(), GaussianBlur.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Box Blur")) {
            Intent intent = new Intent(view.getContext(), BoxBlur.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Bilateral Filter")) {
            Intent intent = new Intent(view.getContext(), BilateralFilter.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Unsharp Masking")) {
            Intent intent = new Intent(view.getContext(), UnsharpMasking.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Add Gaussian Noise")) {
            Intent intent = new Intent(view.getContext(), AddGaussianNoise.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Add Salt-And-Pepper Noise")) {
            Intent intent = new Intent(view.getContext(), AddSaltAndPepperNoise.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Motion Blur Filter")) {
            Intent intent = new Intent(view.getContext(), MotionBlurFilter.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("YCbCr")) {
            Intent intent = new Intent(view.getContext(), YCbCr.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("HSV")) {
            Intent intent = new Intent(view.getContext(), HSV.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Thresholding")) {
            Intent intent = new Intent(view.getContext(), Thresholding.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Contrast")) {
            Intent intent = new Intent(view.getContext(), Contrast.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Brightness")) {
            Intent intent = new Intent(view.getContext(), Brightness.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("ASCII Art")) {
            Intent intent = new Intent(view.getContext(), ASCIIArt.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Watermarking")) {
            Intent intent = new Intent(view.getContext(), Watermarking.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Pencil Sketch (Pipeline)")) {
            Intent intent = new Intent(view.getContext(), PencilSketchPipeline.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Cartoon Effect (Pipeline)")) {
            Intent intent = new Intent(view.getContext(), CartoonEffectPipeline.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Pencil Sketch (Method)")) {
            Intent intent = new Intent(view.getContext(), PencilSketchMethod.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Stylization (Method)")) {
            Intent intent = new Intent(view.getContext(), StylizationMethod.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Difference of Gaussians")) {
            Intent intent = new Intent(view.getContext(), DifferenceOfGaussians.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Canny Edge Detector")) {
            Intent intent = new Intent(view.getContext(), CannyEdgeDetector.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Emboss")) {
            Intent intent = new Intent(view.getContext(), Emboss.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Colour Map")) {
            Intent intent = new Intent(view.getContext(), ColourMap.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Old Photograph (Pipeline)")) {
            Intent intent = new Intent(view.getContext(), OldPhotographPipeline.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }
        else if(adapter.getItem(position).equals("Sharpen")) {
            Intent intent = new Intent(view.getContext(), Sharpen.class);
            intent.putExtra("Uri", uri);
            startActivity(intent);
        }

    }
}