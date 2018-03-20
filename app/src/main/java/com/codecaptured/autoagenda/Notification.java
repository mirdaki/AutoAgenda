package com.codecaptured.autoagenda;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;



/**
 * Created by Ana on 20/3/18.
 */

public class Notification extends AppCompatActivity
{
	private NotificationManager notifManager;

	public void createNotification(MainActivity main, long time){

				final int NOTIFY_ID = 1002;

				String name = "my_package_channel";
				String id = "my_package_channel_1"; // The user-visible name of the channel.
				String description = "my_package_first_channel"; // The user-visible description of the channel

				Intent intent;
				PendingIntent pendingIntent;
				NotificationCompat.Builder builder;

				if (notifManager == null) {
					notifManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				}

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					int importance = NotificationManager.IMPORTANCE_HIGH;
					NotificationChannel mChannel = notifManager.getNotificationChannel(id);

					if (mChannel == null) {
						mChannel = new NotificationChannel(id, name, importance);
						mChannel.setDescription(description);
						mChannel.enableVibration(true);
						mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
						notifManager.createNotificationChannel(mChannel);
					}

					builder = new NotificationCompat.Builder(getApplicationContext(), id);

					intent = new Intent(this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

					builder.setContentTitle("Notification")
									.setSmallIcon(android.R.drawable.ic_popup_reminder)
									.setContentText("Content of notification")
									.setDefaults(android.app.Notification.DEFAULT_ALL)
									.setAutoCancel(true)
									.setContentIntent(pendingIntent)
									.setWhen(time)
									.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
				} else {

					builder = new NotificationCompat.Builder(getApplicationContext(), id);

					intent = new Intent(this, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

					builder.setContentTitle("Notification")
									.setSmallIcon(android.R.drawable.ic_popup_reminder)
									.setContentText("Content of notification")
									.setDefaults(android.app.Notification.DEFAULT_ALL)
									.setAutoCancel(true)
									.setContentIntent(pendingIntent)
									.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
									.setPriority(android.app.Notification.PRIORITY_HIGH)
									.setWhen(time);
				}

				android.app.Notification notification = builder.build();
				notifManager.notify(NOTIFY_ID, notification);

	}
}
