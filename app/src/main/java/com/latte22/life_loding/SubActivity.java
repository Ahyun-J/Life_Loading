package com.latte22.life_loding;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SubActivity extends AppCompatActivity {

    private int mYear =0, mMonth=0, mDay=0;
    private Button btnSub;
    private Toolbar toolbarSub;
    private DatePicker mDate;
    private TextView txtSub, dy;
    private final int ONE_DAY = 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // API 24 이상일 경우 시스템 기본 테마 사용
//            mDate.set
//                    datePickerMode="spinner"
            mDate.setCalendarViewShown(false);

        }

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sub);

        txtSub = findViewById(R.id.text_date);
        dy = findViewById(R.id.day);
        toolbarSub = findViewById(R.id.toolbar_sub);
        mDate = (DatePicker)findViewById(R.id.dp);

        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), mOnDateChangedListener);
        Locale.setDefault(Locale.KOREAN);

        txtSub.setText(String.format("%d/%d/%d", mDate.getYear(), mDate.getMonth() + 1, mDate.getDayOfMonth()));
        //dd.setText(String.format("D-%d", 0));

        setSupportActionBar(toolbarSub);
        getSupportActionBar().setTitle("날짜 변경... ⏳");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar calendar = new GregorianCalendar();

//        long now = calendar.getTimeInMillis();
//                //System.currentTimeMillis();
//                //
//        mDate.setMinDate(now);    //입력한 날짜 이후로 클릭 안되게 옵션

//        Calendar minDate = Calendar.getInstance();
//        minDate.set(2022,11-1,23);
//        mDate.setMinDate(minDate.getTime().getTime());

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        btnSub = findViewById(R.id.btn_sub);
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // D-day 설정
                final Calendar ddayCalendar = Calendar.getInstance();
                ddayCalendar.set(mYear, mMonth, mDay);

                // D-day 를 구하기 위해 millisecond 으로 환산하여 d-day 에서 today 의 차를 구한다.
                final long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
                final long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
                long result = dday - today;

                Log.i("re", String.valueOf(result));

                Percent p = new Percent();
                Percent.tf = false;

                p.persent(result);

                Intent intent = new Intent(SubActivity.this, MainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Intent wintent = new Intent(SubActivity.this, NewAppWidget.class);
                wintent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                SubActivity.this.sendBroadcast(wintent);

                startActivity(intent);
            }
        });
    }

    public void mOnClick(View v){
        Intent intent = new Intent();
        intent.putExtra("mYear",mYear);
        intent.putExtra("mMonth", mMonth+1);
        intent.putExtra("mDay", mDay);
        setResult(RESULT_OK, intent);
        finish();
    }

    DatePicker.OnDateChangedListener mOnDateChangedListener = new DatePicker.OnDateChangedListener(){
        @Override
        public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
            mYear = yy;
            mMonth = mm;
            mDay = dd;

            txtSub.setText(String.format("%d/%d/%d", mYear, mMonth + 1, mDay));

            Calendar ddayCalendar = Calendar.getInstance();
            ddayCalendar.set(mYear, mMonth, mDay);

            final long dday = ddayCalendar.getTimeInMillis() / ONE_DAY;
            final long today = Calendar.getInstance().getTimeInMillis() / ONE_DAY;
            long result = dday - today;

            LocalDate rsrvDate = LocalDate.of(mYear, mMonth+1, mDay);
            LocalDate now = LocalDate.now();

            long remainDay = ChronoUnit.DAYS.between(now, rsrvDate);
            //System.out.println(remainDay);

            if(remainDay <= 0){
                dy.setText(String.format("D+%d", Math.abs(result)));
            } else {
                dy.setText(String.format("D-%d", remainDay));
            }

            Intent intent = new Intent();
            intent.putExtra("mYear",mYear);
            intent.putExtra("mMonth", mMonth+1);
            intent.putExtra("mDay", mDay);
            setResult(RESULT_OK, intent);
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                Intent subIntent = new Intent(this, MainActivity.class);
                startActivity(subIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}