package kz.kaliolla.moneyconverter.module.convert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kz.kaliolla.moneyconverter.model.Currency;

public class CurrencyAdapter extends BaseAdapter{

    private List<Currency> items;
    private Context context;
    private LayoutInflater inflater;

    public CurrencyAdapter(Context context) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCurrency(List<Currency> currencies){
        items = currencies;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items != null ? items.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        Currency currency = (Currency) getItem(position);
        if (currency != null) {
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(currency.getLabel());
        }
        return convertView;
    }
}
