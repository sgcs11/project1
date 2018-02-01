package com.example.joachanghwa.svoprj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ResultView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String str;
        directinput di=new directinput();
        str=di.getResult();

        TextView tv=(TextView)findViewById(R.id.resultView);
        tv.setText(str);
    }

    public void onClickedRetry(View v){
        String str;
        directinput di=new directinput();
        str=di.getResult();

        TextView tv=(TextView)findViewById(R.id.resultView);
        tv.setText(str);
    }
    public void onClickedReturn(View v){
       finish();
    }

}
