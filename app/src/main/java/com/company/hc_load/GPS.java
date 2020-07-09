package com.company.hc_load;
//GPS.java
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import static android.content.Context.LOCATION_SERVICE;


class GPS extends Service implements LocationListener {
    private static boolean gpsEnableState = false;
    private static boolean networkEnableState = false;
    private static Location location;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 위치가 1m 달라졌을 때 정보 갱신
    private static final long MIN_TIME_FOR_UPDATES = 10; // 1분마다 한 번씩 정보 갱신

    private GPS() { }
    private static class LazyHolder {
        private static final GPS INSTANCE = new GPS();
    }

    public static GPS getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void getLocation(Context context) {
        LocationManager locationManager;
        try{
            locationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
            gpsEnableState = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); // GPS On/Off 상태 확인
            networkEnableState = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // Wifi On/Off 상태 확인


            if (!gpsEnableState && !networkEnableState){
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
            } else {
                if (gpsEnableState){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (networkEnableState) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }

            }

            locationManager.removeUpdates(GPS.this);


        } catch (SecurityException ignored){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("위치정보 확인 실패");
            alertDialog.setMessage("위치정보를 확인할 수 없습니다.");
            alertDialog.show();

        } catch (Exception ignored){
            ignored.printStackTrace();
        }
    }

    public static long measure(double curLati, double curLongi, double stnLati, double stnLongi){
        double theta = curLongi - stnLongi;
        double dist = Math.sin(degree2radian(curLati)) * Math.sin(degree2radian(stnLati)) +
                Math.cos(degree2radian(curLati)) * Math.cos(degree2radian(stnLati)) * Math.cos(degree2radian(theta));

        dist = Math.acos(dist);
        dist = radian2degree(dist);
        dist = dist * 60 * 1.1515;
        dist *= 1.609344; // km단위

        return Math.round(dist * 1000);
    }

    private static double degree2radian(double degree) {
        return (degree * Math.PI / 180.0);
    }


    private static double radian2degree(double radian){
        return (radian * 180 / Math.PI);
    }

    public double getLatitute(){
        return location.getLatitude();
    }

    public double getLongitute(){
        return location.getLongitude();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onLocationChanged(Location location) {


    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }
    @Override
    public void onProviderEnabled(String provider) {


    }
    @Override
    public void onProviderDisabled(String provider) {


    }
}