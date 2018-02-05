package com.example.joachanghwa.svoprj;

import android.app.Application;

/**
 * Created by DK on 2018-02-02.
 */

public class globalValueActivity extends Application {

    private String gValue;

    @Override
    public void onCreate(){

        gValue = "";
        super.onCreate();
    }

    @Override
    public void onTerminate(){

        super.onTerminate();
    }

    // 다른 class에서 전역변수 참조
    public String getGlobalValue(){

        return gValue;
    }

    // 다른 class에서 변경한 값을 전역변수에 저장
    public void setGlobalValue(String mValue){

        this.gValue = "" + mValue;
    }

}
