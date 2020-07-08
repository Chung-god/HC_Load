package com.company.hc_load;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;

public class Task extends AsyncTask{
    final String appkey = "jkNa6bIdA9c%2BX3s63rN6NdmxD2C4jDkjTsrPaVT8dDdk9lqgBQc6QdpB0%2FgDReD6wuEMNL3iH7zDxmuHQdOIaw%3D%3D";
    private String str, receiveMsg;

    final Double cxNampo = 129.032850;
    final Double cyNampo = 35.099294;
    final Double cxSeomyun = 129.059103;
    final Double cySeomyun = 35.157751;
    final Double cxPNU = 129.086408;
    final Double cyPNU = 35.230890;

    @Override
    protected Object doInBackground(final Object[] objects) {
        Double cx =cxNampo;
        Double cy = cyNampo;
        if(objects[0] =="Nampo"){
                    cx = cxNampo;
                    cy = cyNampo;
                }else if(objects[0] == "Seomyun"){
                    cx = cxSeomyun;
                    cy = cySeomyun;
                }else if(objects[0] == "PNU"){
                    cx = cxPNU;
                    cy = cyPNU;
        }
        try {
            URL url = new URL("http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?radius=500"
                    + "&cx=" + Double.toString(cx)
                    + "&cy=" + Double.toString(cy)
                    + "&ServiceKey=" + appkey
                    + "&type=json"
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            //conn.setRequestProperty("x-waple-authorization", clientKey);

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }


}
