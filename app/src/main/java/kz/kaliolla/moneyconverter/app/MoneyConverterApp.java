package kz.kaliolla.moneyconverter.app;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import kz.kaliolla.moneyconverter.receiver.BatteryReceiver;
import kz.kaliolla.moneyconverter.receiver.NetworkChangeReceiver;
import kz.kaliolla.moneyconverter.service.MoneyConverterService;
import kz.kaliolla.moneyconverter.utils.ServiceUtil;

public class MoneyConverterApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startMoneyConverterService();
        registerBatteryReceiver();
        registerNetworkChangeReceiver();
    }

    private void startMoneyConverterService() {
        ServiceUtil.startService(getApplicationContext(), MoneyConverterService.class);
    }

    private void registerBatteryReceiver(){
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getApplicationContext().registerReceiver(new BatteryReceiver(), filter);
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getApplicationContext().registerReceiver(new NetworkChangeReceiver(), filter);
    }
}
