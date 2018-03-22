package com.example.hoang.doantotnghiep.View.activitys;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hoang.doantotnghiep.R;

/**
 * Created by Admin on 1/30/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeatingIntent = new Intent(context, HomeActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
                repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.iconapp)
                .setContentTitle("Nhắc nhở !")
                .setContentText("Chạm để thêm chi tiêu cho ngày hôm nay")
                .setAutoCancel(true);


        Log.d("NOTIFI", "NOTIFICATION");
        notificationManager.notify(100, builder.build());
    }
}
