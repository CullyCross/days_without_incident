package me.cullycross.dayswithoutincident;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

/**
 * Created by cullycross on 7/11/15.
 */
public class DaysWidgetMedium extends DaysWidget {

    public final static String MEDIUM = "me.cullycross.dayswithoutincident.medium";

    @Override
    public int getLayout() {
        return R.layout.medium_widget;
    }

    @Override
    public void updateRemoteView(RemoteViews views, SharedPreferences prefs, int id, @NonNull String type) {
        if (type != null && type.equals(MEDIUM)) {
            int current = prefs.getInt(PREFS_COUNT_NAME + id, 0);
            prefs.edit().putInt(PREFS_COUNT_NAME + id, ++current).apply();
            views.setTextViewText(R.id.update, current + "");
        }
    }

    @Override
    public String getType() {
        return MEDIUM;
    }
}
