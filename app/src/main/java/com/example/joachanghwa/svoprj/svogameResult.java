package com.example.joachanghwa.svoprj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class svogameResult extends AppCompatActivity {

    private EditText inputText;
    private Button addText;
    private String inputData;

    svogame sv1 =new svogame();
    svogame2 sv2 =new svogame2();
    svogame3 sv3 =new svogame3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("주술목 게임 - 결과");
        setContentView(R.layout.activity_svogameresult);

        String sStr, vStr, oStr;
        final Spinner spinner_svo = (Spinner)findViewById(R.id.spinner_svo);
        ArrayAdapter adapter_svo = ArrayAdapter.createFromResource(this, R.array.game_svo, android.R.layout.simple_spinner_item);
        adapter_svo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_svo.setAdapter(adapter_svo);

        sStr=sv1.getResult();
        vStr = sv2.getResult();
        oStr = sv3.getResult();


        TextView sView=(TextView)findViewById(R.id.sView);
        sView.setText("주어 : " + sStr);

        TextView vView=(TextView)findViewById(R.id.vView);
        vView.setText("서술어 : " + vStr);

        TextView oView=(TextView)findViewById(R.id.oView);
        oView.setText("목적어 : " + oStr);

        inputText = (EditText)findViewById(R.id.inputText);
        addText = (Button)findViewById(R.id.btn_add_text);
        addText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(spinner_svo.getSelectedItemPosition() == 0){
                    if(inputText.getText().length() == 0){
                        Toast.makeText(svogameResult.this, "주어를 입력하세요.",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        inputData = inputText.getText().toString();
                        svogame.customAdapter.addItem(inputData);
                        svogame.customAdapter.notifyDataSetChanged();
                        Toast.makeText(svogameResult.this,"주어가 추가되었습니다.",Toast.LENGTH_SHORT).show();
                        inputText.setText("");

                        svogame.mLvList.setSelection(svogame.customAdapter.getCount()-1);
                    }
                }

                else if(spinner_svo.getSelectedItemPosition() == 1){
                    if(inputText.getText().length() == 0){
                        Toast.makeText(svogameResult.this, "서술어를 입력하세요.",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        inputData = inputText.getText().toString();
                        svogame2.customAdapter.addItem(inputData);
                        svogame2.customAdapter.notifyDataSetChanged();
                        Toast.makeText(svogameResult.this,"서술어가 추가되었습니다.",Toast.LENGTH_SHORT).show();
                        inputText.setText("");

                        svogame2.mLvList.setSelection(svogame2.customAdapter.getCount()-1);
                    }
                }

                else if(spinner_svo.getSelectedItemPosition() == 2){
                    if(inputText.getText().length() == 0){
                        Toast.makeText(svogameResult.this, "목적어를 입력하세요.",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        inputData = inputText.getText().toString();
                        svogame3.customAdapter.addItem(inputData);
                        svogame3.customAdapter.notifyDataSetChanged();
                        Toast.makeText(svogameResult.this,"목적어가 추가되었습니다.",Toast.LENGTH_SHORT).show();
                        inputText.setText("");

                        svogame3.mLvList.setSelection(svogame3.customAdapter.getCount()-1);
                    }
                }

                else{
                    Toast.makeText(svogameResult.this, "잘못된 접근입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onClickedRetryS(View v){
        String str;
        str = sv1.getResult();

        TextView tv=(TextView)findViewById(R.id.sView);
        tv.setText("주어 : " + str);

    }

    public void onClickedRetryV(View v){
        String str;
        str=sv2.getResult();

        TextView tv=(TextView)findViewById(R.id.vView);
        tv.setText("서술어 : " + str);

    }

    public void onClickedRetryO(View v){
        String str;
        str=sv3.getResult();

        TextView tv=(TextView)findViewById(R.id.oView);
        tv.setText("목적어 : " + str);

    }


    public void onClickedReset(View v){
        String sStr, vStr, oStr;

        sStr=sv1.getResult();
        vStr = sv2.getResult();
        oStr = sv3.getResult();


        TextView sView=(TextView)findViewById(R.id.sView);
        sView.setText("주어 : " + sStr);

        TextView vView=(TextView)findViewById(R.id.vView);
        vView.setText("서술어 : " + vStr);

        TextView oView=(TextView)findViewById(R.id.oView);
        oView.setText("목적어 : " + oStr);

    }

    public void onClickedFinish(View v){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickedDeleteS(View v){
        int n = sv1.getDelete();

        if(n == 0)
            Toast.makeText(svogameResult.this,"리스트에 있는 마지막 주어입니다.",Toast.LENGTH_SHORT).show();


        else {
            Toast.makeText(svogameResult.this, "현재 주어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

            String sStr;
            sStr = sv1.getResult();
            TextView sView = (TextView) findViewById(R.id.sView);
            sView.setText("주어 : " + sStr);
        }
    }

    public void onClickedDeleteV(View v){
        int n = sv2.getDelete();

        if(n == 0)
            Toast.makeText(svogameResult.this,"리스트에 있는 마지막 서술어입니다.",Toast.LENGTH_SHORT).show();

        else {
            Toast.makeText(svogameResult.this, "현재 서술어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

            String vStr;
            vStr = sv2.getResult();
            TextView vView = (TextView) findViewById(R.id.vView);
            vView.setText("서술어 : " + vStr);
        }
    }

    public void onClickedDeleteO(View v){
        int n = sv3.getDelete();

        if(n == 0)
            Toast.makeText(svogameResult.this,"리스트에 있는 마지막 목적어입니다.",Toast.LENGTH_SHORT).show();

        else {
            Toast.makeText(svogameResult.this, "현재 목적어가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

            String oStr;
            oStr = sv3.getResult();
            TextView oView = (TextView) findViewById(R.id.oView);
            oView.setText("목적어 : " + oStr);
        }
    }
}
