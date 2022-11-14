package abcdjob.workonline.com.qrcode.Util;
/**
 * Created by VARUN on 01/01/19.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import abcdjob.workonline.com.qrcode.R;
import abcdjob.workonline.com.qrcode.ui.home.HomeActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "KINGSN";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    int i = 0;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "ADMIN PREFERENCES" ;
    @SuppressLint("CommitPrefEdits")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sharedPreferences=getSharedPreferences(GlobalVariables.ADMIN_PREF, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Context context = getApplicationContext();


        Log.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getData() != null) {
            if (remoteMessage.getData().containsKey(GlobalVariables.TYPE)) {
                if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.CHAT_NOTIFICATION)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.CHAT_NOTIFICATION);
                }/*else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.TICKET_COMMENT_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.TICKET_COMMENT_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.TICKET_STATUS_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.TICKET_STATUS_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.WALLET_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.WALLET_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.DECLINE_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.DECLINE_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.START_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.START_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.END_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.END_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ACCEPT_BOOKING_ARTIST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.ACCEPT_BOOKING_ARTIST_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.JOB_APPLY_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.JOB_APPLY_NOTIFICATION);
                }else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.BRODCAST_NOTIFICATION)) {
                    sendNotification(getValue(remoteMessage.getData(), "body"), GlobalVariables.BRODCAST_NOTIFICATION);
                }*/else if (remoteMessage.getData().get(GlobalVariables.TYPE).equalsIgnoreCase(GlobalVariables.ADMIN_NOTIFICATION)) {
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), GlobalVariables.ADMIN_NOTIFICATION);
                }else {
                    //sendNotification(getValue(remoteMessage.getData(), "body"), "");
                    sendNotification(context,getValue(remoteMessage.getData(), "sendBy"),getValue(remoteMessage.getData(), "sendTo"),getValue(remoteMessage.getData(), "title"),getValue(remoteMessage.getData(), "body"), "");

                }
            }

        }

    }

    public String getValue(Map<String, String> data, String key) {
        try {
            if (data.containsKey(key))
                return data.get(key);
            else
                return getString(R.string.app_name);
        } catch (Exception ex) {
            ex.printStackTrace();
            return getString(R.string.app_name);
        }
    }
    @Override
    public void onNewToken(@NotNull String token) {
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(GlobalVariables.DEVICE_TOKEN, token);
        editor.apply();
        SharedPreferences userDetails = MyFirebaseMessagingService.this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Log.d(TAG, "Refreshed token: " + token);
     //   sendNotification(getValue(remoteMessage.getData(), "sendBy"),sendNotification(getValue(remoteMessage.getData(), "sendto"),getValue(remoteMessage.getData(), "body"), GlobalVariables.CHAT_NOTIFICATION);

    }

    //  send custom notification
    private void sendNotification(Context context,String sendBy,String sendTo,String title,String messageBody, String tag) {

        Log.d(TAG, "sendNotification: "+sendTo+sendBy);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(tag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("sendBy",sendBy);
        intent.putExtra("sendTo",sendTo);
        intent.putExtra("title",title);
        editor.putString(GlobalVariables.adminMobile, sendBy);
        editor.putString(GlobalVariables.userMobile, sendTo);
        editor.putString("title", title);
        editor.apply();
      //  intent.putExtra(GlobalVariables.userMobile, sendTo);
        intent.putExtra(GlobalVariables.SCREEN_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_layout);

        collapsedView.setTextViewText(R.id.tvNotiTitle, "Reply To Your TicKet ID #"+title);
        collapsedView.setTextViewText(R.id.tvNotiDetails, messageBody);
       // collapsedView.setOnClickPendingIntent(R.id.btnViewDetails, pendingIntent);

    /*   RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_expanded);

        expandedView.setTextViewText(R.id.tvNotiTitle, messageBody);
        expandedView.setTextViewText(R.id.tvNotiDetails, messageBody.concat("body"));
*/
        collapsedView.setOnClickPendingIntent(R.id.notiContainer, pendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_error)
                /*.setCustomBigContentView(new RemoteViews(context.getPackageName(),
                        R.layout.notification_layout))*/
                .setCustomContentView(collapsedView)
              // .setCustomBigContentView(expandedView)
                //.setContentTitle(messageBody.concat("title"))
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }


    private void sendNotification1(Context context,String sendBy,String sendTo,String messageBody, String tag){


            RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                    R.layout.notification_layout);
            @SuppressLint("RemoteViewLayout") RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                    R.layout.notification_expanded);
            Intent clickIntent = new Intent(context, HomeActivity.class);
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                    0, clickIntent, 0);
            collapsedView.setTextViewText(R.id.tvNotiTitle, "Math Quiz Unlocked");
        expandedView.setTextViewText(R.id.tvNotiTitle, "Math Quiz Unlocked");
            //expandedView.setImageViewResource(R.id.image_view_expanded, R.drawable.quizlistner);
            expandedView.setOnClickPendingIntent(R.id.btnViewDetails, clickPendingIntent);

            Notification notification = new NotificationCompat.Builder(context, GlobalVariables.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_logout)
                    .setCustomContentView(collapsedView)
                    .setCustomBigContentView(expandedView)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .build();
            NotificationManagerCompat notificationManager
                    = NotificationManagerCompat.from(context);
            notificationManager.notify(1, notification);


    }


    private void sendNotificationcollapse(Context context,String sendBy,String sendTo,String title,String messageBody, String tag) {

        Log.d(TAG, "sendNotification: "+sendTo+sendBy);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(tag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("sendBy",sendBy);
        intent.putExtra("sendTo",sendTo);
        editor.putString(GlobalVariables.adminMobile, sendBy);
        editor.putString(GlobalVariables.userMobile, sendTo);
        editor.apply();
        //  intent.putExtra(GlobalVariables.userMobile, sendTo);
        intent.putExtra(GlobalVariables.SCREEN_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_layout);

        collapsedView.setTextViewText(R.id.tvNotiTitle, title);
        // collapsedView.setTextViewText(R.id.tvNotiDetails, messageBody);
        //collapsedView.setOnClickPendingIntent(R.id.btnViewDetails, pendingIntent);

        @SuppressLint("RemoteViewLayout") RemoteViews expandedView = new RemoteViews(context.getPackageName(),
                R.layout.notification_expanded);


        expandedView.setTextViewText(R.id.tvNotiDetails, messageBody);

        expandedView.setOnClickPendingIntent(R.id.btnViewDetails, pendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_error)
                /*.setCustomBigContentView(new RemoteViews(context.getPackageName(),
                        R.layout.notification_layout))*/
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                //.setContentTitle(messageBody.concat("title"))
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    // send offer notification
    private void sendOfferNotification(Context context,String sendBy,String sendTo,String title,String messageBody, String tag) {

        Log.d(TAG, "sendNotification: "+sendTo+sendBy);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(tag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("sendBy",sendBy);
        intent.putExtra("sendTo",sendTo);
        editor.putString(GlobalVariables.adminMobile, sendBy);
        editor.putString(GlobalVariables.userMobile, sendTo);
        editor.apply();
        intent.putExtra(GlobalVariables.SCREEN_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.offer_notification_layout);

        collapsedView.setTextViewText(R.id.tvNotiTitle, title);
        collapsedView.setTextViewText(R.id.tvNotiDetails, messageBody);

        collapsedView.setOnClickPendingIntent(R.id.notiContainer, pendingIntent);
        Bitmap bigImage = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slide1);

        NotificationCompat.Builder builder  = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bigImage)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigImage).setBigContentTitle(title));


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    // send message notification
    private void sendMessageNotification(Context context,String sendBy,String sendTo,String title,String messageBody, String tag) {

        Log.d(TAG, "sendNotification: "+sendTo+sendBy);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(tag);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("sendBy",sendBy);
        intent.putExtra("sendTo",sendTo);
        editor.putString(GlobalVariables.adminMobile, sendBy);
        editor.putString(GlobalVariables.userMobile, sendTo);
        editor.apply();
        intent.putExtra(GlobalVariables.SCREEN_TAG, tag);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        Uri defaultSoundUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

        RemoteViews collapsedView = new RemoteViews(context.getPackageName(),
                R.layout.offer_notification_layout);

        collapsedView.setTextViewText(R.id.tvNotiTitle, title);
        collapsedView.setTextViewText(R.id.tvNotiDetails, messageBody);

        collapsedView.setOnClickPendingIntent(R.id.notiContainer, pendingIntent);
        Bitmap bigImage = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slide1);

        NotificationCompat.Builder builder  = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bigImage)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bigImage).setBigContentTitle(title));


        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }


}

