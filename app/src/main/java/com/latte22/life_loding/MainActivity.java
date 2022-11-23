package com.latte22.life_loding;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private Toolbar toolbarMain;
    private TextView textMain;
    private TextView textDate;

    private static Handler mHandler ;
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar testProgress;
    public static double value;

    public ProgressBar wProgress;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);

        toolbarMain = findViewById(R.id.toolbar_main);
        textMain = findViewById(R.id.textView);
        textDate = findViewById(R.id.textDate);
        testProgress = findViewById(R.id.testprogressBar);
        //wProgress = findViewById(R.id.wprogressBar);

        textDate.setText(getDate());

        setSupportActionBar(toolbarMain); //툴바를 사용하면 여러 레이아웃을 사용할 수 있기 때문
        //getSupportActionBar().setTitle("Loading.."); //툴바 메인 이름 바뀜
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("Life Loading... ⏳");
        //액션바 배경색 변경
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();
        String colorText = intent.getStringExtra("color");
        if (colorText != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                testProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#"+colorText)));
            }else {
                Toast t = Toast.makeText(getApplicationContext() ,"SDK version is low . :(", Toast.LENGTH_LONG);
                t.show();
            }
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                textMain = findViewById(R.id.textView);
                testProgress = findViewById(R.id.testprogressBar);

                Percent p = new Percent();
                p.persent();
                double percent = p.testLoading;
//
                String strNumber = String.format("%.1f", percent);
                textMain.setText("Loading... " + strNumber + "%");

                //textMain.setText("Loading... " + p.testLoading + "%");

                value = Double.parseDouble(strNumber);
            }
        };

        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mHandler.sendEmptyMessage(0);
                }
            }
        }

        NewRunnable nr = new NewRunnable();
        Thread t = new Thread(nr);
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //뒤로가기 버튼 실행 어떻게 받아야 하는지
    //툴바에서 클릭되면 이게 실행됨
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_test_01:
                Intent subIntent = new Intent(this, SubActivity.class);

                subIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                subIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(subIntent);
                break;

            case R.id.action_test_02:
                Intent thirdIntent = new Intent(this, ThirdActivity.class);

                thirdIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                thirdIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(thirdIntent);
                break;

            case R.id.action_test_03:
                Percent p = new Percent();
                Percent.tf = true;
                Percent.dday_static = (long) 0.0;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    testProgress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getDate() {
        //LocalDate now = LocalDate.now(); //2021년 01월 28일에 코드 실행함.

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        String getDate = dateFormat.format(date);

        return getDate;
    }

}