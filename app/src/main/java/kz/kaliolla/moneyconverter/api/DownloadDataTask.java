package kz.kaliolla.moneyconverter.api;

import android.os.AsyncTask;
import android.util.Log;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import kz.kaliolla.moneyconverter.model.Data;

public class DownloadDataTask extends AsyncTask<URL, Void, Data> {
    @Override
    protected Data doInBackground(URL... urls) {
        for (int i = 0; i < urls.length; i++) {
            Reader inputStreamReader = null;
            try {
                inputStreamReader = new InputStreamReader(urls[i].openStream(), Charset.forName("windows-1251"));
                Serializer serializer = new Persister();
                try {
                    return serializer.read(Data.class, inputStreamReader);
                } catch (Exception e) {
                    Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e) {
                        Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
                    }
                }
            }
            return null;
        }
        return null;
    }

    private String download(URL url) {
        StringBuilder data = new StringBuilder();
        BufferedReader src = null;
        try {
            src = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String inputLine;
            while ((inputLine = src.readLine()) != null) {
                data.append(inputLine);
            }
        } catch (IOException e) {
            Log.e(DownloadDataTask.class.getName(), e.getMessage(), e);
        } finally {
            if (src != null) {
                try {
                    src.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
}
