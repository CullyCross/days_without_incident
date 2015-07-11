package me.cullycross.dayswithoutincident;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;

/**
 * Created by cullycross on 7/11/15.
 */
public abstract class DaysWidget extends AppWidgetProvider {

    protected final static String PREFS_FILE = "me.cullycross.dayswithoutincident.preferences";
    protected final static String PREFS_COUNT_NAME = "me.cullycross.dayswithoutincident.preferences.count";
    protected final static String ID = "me.cullycross.dayswithoutincident.widget_id";
    protected final static String TYPE = "me.cullycross.dayswithoutincident.type";

    protected final static String TAG = DaysWidget.class.getName();

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, 2000);

        Intent alarmIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        alarmIntent.putExtra(TYPE, getType());
        alarmIntent.putExtra(ID, appWidgetId);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // RTC does not wake the device up
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 2000, pendingIntent);

        /*Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        *//*calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH, 1);*//*

        Intent intent = new Intent(context, DaysWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingIntentService =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        *//*alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntentService);*//*

        SharedPreferences sharedPreferences =
                context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(PREFS_COUNT_NAME + appWidgetId, 0).apply();*/
    }

    @Override
    public void onReceive(@NonNull Context context,@NonNull Intent intent) {
        super.onReceive(context, intent);

        Bundle extras = intent.getExtras();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            if (extras != null) {
                int id = extras.getInt(ID);
                String type = extras.getString(TYPE);
                RemoteViews views = new RemoteViews(context.getPackageName(), getLayout());
                SharedPreferences sharedPreferences =
                        context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
                updateRemoteView(views, sharedPreferences, id, type);
                appWidgetManager.updateAppWidget(id, views);
            }
        }
    }

    public abstract int getLayout();

    public abstract void updateRemoteView(RemoteViews views,
                                          SharedPreferences prefs,
                                          int id,
                                          @NonNull String type);

    public abstract String getType();
}
