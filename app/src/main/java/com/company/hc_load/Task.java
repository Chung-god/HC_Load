package com.company.hc_load;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;

import javax.xml.transform.Result;

class storeInfo{
    public String longitude = null; //경도  X
    public String latitude = null; //위도 Y
    public String storeName = null; //가게이름
    public String foodType = null; //음식 분류
    public String storeAddress = null; //주소



    storeInfo(JSONObject data){
        try {
            longitude = data.getString("lon");
            latitude = data.getString("lat");
            storeName = data.getString("bizesNm");
            foodType = data.getString("indsMclsNm");
            storeAddress = data.getString("rdnmAdr");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
    void printJson(){
        System.out.println("longitude: " + this.longitude + "\n" +"latitude: " +this.latitude + "\n"
                +"storeName: " + this.storeName + "foodType: "+this.foodType + "storeAddress:" + this.storeAddress +"\n" );
    }
}


public class Task{
    final String appkey = "jkNa6bIdA9c%2BX3s63rN6NdmxD2C4jDkjTsrPaVT8dDdk9lqgBQc6QdpB0%2FgDReD6wuEMNL3iH7zDxmuHQdOIaw%3D%3D";
    private String str, receiveMsg;
    final Double cxNampo = 129.032850;
    final Double cyNampo = 35.099294;
    final Double cxSeomyun = 129.059103;
    final Double cySeomyun = 35.157751;
    final Double cxPNU = 129.086408;
    final Double cyPNU = 35.230890;

    ArrayList<storeInfo> storeInfoArr = new ArrayList<storeInfo>();
    ArrayList<storeInfo> playInfoArr = new ArrayList<storeInfo>();
    ArrayList<storeInfo> cafeInfoArr = new ArrayList<storeInfo>();

    storeInfo a;
    storeInfo b;
    storeInfo c;



    public Object Task(final String place,final int number){
        Thread mThread = new Thread(){
            Double cx =cxNampo;
            Double cy = cyNampo;

            @Override
            public void run(){
                if( place =="Nampo"){
                    cx = cxNampo;
                    cy = cyNampo;
                }else if(place == "Seomyun"){
                    cx = cxSeomyun;
                    cy = cySeomyun;
                }else if(place == "PNU"){
                    cx = cxPNU;
                    cy = cyPNU;
                }
                try{
                    URL url;
                    String str = "";
                    url = new URL("http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?type=json&radius=500"
                    + "&cx=" + Double.toString(cx)
                    + "&cy=" + Double.toString(cy)
                    + "&ServiceKey=" + appkey);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                    int i = conn.getResponseCode();

                    if (i== conn.HTTP_OK) {
                        InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                        BufferedReader reader = new BufferedReader(tmp);
                        StringBuffer buffer = new StringBuffer();
                        while ((str = reader.readLine()) != null) {
                            buffer.append(str);
                        }
                        receiveMsg = buffer.toString();
                        reader.close();
                    } else {
                        Log.i("통신 결과", conn.getResponseCode() + "에러");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        mThread.start(); // Thread 실행
        try {
            mThread.join();
            placejsonParser(receiveMsg);
            if(number.equals(1)) {
                return SelectCourse(storeInfoArr);
            }
            if(number.equals(2)) {
                return SelectCourse(playInfoArr);
            }
            if(number.equals(3)){
                return SelectCourse(cafeInfoArr);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // return receiveMsg;
    }


    public void placejsonParser(String jsonString) {
        //System.out.print("PlacejsonParser");


        try {
            JSONArray jarray = new JSONObject(jsonString).getJSONObject("body").getJSONArray("items");


            for (int i = 0; i < jarray.length(); i++) {
                String t = jarray.getJSONObject(i).getString("indsLclsNm");
                if (jarray.getJSONObject((i)).getString("indsMclsNm").equals("커피점/카페")) {
                    storeInfoArr.add(new storeInfo(jarray.getJSONObject(i)));
                }else if(t.equals("소매")){
                    playInfoArr.add(new storeInfo(jarray.getJSONObject(i)));
                }else if(t.equals("음식")){
                    cafeInfoArr.add(new storeInfo(jarray.getJSONObject(i)));
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public storeInfo SelectCourse(ArrayList<storeInfo> storeInfoArr){
        int n =  storeInfoArr.size();
        int random = (int) ((Math.random() * 1000 )% n);
        return storeInfoArr.get(random);
    }
}


