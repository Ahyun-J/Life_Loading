package com.latte22.life_loding;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static final String TAG = "NewAppWidget";
    private static final int WIDGET_UPDATE_INTERVAL = 5000;
    private static PendingIntent mSender;
    private static AlarmManager mManager;
    static public double val = MainActivity.value;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //CharSequence widgetText = context.getString(R.string.appwidget_text);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        views.setProgressBar(R.id.wprogressBar, 100, (int)val, false);
        views.setTextViewText(R.id.textP, (int)val+"%");

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new ManiTime(context,appWidgetManager),1,1000);

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    class ManiTime extends TimerTask {
        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;

        public ManiTime(Context context,AppWidgetManager appWidgetManager) {
            this.appWidgetManager=appWidgetManager;
            remoteViews=new RemoteViews(context.getPackageName(),R.layout.new_app_widget);
            thisWidget=new ComponentName(context, NewAppWidget.class);
        }

        @Override
        public void run() {
            val = MainActivity.value;
            remoteViews.setTextViewText(R.id.textP,(int)val+"%");
            remoteViews.setProgressBar(R.id.wprogressBar, 100, (int)val, false);
            appWidgetManager.updateAppWidget(thisWidget,remoteViews);
        }
    }
}