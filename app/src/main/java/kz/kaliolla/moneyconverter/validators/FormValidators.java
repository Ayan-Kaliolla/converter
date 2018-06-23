package kz.kaliolla.moneyconverter.validators;

import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FormValidators<T extends EditText> {
    private List<Validator> validators;
    private T view;

    public FormValidators(T view){
        this.view = view;
        validators = new LinkedList<>();
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ((T) v).setError(null);
            }
        });
    }

    public void addValidator(Validator validator){
        if (validator != null) {
            validators.add(validator);
        }
    }

    public boolean validate(){
        for (Validator validator : validators){
            if (!validator.validate(view)) {
                return false;
            }
        }
        return true;
    }
}
