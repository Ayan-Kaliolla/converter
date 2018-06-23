package kz.kaliolla.moneyconverter.validators;

import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class LengthValidator implements Validator<EditText> {
    private int min;
    private int max;

    public LengthValidator(int min, int max) {
        if (min < 0 || min > max){
            throw new IllegalArgumentException();
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validate(EditText view) {
        int length = view.getText().length();
        if (length < min){
            view.setError("размер не может быть менше " + min);
            return false;
        }else if (length > max){
            view.setError("размер не может быть больше " + min);
            return false;
        }
        return true;
    }
}
