package kz.kaliolla.moneyconverter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import kz.kaliolla.moneyconverter.api.DownloadDataTask;
import kz.kaliolla.moneyconverter.model.Data;

public class DataUtil {
    private static final String KEY_DATA = "data";
    private volatile static SharedPreferences sharedPreferences;
    private volatile static Context context;
    public DataUtil(){ }

    public static void init(Context context){
        if (context == null){
            throw new NullPointerException("context not by null");
        }
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        if (DataUtil.context == null){
            DataUtil.context = context;
        }
    }

    public static synchronized void saveData(Data data){
        if (sharedPreferencesIsInitialized()){
            String stringData = objectToXMLString(data);
            if (stringData != null) {
                sharedPreferences.edit().putString(KEY_DATA, stringData).commit();
            }
        }else {
            throw new ExceptionInInitializerError("DataUtil not Initialized");
        }
    }

    public static synchronized Data getData(){
        if (sharedPreferencesIsInitialized()){
            String stringData = sharedPreferences.getString(KEY_DATA, null);
            if (stringData != null){
                return XMLStringToObject(stringData);
            }
            return null;
        }else {
            throw new ExceptionInInitializerError("DataUtil not Initialized");
        }
    }

    private static String objectToXMLString(Data data) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Serializer serializer = new Persister();
        try {
            serializer.write(data, byteArrayOutputStream);
        } catch (Exception e) {
            Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
        }
        return byteArrayOutputStream != null ? byteArrayOutputStream.toString() : null;
    }

    private static Data XMLStringToObject(String data) {
        Serializer serializer = new Persister();
        try {
            return serializer.read(Data.class, new ByteArrayInputStream(data.getBytes()));
        } catch (Exception e) {
            Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
        }
        return null;
    }

    private static boolean sharedPreferencesIsInitialized(){
        if (sharedPreferences == null){
            throw new ExceptionInInitializerError("DataUtil need initialize");
        }
        return true;
    }
}
