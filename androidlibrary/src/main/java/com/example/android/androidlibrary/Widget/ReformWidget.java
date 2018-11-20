package com.example.android.androidlibrary.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.R;
import com.example.android.androidlibrary.ViewModel.ReformViewModel;

public class ReformWidget extends AppWidgetProvider {

    private static Reform reform;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.reform_widget);

        if (reform != null) {
            views.setTextViewText(R.id.txt_Widget_room, reform.getRoom());
            views.setTextViewText(R.id.txt_widget_days, reform.getDays());
            views.setTextViewText(R.id.txt_widget_spent, reform.getTotal_spent());
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

    public static void updateReformWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public void getReform(final Context context, Reform[] reforms){
        int value = Integer.MAX_VALUE;
        int index = 0;
        for (int i=0; i<reforms.length; i++){
            if (Integer.parseInt(reforms[i].getDays()) < value) {
                value = Integer.parseInt(reforms[i].getDays());
                index = i;
            }
        }
        setReform(context, reforms[index]);
    }

    public void setReform(Context context, Reform reform){
        ReformWidget.reform = reform;

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, ReformWidget.class));
        ReformWidget.updateReformWidget(context, appWidgetManager, appWidgetIds);
    }
}

