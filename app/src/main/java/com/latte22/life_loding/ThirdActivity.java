package com.latte22.life_loding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.skydoves.colorpickerview.sliders.AlphaSlideBar;
import com.skydoves.colorpickerview.sliders.BrightnessSlideBar;

public class ThirdActivity extends AppCompatActivity {

    TextView color;
    View colorView;
    Button btnThrid;
    private Toolbar toolbarThird;
    public static String color_h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        color = findViewById(R.id.color);
        colorView = findViewById(R.id.color_view);
        btnThrid = findViewById(R.id.btn_third);
        toolbarThird = findViewById(R.id.toolbar_third);

        setSupportActionBar(toolbarThird);
        getSupportActionBar().setTitle("색상 변경... ⏳");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ColorPickerView colorPickerView = findViewById(R.id.colorPickerView);
        colorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                color.setText("#"+envelope.getHexCode());
                colorView.setBackgroundColor(envelope.getColor());
                btnThrid.setBackgroundColor(envelope.getColor());
                color_h = envelope.getHexCode();
            }
        });

        BrightnessSlideBar brightnessSlideBar = findViewById(R.id.brightnessSlide);
        colorPickerView.attachBrightnessSlider(brightnessSlideBar);

        AlphaSlideBar alphaSlideBar = findViewById(R.id.alphaSlideBar);
        colorPickerView.attachAlphaSlider(alphaSlideBar);

        btnThrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                intent.putExtra("color", color_h);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent wintent = new Intent(ThirdActivity.this, NewAppWidget.class);
                wintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                ThirdActivity.this.sendBroadcast(wintent);

                startActivity(intent);
            }
        });
    }
}