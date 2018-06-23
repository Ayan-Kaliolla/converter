package kz.kaliolla.moneyconverter.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

public class ServiceUtil {

    public static void startService(Context context, Class serviceClass){
        if (!serviceIsRunning(context, serviceClass)){
            Intent moneyConverterServiceIntent = new Intent(context.getApplicationContext(), serviceClass);
            context.startService(moneyConverterServiceIntent);
        }
    }

    public static void stopService(Context context, Class serviceClass){
        if (serviceIsRunning(context, serviceClass)){
            Intent moneyConverterServiceIntent = new Intent(context.getApplicationContext(), serviceClass);
            context.stopService(moneyConverterServiceIntent);
        }
    }

    private static boolean serviceIsRunning(Context context, Class serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
