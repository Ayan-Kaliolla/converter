package kz.kaliolla.moneyconverter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import kz.kaliolla.moneyconverter.service.MoneyConverterService;
import kz.kaliolla.moneyconverter.utils.BatteryUtil;
import kz.kaliolla.moneyconverter.utils.ServiceUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final android.net.NetworkInfo wifi = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi != null && wifi.isAvailable()) {
            if (BatteryUtil.isOK(context)) {
                ServiceUtil.startService(context, MoneyConverterService.class);
            }else {
                ServiceUtil.stopService(context, MoneyConverterService.class);
            }
        }
    }
}
