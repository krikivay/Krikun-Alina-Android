package com.example.internetlab;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class InternetService extends Service {

    ExecutorService es;

    public void onCreate() {
        super.onCreate();

        es = Executors.newFixedThreadPool(1);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        Check check = new Check();
        //проверка состояния сети происходит в другом потоке
        es.execute(check);

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
    //если сеть появляется либо исчезает, то создается уведомление об этом
    class Check implements Runnable {
        public void run()
        {
            NotificationCompat.Builder builder;
            String text;
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(InternetService.this);
            boolean temp = true;
            if(isOnline(InternetService.this) == temp)
            {
                text = "Соединение присутствует";
                builder = new NotificationCompat.Builder(InternetService.this, "MyNotification channel")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Наличие интернет соединения")
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                notificationManager.notify(1, builder.build());
            }
            while (true) {

                boolean temp1 = isOnline(InternetService.this);
                if(temp1 != temp)
                {
                    notificationManager.cancelAll();
                    temp = temp1;
                    if(temp == true)
                    {
                        text = "Соединение присутствует";
                        builder = new NotificationCompat.Builder(InternetService.this, "MyNotification channel")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Наличие интернет соединения")
                                .setContentText(text)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        notificationManager.notify(1, builder.build());
                    }
                    else
                    {
                        text = "Соединение отсутствует";
                        builder = new NotificationCompat.Builder(InternetService.this, "MyNotification channel")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Наличие интернет соединения")
                                .setContentText(text)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                        notificationManager.notify(2, builder.build());
                    }
                }
            }
        }
    }
    //проверка есть сеть на устройстве или нет
    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }
}
