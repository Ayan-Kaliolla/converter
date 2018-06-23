package kz.kaliolla.moneyconverter.utils;

import android.content.Context;
import android.os.BatteryManager;

import static android.content.Context.BATTERY_SERVICE;

public class BatteryUtil {

    public static boolean isOK(Context context) {
        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        int capacity = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if (capacity > 50) {
            return true;
        }else {
            return false;
        }
    }
}
