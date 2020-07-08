import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class Tmap {
    public Bitmap mapImage;

    final String appkey = "l7xx10772ddf0cda4d6faf1bd54eba44d066";

    Tmap(final double startY, final double startX, final double endY, final double endX) {//Y 위도 X 경도
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://apis.openapi.sk.com/tmap/routeStaticMap?appKey="+appkey
                            + "&endX=" + Double.toString(endX)
                            + "&endY=" + Double.toString(endY)
                            + "&startX=" + Double.toString(startX)
                            + "&startY=" + Double.toString(startY)
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
            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}