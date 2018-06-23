package kz.kaliolla.moneyconverter.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URL;
import java.util.Date;

import kz.kaliolla.moneyconverter.api.DownloadDataTask;
import kz.kaliolla.moneyconverter.model.Data;
import kz.kaliolla.moneyconverter.utils.DataUtil;
import kz.kaliolla.moneyconverter.utils.NetworkUtil;

import static kz.kaliolla.moneyconverter.api.Constants.BASE_URL;
import static kz.kaliolla.moneyconverter.api.Constants.PATH_DATA;

public class MoneyConverterService extends Service {
    private static final Long CONST_TIME_UPDATE_LIMIT = 3 * 1000L;
    private static Date lastUpdateTime;
    private MoneyConverterServiceBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        DataUtil.init(getApplicationContext());
        binder = new MoneyConverterServiceBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int status = super.onStartCommand(intent, flags, startId);
        Data data = null;
        try {
            data =  new DownloadDataTask().execute(new URL(BASE_URL + PATH_DATA)).get();
            if (data != null) {
                DataUtil.saveData(data);
            }
        } catch (Exception e) {
            Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
        }
        return status;
    }

    public Data loadData() {
        if (NetworkUtil.isAvailable(getApplicationContext()) && (lastUpdateTime == null || (lastUpdateTime != null && (new Date().getTime() - lastUpdateTime.getTime() > CONST_TIME_UPDATE_LIMIT)))) {
            try {
                return new DownloadDataTask().execute(new URL(BASE_URL + PATH_DATA)).get();
            } catch (Exception e) {
                Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
                return DataUtil.getData();
            }
        } else {
            return DataUtil.getData();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MoneyConverterServiceBinder extends Binder {
        public MoneyConverterService getService() {
            return MoneyConverterService.this;
        }
    }
}
