package com.kavya.windownotification;

import static android.os.Build.VERSION_CODES.O;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btn;
private static final String CHANNEL_ID="some_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn_show_notification);
        btn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= O) {
                    setUpNotification();
                }else{
                    createOlderNotif();
                }

            }
        });
    }
    @RequiresApi(O)
    private void setUpNotification(){

          @SuppressLint("WrongConstant") NotificationChannel  channel = new NotificationChannel(CHANNEL_ID,"Notification one ", NotificationManager.IMPORTANCE_MAX);

        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification.Builder builder=new Notification.Builder(getApplicationContext())
                .setContentTitle("Notification")
                .setContentText("Congratulations! You've unlocked a new achievement. Keep reaching for the stars and achieving greatness in your journey!")
               // .setStyle(new Notification.BigTextStyle().bigText("Congratulations! You've unlocked a new achievement. Keep reaching for the stars and achieving greatness in your journey!"))
                .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.prizee)).bigLargeIcon((Bitmap)null))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.prizee))
                .setSmallIcon(R.drawable.prize)
               // .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 13, intent, PendingIntent.FLAG_IMMUTABLE))
                .setChannelId(CHANNEL_ID);
        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= O) {
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1,builder.build());
    }
    @RequiresApi(api = O)
    private void createOlderNotif(){
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        @SuppressLint("WrongConstant") NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Notification one ",NotificationManager.IMPORTANCE_MAX);
        intent = new Intent(MainActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        @SuppressLint("UnspecifiedImmutableFlag") Notification.Builder builder=new Notification.Builder(getApplicationContext())
                .setContentTitle("Title of the notification")
                // .setContentText("Congratulations! You've unlocked a new achievement. Keep reaching for the stars and achieving greatness in your journey!")
                .setStyle(new Notification.BigTextStyle().bigText("Congratulations! You've unlocked a new achievement. Keep reaching for the stars and achieving greatness in your journey!"))
                .setSmallIcon(R.drawable.prize)
                // .setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(),13,intent,0));
        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(1,builder.build());
    }
}