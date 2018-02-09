package com.example.joachanghwa.svoprj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class svogameResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svogameresult);

        String sStr, vStr, oStr;

        svogame sv1 =new svogame();
        sStr=sv1.getResult();

        svogame2 sv2 = new svogame2();
        vStr = sv2.getResult();

        svogame3 sv3 = new svogame3();
        oStr = sv3.getResult();


        TextView sView=(TextView)findViewById(R.id.sView);
        sView.setText(sStr);

        TextView vView=(TextView)findViewById(R.id.vView);
        vView.setText(vStr);

        TextView oView=(TextView)findViewById(R.id.oView);
        oView.setText(oStr);

}

    public void onClickedRetryS(View v){
        String str;
        svogame sv1 =new svogame();
        str = sv1.getResult();

        TextView tv=(TextView)findViewById(R.id.sView);
        tv.setText(str);

    }

    public void onClickedRetryV(View v){
        String str;
        svogame2 sv2=new svogame2();
        str=sv2.getResult();

        TextView tv=(TextView)findViewById(R.id.vView);
        tv.setText(str);

    }

    public void onClickedRetryO(View v){
        String str;
        svogame3 sv3=new svogame3();
        str=sv3.getResult();

        TextView tv=(TextView)findViewById(R.id.oView);
        tv.setText(str);

    }


    public void onClickedReset(View v){
        String sStr, vStr, oStr;

        svogame sv1 =new svogame();
        sStr=sv1.getResult();

        svogame2 sv2 = new svogame2();
        vStr = sv2.getResult();

        svogame3 sv3 = new svogame3();
        oStr = sv3.getResult();


        TextView sView=(TextView)findViewById(R.id.sView);
        sView.setText(sStr);

        TextView vView=(TextView)findViewById(R.id.vView);
        vView.setText(vStr);

        TextView oView=(TextView)findViewById(R.id.oView);
        oView.setText(oStr);


    }

    public void onClickedFinish(View v){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
