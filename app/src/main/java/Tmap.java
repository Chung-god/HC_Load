
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;



public class Tmap {
    public Bitmap mapImage;
    public int time1;
    public int distance1;
    public int time2;
    public int distance2;

    final private String appkey = "l7xx10772ddf0cda4d6faf1bd54eba44d066";
    private String receiveMsg = "";

    Tmap(final double x1, final double y1, final double x2, final double y2, final double x3, final double y3) {
        route(x1,y1, x2, y2, 1); // 경로 1~2 사이 거리/시간 구하기
        route(x1,y1, x2, y2, 2); // 경로 2~3 사이 거리/시간 구하기
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://apis.openapi.sk.com/tmap/routeStaticMap?appKey=" + appkey
                            + "&endX=" + Double.toString(x3)
                            + "&endY=" + Double.toString(y3)
                            + "&startX=" + Double.toString(x1)
                            + "&startY=" + Double.toString(y1)
                            + "&passList=" + Double.toString(x2) + "," + Double.toString(y2)
                            + "&version=1");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();
                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    mapImage = BitmapFactory.decodeStream(is); // Bitmap으로 변환
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String route(final double x1, final double y1, final double x2, final double y2, int type) {
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url;
                    String str = "";
                    url = new URL("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&format=json");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    conn.setDoOutput(true);
                    JSONObject json = new JSONObject();
                    String body = "";
                    body += "appKey="+ appkey;
                    body += "&startX=" + Double.toString(x1);
                    body += "&startY=" + Double.toString(y1);
                    body += "&endX=" + Double.toString(x2);
                    body += "&endY=" + Double.toString(y2);
                    body += "&startName=" + "start";
                    body +="&endName=" +"end";


                    OutputStream os = conn.getOutputStream();
                    os.write(body.getBytes("euc-kr"));
                    os.flush();
                    os.close();

                    if (conn.getResponseCode() == conn.HTTP_OK) {
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
            JSONObject jarray = new JSONObject(receiveMsg).getJSONArray("features").getJSONObject(0);
            jarray = jarray.getJSONObject("properties");
            if(type ==1){
                distance1 = jarray.getInt("totalDistance");
                time1 = jarray.getInt("totalTime");
            }
            else if(type ==2) {
                distance2 = jarray.getInt("totalDistance");
                time2 = jarray.getInt("totalTime");
            }
            else{

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return receiveMsg;
    }
}