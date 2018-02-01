package com.example.joachanghwa.svoprj;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class quicksolve extends Activity {

    TextView text01;
    private static final int REQUEST_LOCATION=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quicksolve);

        text01 = (TextView) findViewById(R.id.text01);

        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permissions, int [] grantResults){
        if(requestCode==REQUEST_LOCATION){
            if(grantResults.length==1
                &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
               ;
            }
            else {
                ;// Permission was denied or request was cancelled
            }
        }
    }

    private void getMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        long minTime = 10000;//10초 10000ms
        float minDistance = 0;//거리에 상관없이

        MyLocationListener listener = new MyLocationListener();

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ;//이 권한이 필요한 이유에 대해 설명을 해야 한다.
                    String message = "GPS정보를 이용하여 주변의 음식점을 추천받을 수 있습니다.";

                    DialogInterface.OnClickListener clearListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    };
                    new AlertDialog.Builder(this)
                            .setTitle("projectsvo")
                            .setMessage(Html.fromHtml(message))
                            .setPositiveButton("확인", clearListener)
                            .show();
                } else {

                    ;//권한을 허가 받을 필요가 없는 경우
                }
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);

            } else {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
            }

        }
        else{
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
        }


        appendText("내 위치를 요청했습니다.");
    }
    private void appendText(String msg){
        text01.append(msg+"\n");
    }
    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            appendText("현재 위치:"+latitude + ","+longitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
