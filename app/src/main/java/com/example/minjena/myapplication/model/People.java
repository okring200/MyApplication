package com.example.minjena.myapplication.model;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.minjena.myapplication.presenter.IPeoplePresenter;

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

public class People {

    IPeoplePresenter iPeoplePresenter;
    Bundle arg;

    public People(Bundle arg)
    {
        this.arg = arg;
    }

    public ArrayList<String> getPeopleList()
    {
        ArrayList<String> result = new ArrayList<String>();
        String key = arg.getString("key");
        try {
            result = new People.JSONTask().execute(key).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public class JSONTask extends AsyncTask<String, String, ArrayList<String>> {
        protected ArrayList<String> doInBackground(String... list) {
            JSONObject ob = new JSONObject();
            try {
                ob.accumulate("idx",list[0].toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try{
                URL url = new URL("http://13.125.248.74:80/user/fList");
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

                InputStream stream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                //-------------------------------------------------친구목록 불러오기
                String friends = buffer.toString();
                ArrayList<String> resultList = new ArrayList<String>();
                if(friends.equals(""))
                    return resultList;
                url = new URL("http://13.125.248.74:80/user/list");
                ob = new JSONObject();

                try {
                    ob.accumulate("friends",friends);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Cache-Control", "no-cache");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setConnectTimeout(60);
                conn.connect();

                os = conn.getOutputStream();
                writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(ob.toString());
                writer.flush();
                writer.close();
                os.close();

                stream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();

                String temp;
                String[] tempList;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                temp = buffer.toString();
                tempList = temp.split(",");
                for(int i = 0; i < tempList.length; i++)
                    resultList.add(tempList[i]);

                return resultList;
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
