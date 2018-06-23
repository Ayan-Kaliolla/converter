package kz.kaliolla.moneyconverter.validators;

import android.widget.EditText;

public class IsEmptyValidator implements Validator<EditText> {
    @Override
    public boolean validate(EditText view) {
        if(view.getText().toString().isEmpty()){
            view.setError("Поле не может быть пустым");
            return false;
        }
        return true;
    }
}
