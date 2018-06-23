package kz.kaliolla.moneyconverter.module.convert;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;

import kz.kaliolla.moneyconverter.R;
import kz.kaliolla.moneyconverter.model.Currency;
import kz.kaliolla.moneyconverter.model.Data;
import kz.kaliolla.moneyconverter.utils.DialogUtil;
import kz.kaliolla.moneyconverter.validators.FormValidators;
import kz.kaliolla.moneyconverter.validators.IsEmptyValidator;
import kz.kaliolla.moneyconverter.validators.LengthValidator;

public class MainActivity extends AppCompatActivity implements ConvertView {

    private AppCompatSpinner currencySpinner;
    private TextInputEditText vCurrency;
    private TextInputLayout currencyLayout;
    private Button convert;
    private Button repeat;
    private View contentLayout;
    private View errorLayout;
    private TextView date;
    private TextView serviceName;
    private TextView errorMessage;

    private TextView result;
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
        currencySpinner.setAdapter(adapter);
        setValidators();
        setListeners();
    }

    private void setValidators() {
        currencyValidators = new FormValidators(vCurrency);
        currencyValidators.addValidator(new IsEmptyValidator());
    }

    private void setListeners() {
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Currency currency = (Currency) parent.getAdapter().getItem(position);
                currencyLayout.setHint(currency.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currencyValidators.validate()) {
                    Currency currency = (Currency) currencySpinner.getSelectedItem();
                    result.setText(new BigDecimal(currency.getValue().replace(',', '.')).multiply(new BigDecimal(vCurrency.getText().toString())) + " " + getString(R.string.result_unit));
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
        currencySpinner = findViewById(R.id.currency);
        vCurrency = findViewById(R.id.currency_value);
        currencyLayout = findViewById(R.id.currency_value_layout);
        convert = findViewById(R.id.convert);
        repeat = findViewById(R.id.repeat);
        errorMessage = findViewById(R.id.error_message);
        contentLayout = findViewById(R.id.content);
        errorLayout = findViewById(R.id.error);
        date = findViewById(R.id.date);
        serviceName = findViewById(R.id.service_name);
        result = findViewById(R.id.result);
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
}
