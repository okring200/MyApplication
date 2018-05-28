package com.example.minjena.myapplication.model;

import android.os.AsyncTask;

import com.example.minjena.myapplication.presenter.ISubmitPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Submit {
    ISubmitPresenter iSubmitPresenter;

    public Submit(ISubmitPresenter iSubmitPresenter)
    {
        this.iSubmitPresenter = iSubmitPresenter;
    }

    /*public boolean validation(ArrayList<String> list)
    {

        return true;
    }*/
    public void doSubmit(ArrayList list) {
        String result = "0";
        try {
            result = new JSONTask().execute(list).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        iSubmitPresenter.isSuccesss(result);
    }
    public class JSONTask extends AsyncTask<ArrayList, String, String> {
        protected String doInBackground(ArrayList... list) {
            JSONObject ob = new JSONObject();
            try {
                ob.accumulate("id",list[0].get(0).toString());
                ob.accumulate("passwd",list[0].get(1).toString());
                ob.accumulate("mname",list[0].get(2).toString());
                ob.accumulate("phone",list[0].get(3).toString());
                ob.accumulate("email",list[0].get(4).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try{
                URL url = new URL("http://13.125.248.74:80/user/signup");
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setConnectTimeout(60);
                conn.connect();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(ob.toString());
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

                InputStream stream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
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
