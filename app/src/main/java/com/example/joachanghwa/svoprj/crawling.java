package com.example.joachanghwa.svoprj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
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
import java.util.Random;

public class crawling extends AppCompatActivity {

    private String htmlPageUrl1 = "https://m.store.naver.com/restaurants/listMap?level=top&query=맛집&searchQuery=맛집&sortingOrder=prevision&x="; //파싱할 홈페이지의 URL주소
    private String htmlPageUrl="";
    private String cafePageUrl1 = "https://m.store.naver.com/places/listMap?level=top&nlu=%5Bobject%20Object%5D&query=%EC%B9%B4%ED%8E%98&sid=19923805%2C11675200%2C35782036&sortingOrder=distance&viewType=place&x="; //파싱할 홈페이지의 URL주소
    private String alcoholPageUrl1="https://m.store.naver.com/places/listMap?query=%EC%88%A0%EC%A7%91&sid=34706559%2C20259805%2C34541782&level=top&sortingOrder=distance&x=";
    private String searchPageUrl1="https://m.store.naver.com/places/listMap?level=top&nlu=%5Bobject%20Object%5D&query=search_contents&sid=1079072185%2C1166038785%2C1710676941&sortingOrder=distance&viewType=place&x=";
    private TextView textviewHtmlDocument;
    private String htmlContentInStringFormat="";
    private List<String> nameList=new ArrayList<String>();
    private List<String> explainList=new ArrayList<String>();
    private List<String> concreteList=new ArrayList<String>();
    private List<String> LinkList=new ArrayList<>();
    private List<String> distance=new ArrayList<>();
    private WebView webv;
    private Random rand = new Random(System.currentTimeMillis());
    private int flag;
    private String abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GPS로 정했다 - 결과");
        setContentView(R.layout.activity_crawling);

