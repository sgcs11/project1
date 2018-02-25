package com.JOA.joachanghwa.svoprj;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class quicksolve extends AppCompatActivity {

    LocationManager manager;
    MyLocationListener listener;

    public static double latitude;
    public static double longitude;
    public static double prelat=0;
    public static double prelong=0;
    public static int btn_number;
    public static int pre_btn_number;
    private EditText edt;
    public static String edt_text;
    private static final int REQUEST_LOCATION=2;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;

    private boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("주변 먹거리 검색");
        setContentView(R.layout.activity_quicksolve);

        Button button01 = (Button) findViewById(R.id.button01);
        Button button02 = (Button) findViewById(R.id.button02);
        Button button03 = (Button) findViewById(R.id.button03);
        Button button04 = (Button) findViewById(R.id.button04);

        edt=(EditText)findViewById(R.id.editText01);

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//뭐 먹을까?
                getMyLocation();
                btn_number=1;
            }
        });
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
                btn_number=2;
            }
        });
        button03.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getMyLocation();
                btn_number=3;
            }
        });
        button04.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                getMyLocation();
                btn_number=4;
                edt_text=edt.getText().toString();
              //  Toast.makeText(getApplicationContext(),edt_text,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getMyLocation() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        flag=false;

        gps_enabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);//GPS 이용가능 여부
        network_enabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);//Network 이용가능 여부

        long minTime = 3000;//10초 10000ms
        float minDistance = 10;//거리에 상관없이

        listener = new MyLocationListener();

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    ;//이 권한이 필요한 이유에 대해 설명을 해야 한다.
                    String message = "GPS정보를 이용하여 주변의 음식점을 추천받을 수 있습니다. 기능을 사용하려면 환경설정에서 GPS 권한을 설정해주십시오.";

                    DialogInterface.OnClickListener clearListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                        }
                    };
                    new AlertDialog.Builder(this)
                            .setTitle("GPS 정보 수신")
                            .setMessage(Html.fromHtml(message))
                            .setPositiveButton("확인", clearListener)
                            .show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
                    //권한을 허가 받을 필요가 없는 경우
                }


            } else {
                    manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
                    manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
            }

        }
        else{
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, listener);
        }
    }
    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            manager.removeUpdates(listener);
            if(flag==false) {
                flag=true;
                Intent intent = new Intent(getApplicationContext(), crawling.class);
                startActivity(intent);
            }
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
    @Override
    public void onRequestPermissionsResult(int requestCode,String [] permissions, int [] grantResults){
        if(requestCode==REQUEST_LOCATION){
            if(grantResults.length>0
                    &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(getApplicationContext(),permissionflag,Toast.LENGTH_SHORT).show();
            }
            else {
                // Permission was denied or request was cancelled
            }
        }
    }
}
