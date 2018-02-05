package com.example.joachanghwa.svoprj;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class quicksolve extends AppCompatActivity {

    TextView whereText;
    private static final int REQUEST_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quicksolve);

        whereText = (TextView) findViewById(R.id.whereText);

        Button whereButton = (Button) findViewById(R.id.whereButton);
        whereButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                startLocationService();
            }
        });

        Button crawlButton = (Button) findViewById(R.id.crawlButton);
        crawlButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),crowlActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permissions, int [] grantResults){
        if(requestCode==REQUEST_LOCATION){
            if(grantResults.length==1
                    &&grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
            else {
                // Permission was denied or request was cancelled
            }
        }
    }

    public void startLocationService() {

        long minTime = 5000; // 5초 후 위치정보 갱신
        float minDistance = 0;

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // 권한이 필요한 이유에 대해 설명
                String message = "GPS를 이용하여 주변의 식당을 검색합니다.";

                DialogInterface.OnClickListener checkListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                    }
                };

                new AlertDialog.Builder(this).
                        setTitle("projectsvo")
                        .setMessage(Html.fromHtml(message))
                        .setPositiveButton("확인", checkListener)
                        .show();
            }

            else {
                // 권한을 허가받을 필요가 없는 경우
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        }

        else{
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            Double latitude = location.getLatitude();
                            Double longitude = location.getLongitude();

                            // 전역변수에 gps 좌표값 저장
                            String longiLati = "x=" + Double.toString(longitude) + "&y=" + Double.toString(latitude);
                            globalValueActivity gValue = (globalValueActivity) getApplicationContext();
                            gValue.setGlobalValue(longiLati);

                            whereText.setText("위도 : " + latitude + "\r\n경도 : " + longitude);


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
                    });
        }
    }
}