        textviewHtmlDocument = (TextView)findViewById(R.id.textView);
        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod()); //스크롤 가능한 텍스트뷰로 만들기
        webv=(WebView)findViewById(R.id.webview);
        webv.getSettings().setJavaScriptEnabled(true);
        webv.setWebViewClient(new WebViewClient());
        webv.setWebChromeClient(new WebChromeClient(){

            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        if(quicksolve.btn_number==1)
            htmlPageUrl=htmlPageUrl1+ quicksolve.longitude+"&y="+ quicksolve.latitude;
        else if(quicksolve.btn_number==2)
            htmlPageUrl=cafePageUrl1+ quicksolve.longitude+"&y="+ quicksolve.latitude;
        else if(quicksolve.btn_number==3)
            htmlPageUrl=alcoholPageUrl1+ quicksolve.longitude+"&y="+ quicksolve.latitude;
        else if(quicksolve.btn_number==4) {
            htmlPageUrl = searchPageUrl1 + quicksolve.longitude + "&y=" + quicksolve.latitude;
            htmlPageUrl=htmlPageUrl.replace("search_contents",quicksolve.edt_text);
        }
            JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
            jsoupAsyncTask.execute();

        Button htmlTitleButton = (Button)findViewById(R.id.button);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quicksolve.btn_number==1)
                    htmlPageUrl=htmlPageUrl1+ quicksolve.longitude+"&y="+ quicksolve.latitude;
                else if(quicksolve.btn_number==2)
                    htmlPageUrl=cafePageUrl1+ quicksolve.longitude+"&y="+ quicksolve.latitude;
                else if(quicksolve.btn_number==3)
                    htmlPageUrl=alcoholPageUrl1+ quicksolve.longitude+"&y="+ quicksolve.latitude;
                else if(quicksolve.btn_number==4) {
                    htmlPageUrl = searchPageUrl1 + quicksolve.longitude + "&y=" + quicksolve.latitude;
                    htmlPageUrl=htmlPageUrl.replace("search_contents",quicksolve.edt_text);
                }
                    JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                    jsoupAsyncTask.execute();
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            flag=0;
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                htmlContentInStringFormat="";

             //   if(Math.abs(quicksolve.prelat-quicksolve.latitude)>0.0001||Math.abs(quicksolve.prelong-quicksolve.longitude)>0.0001||quicksolve.pre_btn_number!=quicksolve.btn_number) {
                    Document doc = Jsoup.connect(htmlPageUrl).get();

                    nameList.clear();
                    explainList.clear();
                    concreteList.clear();
                    LinkList.clear();

                    /*
                abc="";
                Element erritems = doc.select("title").first();
                abc=erritems.text().trim();
                Log.e(this.getClass().getName(),"웹에서 가져온 문자열"+abc);
                if(abc.equals("")){
                    flag=1;
                }*/

                    //테스트1
                    if (quicksolve.btn_number == 1) {
                        Elements items = doc.select("li.list_item.type_restaurant");
                        Elements distitems = items.select("div.etc_area").select("span.etc_item.distance");
                        Elements items1 = items.select("span.name._name");
                        Elements items3 = items.select("div.tag_area");
                        Elements items4 = items.select("a.info_area._info_area");

                        for (Element e : items1) {
                            nameList.add(e.text().trim());
                        }
                        for (Element e : items3) {
                            concreteList.add(e.text().trim());
                        }
                        for (Element e : items4) {
                            LinkList.add(e.attr("href"));
                        }
                        for (Element e : distitems) {
                            distance.add(e.text().trim());
                        }
                    } else if (quicksolve.btn_number == 2 || quicksolve.btn_number == 3 || quicksolve.btn_number == 4) {
                        Elements items = doc.select("div.list_item.type_common");
                        Elements distitems = items.select("div.address").select("em.distance");
                        Elements items1 = items.select("span.name");
                        Elements items4 = items.select("a.info_area");
                        Elements items3 = items.select("span.category");

                        for (Element e : items1) {
                            nameList.add(e.text().trim());
                        }
                        for (Element e : items4) {
                            LinkList.add(e.attr("href"));
                        }
                        for (Element e : distitems) {
                            distance.add(e.text().trim());
                        }
                        for (Element e : items3) {
                            concreteList.add(e.text().trim());
                        }
                    }

                    quicksolve.prelat = quicksolve.latitude;
                    quicksolve.prelong = quicksolve.longitude;
                    quicksolve.pre_btn_number = quicksolve.btn_number;
                    //  }
            } catch (IOException e) {
                flag=1;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int idx;
            String mUrl = "https://m.store.naver.com";
            String mnewUrl = "";

            String temp = "";
            String temp1 = "";

            if (flag == 1) {
                Toast.makeText(getApplicationContext(),"검색 결과를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            else {
               // Toast.makeText(getApplicationContext(), abc, Toast.LENGTH_LONG).show();

                idx = (int) (rand.nextInt(nameList.size()));
                temp = distance.get(idx);
                temp1 = concreteList.get(idx);

                if (quicksolve.btn_number == 1) {
                    while (temp1.indexOf("카페") != -1 || temp1.indexOf("술집") != -1) {
                        idx = (int) (rand.nextInt(nameList.size()));
                        temp = distance.get(idx);
                        temp1 = concreteList.get(idx);
                    }
                } else if (quicksolve.btn_number == 2) {
                    while (temp1.indexOf("방탈출") != -1 || temp1.indexOf("카페") == -1) {
                        idx = (int) (rand.nextInt(nameList.size()));
                        temp = distance.get(idx);
                        temp1 = concreteList.get(idx);
                    }
                }


                htmlContentInStringFormat += nameList.get(idx) + "\n";
                htmlContentInStringFormat += distance.get(idx);
                mnewUrl += mUrl + LinkList.get(idx);
                mnewUrl = mnewUrl.replace("&entry=pll", "");
                mnewUrl = mnewUrl.replace("detail", "detailMap");

                textviewHtmlDocument.setText(htmlContentInStringFormat);
                webv.loadUrl(mnewUrl);
            }

        }
    }


}
