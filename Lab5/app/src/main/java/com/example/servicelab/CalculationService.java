package com.example.servicelab;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//сервис для вычислений
public class CalculationService extends Service {
    ExecutorService es;

    public void onCreate() {
        super.onCreate();
        es = Executors.newFixedThreadPool(1);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        double num1 = 0, num2 = 0;
        PendingIntent pendingIntent = intent.getParcelableExtra("pending intent");
        //получение 1 числа
        try
        {
            num1 = Double.valueOf(intent.getStringExtra("num1"));
        }
        catch (Exception ex)
        {
            Intent intent1 = new Intent().putExtra("num1error", "Ошибка при введении 1 числа");
            try {
                pendingIntent.send(CalculationService.this, -1, intent1);
            } catch (PendingIntent.CanceledException e) {}
            stopSelfResult(startId);
        }
        //получение 2 числа
        try
        {
            num2 = Double.valueOf(intent.getStringExtra("num2"));
        }
        catch (Exception ex)
        {
            Intent intent1 = new Intent().putExtra("num2error", "Ошибка при введении 2 числа");
            try {
                pendingIntent.send(CalculationService.this, -2, intent1);
            } catch (PendingIntent.CanceledException e) {}
            stopSelfResult(startId);
        }
        //получение операнда
        String operand = (intent.getStringExtra("operand"));
        //заупуск вычислений в другом потоке
        Calculation calculation = new Calculation(num1, num2, operand, pendingIntent);
        es.execute(calculation);

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    class Calculation implements Runnable {
        double num1, num2;
        String operand;
        PendingIntent pendingIntent;

        Calculation(double num1, double num2, String operand, PendingIntent pendingIntent)
        {
            this.num1 = num1;
            this.num2 = num2;
            this.operand = operand;
            this.pendingIntent = pendingIntent;
        }

        public void run()
        {
            Intent intent;
            double result = 0.0;
            //вычисление результата
            switch(operand)
            {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                case "^":
                    result = Math.pow(num1, num2);
                    break;
                default:
                    Intent intent1 = new Intent().putExtra("operanderror", "Ошибка при введении операнда");
                    try {
                        pendingIntent.send(CalculationService.this, -3, intent1);
                    } catch (PendingIntent.CanceledException e) {}
                    stopSelf();
                    break;
            }
            //передача результата в активность
            intent = new Intent().putExtra("result", result);
            try {
                pendingIntent.send(CalculationService.this, 0, intent);
            } catch (PendingIntent.CanceledException e) {}
            stopSelf();
        }
    }

}
