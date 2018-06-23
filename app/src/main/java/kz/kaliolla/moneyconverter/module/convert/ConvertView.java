package kz.kaliolla.moneyconverter.module.convert;

import kz.kaliolla.moneyconverter.model.Data;

public interface ConvertView {
    void showLoading();
    void hideLoading();
    void setData(Data data);
    void showError(String error);
}
