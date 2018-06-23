package kz.kaliolla.moneyconverter.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@Root(name = "Valute")
public class Currency {
    @Attribute(name = "ID")
    private String id;
    @Element(name = "NumCode")
    private long code;
    @Element(name = "CharCode")
    private String label;
    @Element(name = "Nominal")
    private int denomination;
    @Element(name = "Name")
    private String name;
    @Element(name = "Value")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

            this.name = name;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
