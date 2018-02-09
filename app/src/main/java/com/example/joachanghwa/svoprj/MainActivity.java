package com.example.joachanghwa.svoprj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClicked1(View v){
        Intent intent=new Intent(getApplicationContext(),quicksolve.class);
        startActivity(intent);

    }
    public void onClicked2(View v){
        Intent intent=new Intent(getApplicationContext(),quickteam.class);
        startActivity(intent);

    }
    public void onClicked3(View v){
        Intent intent=new Intent(getApplicationContext(),svogame.class);
        startActivity(intent);

    }
    public void onClicked4(View v){
        Intent intent=new Intent(getApplicationContext(),directinput.class);
        startActivity(intent);

    }

}
