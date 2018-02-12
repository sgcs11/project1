package com.example.joachanghwa.svoprj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivity);

    }

    public void onClicked1(View v){
       Intent intent= new Intent(this,MainActivity.class) ;
        startActivity(intent);
    }

}
