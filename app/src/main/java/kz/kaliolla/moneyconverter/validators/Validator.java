package kz.kaliolla.moneyconverter.validators;

import android.widget.EditText;


public interface Validator<T extends EditText> {
    boolean validate(T view);
}
