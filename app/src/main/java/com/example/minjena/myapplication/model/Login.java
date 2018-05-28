package com.example.minjena.myapplication.model;

import android.os.AsyncTask;

import com.example.minjena.myapplication.presenter.ILoginPresenter;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Login {
    ILoginPresenter iLoginPresenter;

    public Login(ILoginPresenter iLoginPresenter)
    {
        this.iLoginPresenter = iLoginPresenter;
    }

    public void doLogin(String id, String passwd)
    {
        String result="1";
        try {
            result = new JSONTask().execute("http://13.125.248.74:80/user/login/?",id, passwd).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        iLoginPresenter.validation(result);
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {

            HttpURLConnection conn = null;
            BufferedReader reader = null;
            ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("id",urls[1]));
            list.add(new BasicNameValuePair("passwd",urls[2]));
            final String urlPath = urls[0]+ URLEncodedUtils.format(list, "UTF-8");
            try {
                URL url = new URL(urlPath);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream stream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
