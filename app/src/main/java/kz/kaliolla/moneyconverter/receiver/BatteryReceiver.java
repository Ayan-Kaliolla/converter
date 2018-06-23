package kz.kaliolla.moneyconverter.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import kz.kaliolla.moneyconverter.service.MoneyConverterService;
import kz.kaliolla.moneyconverter.utils.ServiceUtil;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float)scale;
        if (batteryPct > 50) {
            ServiceUtil.startService(context, MoneyConverterService.class);
        }else {
            ServiceUtil.stopService(context, MoneyConverterService.class);
        }
    }




}
