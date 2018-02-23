package com.codecaptured.autoagenda;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.content.Context;
import android.os.Build;
import android.app.NotificationChannel;
import android.app.Notification;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
	  private NotificationManager notifManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

	Button notification;

	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTextMessage = (TextView) findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


		final int NOTIFY_ID = 1002;
		notification = (Button) findViewById(R.id.btnNotif);
		notification.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
		// There are hardcoding only for show it's just strings
		String name = "my_package_channel";
		String id = "my_package_channel_1"; // The user-visible name of the channel.
		String description = "my_package_first_channel"; // The user-visible description of the channel

		Intent intent;
		PendingIntent pendingIntent;
		NotificationCompat.Builder builder;

		if (notifManager == null) {
			notifManager =
							(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
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

			intent = new Intent(MainActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

			builder.setContentTitle("Notification")  // required
							.setSmallIcon(android.R.drawable.ic_popup_reminder) // required
							.setContentText("Content of notification")  // required
							.setDefaults(Notification.DEFAULT_ALL)
							.setAutoCancel(true)
							.setContentIntent(pendingIntent)

							.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
		} else {

			builder = new NotificationCompat.Builder(getApplicationContext(), id);

			intent = new Intent(MainActivity.this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

			builder.setContentTitle("Notification")                           // required
							.setSmallIcon(android.R.drawable.ic_popup_reminder) // required
							.setContentText("Content of notification")  // required
							.setDefaults(Notification.DEFAULT_ALL)
							.setAutoCancel(true)
							.setContentIntent(pendingIntent)
							.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
							.setPriority(Notification.PRIORITY_HIGH);
		} // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

		Notification notification = builder.build();
		notifManager.notify(NOTIFY_ID, notification);


			}
		});
	}
}
