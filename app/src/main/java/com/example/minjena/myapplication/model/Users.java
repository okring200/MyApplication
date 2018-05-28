package com.example.minjena.myapplication.model;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Users {
    public void checkUserValidate(String id, String passwd)
    {
        boolean isSuccess = true;
        String result="1";
        try {
            result = new JSONTask().execute("http://13.125.248.74:80",id, passwd).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(result.equals("1"))
            isSuccess = true;
        else
            isSuccess = false;

    }

public class JSONTask extends AsyncTask<String, String, String> {
    protected String doInBackground(String... urls) {
        JSONObject jsonObject = new JSONObject();


        HttpURLConnection conn = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(urls[0]);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
            conn.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
            conn.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
            conn.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
            conn.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
            conn.connect();

            OutputStream outStream = conn.getOutputStream();
            //버퍼를 생성하고 넣음
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();//버퍼를 받아줌

            //서버로 부터 데이터를 받음
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