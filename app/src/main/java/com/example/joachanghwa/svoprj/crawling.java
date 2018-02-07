package com.example.joachanghwa.svoprj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class crawling extends AppCompatActivity {

    private String htmlPageUrl1 = "https://m.store.naver.com/restaurants/listMap?level=middle&query=%EB%A7%9B%EC%A7%91&searchQuery=%EB%A7%9B%EC%A7%91&sortingOrder=precision&x="; //파싱할 홈페이지의 URL주소
    private String htmlPageUrl="";
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";
    private List<String> nameList=new ArrayList<String>();
    private List<String> explainList=new ArrayList<String>();
    private List<String> concreteList=new ArrayList<String>();
    private List<String> LinkList=new ArrayList<>();
    private WebView webv;
    int cnt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crawling);

        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기
        webv=(WebView)findViewById(R.id.webview);
        webv.getSettings().setJavaScriptEnabled(true);
        webv.setWebViewClient(new WebViewClient());

        htmlPageUrl=htmlPageUrl1+ quicksolve.latitude+"&y="+ quicksolve.longitude;
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlPageUrl=htmlPageUrl1+ quicksolve.latitude+"&y="+ quicksolve.longitude;
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();

                Toast.makeText(getApplicationContext(),htmlPageUrl,Toast.LENGTH_LONG).show();
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

                htmlContentInStringFormat="";
                //테스트1
                Elements items= doc.select("li.list_item.type_restaurant");
                Elements items1=items.select("span.name._name");
                Elements items2=items.select("span.category._category");
                Elements items3=items.select("span.tag");
                Elements items4=items.select("a.info_area._info_area");

                nameList.clear();
                explainList.clear();
                concreteList.clear();
                LinkList.clear();

                for(Element e: items1){
                    nameList.add(e.text().trim());
                }
                for(Element e: items2){
                    explainList.add(e.text().trim());
                }
                for(Element e: items3){
                    concreteList.add(e.text().trim());
                }
                for(Element e: items4){
                    LinkList.add(e.attr("href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int idx;
            String mUrl="https://m.store.naver.com";
            String mnewUrl="";
            idx=(int)(Math.random()*nameList.size());
            htmlContentInStringFormat+=nameList.get(idx)+"\n";
           // htmlContentInStringFormat+=explainList.get(idx)+"\n";
            mnewUrl+=mUrl+LinkList.get(idx);
            mnewUrl=mnewUrl.replace("&entry=pll","");
            mnewUrl=mnewUrl.replace("detail","detailMap");
            //idx=(int)(Math.random()*concreteList.size());
            //htmlContentInStringFormat+=concreteList.get(idx);

            textviewHtmlDocument.setText(htmlContentInStringFormat);
            webv.loadUrl(mnewUrl);
        }
    }


}
