package com.example.joachanghwa.svoprj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class crowlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowl);

        final TextView crowlText = (TextView) findViewById(R.id.textCrowl);

        String resURL = "https://m.store.naver.com/restaurants/listMap?back=false&level=bottom&query=%EB%A7%9B%EC%A7%91&searchQuery=%EB%A7%9B%EC%A7%91&sortingOrder=distance&";
        globalValueActivity gValue = (globalValueActivity)getApplicationContext();
        final String resURL2 = resURL + gValue.getGlobalValue();

        new Thread(new Runnable(){
            @Override
            public void run() {

                // 크롤링 성공시 크롤링 내역 출력
                try{
                    // url 주소에 연결
                    Connection.Response response = Jsoup.connect(resURL2)
                            .method(Connection.Method.GET).execute();

                    // 연결된 주소를 파싱하여 텍스트를 크롤링
                    Document document = response.parse();
                    Element restaurants = document.select("li").first();
                    Element resName1 = restaurants.selectFirst("a").selectFirst("div").selectFirst("span").select("span").get(1);
                    Element resName2 = restaurants.selectFirst("a").selectFirst("div").selectFirst("span").select("span").get(2);
                    final String text = resName1.text() + ", " + resName2.text();

                    //int i = 0;
                    //final String[] resArray = new String[25];

                    /*for(Element resLi : restaurants){
                        Element resName1 = resLi.selectFirst("a").selectFirst("div").selectFirst("span").select("span").get(1);
                        Element resName2 = resLi.selectFirst("a").selectFirst("div").selectFirst("span").select("span").get(2);

                        resArray[i] = resName1.text() + ", " + resName2.text();
                        i++;
                        if(i == 20)
                            break;
                    }*/

                    /*String ansTemp = "";
                    for(i = 0; i < resArray.length; i++){
                        ansTemp += resArray[i] + "\r\n";
                    }
                    final String ans = ansTemp;*/


                    /*Element resA = restaurants.select("a").first();
                    Element resDiv = resA.select("div").first();
                    Element resSpan = resDiv.select("span").first();
                    Element resName = resSpan.select("span").get(1);
                    Element resName2 = resSpan.select("span").get(2);
                    final String text = resName.text() + ", " + resName2.text();*/


                    /*Element resDiv = restaurants.select("div").first();
                    Element resName = resDiv.select("span").first();*/

                    //final String text = resName.attr("class");



                    // 크롤링한 텍스트를 출력
                    crowlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            crowlText.setText(text);
                        }
                    });
                }

                // 크롤링 도중 에러가 발생했을 경우, 에러메시지를 출력
                catch(Exception e){
                    crowlText.setText(e.getMessage());
                }
            }

        }).start();
    }
}