package kz.kaliolla.moneyconverter.module.convert;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import kz.kaliolla.moneyconverter.R;
import kz.kaliolla.moneyconverter.model.Data;
import kz.kaliolla.moneyconverter.service.MoneyConverterService;

class ConvertPresenterImpl implements ConvertPresenter, ServiceConnection {
    private ConvertView view;
    private Context context;
    private ServiceConnection connection;
    private boolean isConnected = false;
    private MoneyConverterService service;

    public ConvertPresenterImpl(Context context, ConvertView view) {
        this.context = context;
        this.view = view;
        connectionToService();
    }

    private void connectionToService() {
        Intent moneyConverterServiceIntent = new Intent(context, MoneyConverterService.class);
        isConnected = context.bindService(moneyConverterServiceIntent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void loadData() {
        view.showLoading();
        if (!isConnected || service == null) {
            connectionToService();
        }else {
            initData(this.service.loadData());
        }
    }

    private void initData(Data data) {
        if (data != null) {
            view.hideLoading();
            view.setData(data);
        } else {
            view.hideLoading();
            view.showError(context.getString(R.string.network_error));
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = ((MoneyConverterService.MoneyConverterServiceBinder) service).getService();
        initData(this.service.loadData());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        isConnected = false;
    }

    public void clear() {
        if (isConnected) {
            isConnected = false;
            context.unbindService(this);
        }
    }
}
