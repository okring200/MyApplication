package com.example.minjena.myapplication.model;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.minjena.myapplication.presenter.IChatPresenter;

import org.json.JSONArray;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Chat {

    Bundle arg;

    public Chat(Bundle arg){ this.arg = arg; }

    public ArrayList<RecyclerChat> getChatList()
    {
        ArrayList<RecyclerChat> result = new ArrayList<RecyclerChat>();
        String key = arg.getString("rkey");
        try {
            result = new Chat.JSONTask().execute(key).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public class JSONTask extends AsyncTask<String, String, ArrayList<RecyclerChat>> {

        protected ArrayList<RecyclerChat> doInBackground(String... list) {
            JSONObject ob = new JSONObject();
            try {
                ob.accumulate("ridx",list[0].toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            BufferedReader reader = null;
            try{
                URL url = new URL("http://13.125.248.74:80/chat/cList");
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

                ArrayList<RecyclerChat> resultList = new ArrayList<RecyclerChat>();
                String mJsonString = buffer.toString().trim();
                JSONArray jsonArray = new JSONArray(mJsonString);
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject item = jsonArray.getJSONObject(i);
                    String ridx = String.valueOf(item.getInt("room_idx"));
                    String fidx = String.valueOf(item.getInt("from_idx"));
                    //String d = item.getString("reg_date");
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    //java.util.Date to = sdf.parse(d);
                    String msg = item.getString("msg_json");
                    RecyclerChat temp = new RecyclerChat(ridx,fidx,msg);
                    resultList.add(temp);
                }

                return resultList;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
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
