package com.latte22.life_loding;

import android.util.Log;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoUnit;

import java.time.OffsetDateTime;
import java.util.Calendar;

public class Percent {
    private final int ONE_DAY = 24 * 60 * 60 * 1000;
    double testLoading;
    //= ((getRemainingDays() /  getYear())*100);
    //YearPercent yp = new YearPercent();

    //testLoading = ((getRemainingDays() /  getYear())*100);

    static boolean tf = true;
    static long dday_static;

    public void persent(long d){
        if(d <= -1) {

        } else dday_static = d;
    }

    public void persent(){
        if(tf == true && dday_static == 0.0){
            //testLoading = yp.test;
            //
            //return testLoading;
            testLoading = ((getRemainingDays() /  getYear())*100);
            Log.i("loading", Double.toString(testLoading));
            Log.i("loading", Double.toString(getRemainingDays()));
            Log.i("loading", Double.toString(getYear()));
//            Log.i("tf","true");
        }
        else {
            // dday_static = d;
            testLoading =  (((getYear()-(double)dday_static)/getYear())*100);
//            Log.i("loading", Double.toString(testLoading));
//            Log.i("loading", Double.toString(dday_static));
//            Log.i("loading", Double.toString(getYear()));
            if(testLoading < 0) testLoading = 0;
            //return testLoading;
//            Log.i("tf","false");
        }
        MainActivity.testProgress.setProgress((int)testLoading);
    }

    private long getYear(){
        Calendar calendar;
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        if (year % 400 == 0) {  //연도가 400의 배수이면 윤년
            return 366;
        } else if (year % 4 == 0 && year % 100 != 0) {  //연도가 4의 배수고 100의 배수가 아니면 윤년
            return 366;
        } else {  //나머지는 다 윤년이 아니다
            return 365;
        }
    }

    private double getRemainingDays(){
        int  d1 = -2;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            OffsetDateTime offset = OffsetDateTime.now();

            d1 = offset.getDayOfYear();
        } else{
            LocalDate now = LocalDate.now();
            String str = String.valueOf(now);

            String[] array = str.split("-");

            for(int i = 0; i <Integer.parseInt(array[1])-1; i++){
                if(i == 2 || i == 4 || i == 6 || i == 9 || i == 11){
                    d1 += 1;
                }
                d1 += 30;
            }
            if(getYear() == 366) d1 += 2;
            d1 += Integer.parseInt(array[2]);
        }

        return d1;
    }
}
