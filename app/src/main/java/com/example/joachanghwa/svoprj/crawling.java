package com.example.joachanghwa.svoprj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class crawling extends AppCompatActivity {

    private String htmlPageUrl = "https://m.store.naver.com/restaurants/listMap?level=middle&query=%EB%A7%9B%EC%A7%91&searchQuery=%EB%A7%9B%EC%A7%91&sortingOrder=precision&x="; //파싱할 홈페이지의 URL주소
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";

    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling);

        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기

        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlPageUrl+= quicksolve.latitude+"&y="+ quicksolve.longitude;
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
                cnt++;
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();

                //테스트1
                Elements items= doc.select("li.list_item.type_restaurant");
                Elements items1=items.select("span.name._name");
                Elements items2=items.select("span.category._category");
                Elements items3=items.select("div.info_inner").select("div.txt");

                System.out.println("-------------------------------------------------------------");
                for(Element e: items1){
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }
                for(Element e: items2){
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }
                for(Element e: items3){
                    htmlContentInStringFormat += e.text().trim() + "\n";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }


}
