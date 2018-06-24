package kz.kaliolla.moneyconverter.module.convert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import kz.kaliolla.moneyconverter.R;
import kz.kaliolla.moneyconverter.model.Currency;
import kz.kaliolla.moneyconverter.model.Data;
import kz.kaliolla.moneyconverter.utils.DialogUtil;
import kz.kaliolla.moneyconverter.validators.FormValidators;
import kz.kaliolla.moneyconverter.validators.IsEmptyValidator;

public class MainActivity extends AppCompatActivity implements ConvertView {

    private AppCompatSpinner currencyFromSpinner;
    private EditText evCurrencyFrom;
    private AppCompatSpinner currencyToSpinner;
    private EditText evCurrencyTo;

    private Button convert;
    private Button repeat;
    private View contentLayout;
    private View errorLayout;
    private TextView date;
    private TextView serviceName;
    private TextView errorMessage;

    private CurrencyAdapter adapter;
    private ConvertPresenter presenter;

    private FormValidators currencyValidators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter == null) {
            presenter = new ConvertPresenterImpl(this, this);
        }
        presenter.loadData();
    }

    private void initView() {
        adapter = new CurrencyAdapter(this);
        currencyFromSpinner.setAdapter(adapter);
        currencyToSpinner.setAdapter(adapter);
        setValidators();
        setListeners();
    }

    private void setValidators() {
        currencyValidators = new FormValidators(evCurrencyFrom);
        currencyValidators.addValidator(new IsEmptyValidator());
    }

    private void setListeners() {
        currencyFromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        currencyToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currencyValidators.validate()) {
                    Currency fromCurrency = (Currency) currencyFromSpinner.getSelectedItem();
                    Currency toCurrency = (Currency) currencyToSpinner.getSelectedItem();
                    BigDecimal rateFrom = new BigDecimal(fromCurrency.getValue().replace(',', '.')).divide(new BigDecimal(fromCurrency.getDenomination()));
                    BigDecimal rateTo = new BigDecimal(toCurrency.getValue().replace(',', '.')).divide(new BigDecimal(toCurrency.getDenomination()));
                    BigDecimal strResult = new BigDecimal(evCurrencyFrom.getText().toString()).multiply(rateFrom).divide(rateTo, 2, RoundingMode.HALF_DOWN);
                    evCurrencyTo.setText(strResult.toString());
                }
            }
        });
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loadData();
            }
        });
    }

    private void bindView() {
        currencyFromSpinner = findViewById(R.id.currency_from_spinner);
        currencyToSpinner = findViewById(R.id.currency_to_spinner);
        evCurrencyFrom = findViewById(R.id.currency_from_value);
        evCurrencyTo = findViewById(R.id.currency_to_value);
        convert = findViewById(R.id.convert);
        repeat = findViewById(R.id.repeat);
        errorMessage = findViewById(R.id.error_message);
        contentLayout = findViewById(R.id.content);
        errorLayout = findViewById(R.id.error);
        date = findViewById(R.id.date);
        serviceName = findViewById(R.id.service_name);
    }

    @Override
    public void showLoading() {
        DialogUtil.showProgressDialog(this, getString(R.string.load_dialog_message));
    }

    @Override
    public void hideLoading() {
        DialogUtil.hideProgressDialog();
    }

    @Override
    public void setData(Data data) {
        errorLayout.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
        date.setText(data.getDate());
        serviceName.setText(data.getName());
        adapter.setCurrency(data.getCurrency());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        errorLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        if (error != null) {
            errorMessage.setText(error);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.clear();
            presenter = null;
        }
    }
}
