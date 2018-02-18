package com.JOA.joachanghwa.svoprj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResultView extends AppCompatActivity {

    private EditText inputText;
    private Button addText;
    private String inputData;

    directinput di=new directinput();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("입력으로 정했다 - 결과");
        setContentView(R.layout.activity_result);

        inputText = (EditText)findViewById(R.id.inputText);
        addText = (Button)findViewById(R.id.btn_add_text);

        String str;

        str=di.getResult();

        TextView tv=(TextView)findViewById(R.id.resultView);
        tv.setText(str);

        addText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if(inputText.getText().length() == 0){
                    Toast.makeText(ResultView.this, "데이터를 입력하세요.",Toast.LENGTH_SHORT).show();
                }

                else{
                    inputData = inputText.getText().toString();
                    directinput.customAdapter.addItem(inputData);
                    directinput.customAdapter.notifyDataSetChanged();
                    Toast.makeText(ResultView.this,"데이터가 추가되었습니다.",Toast.LENGTH_SHORT).show();
                    inputText.setText("");

                    directinput.mLvList.setSelection(directinput.customAdapter.getCount()-1);
                }
            }
        });
    }

    public void onClickedRetry(View v){
        String str;
        str=di.getResult();

        TextView tv=(TextView)findViewById(R.id.resultView);
        tv.setText(str);
    }

    public void onClickedReturn(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickedDelete(View v){

        int n = di.getDelete();

        if(n == 0)
            Toast.makeText(ResultView.this,"리스트에 있는 마지막 데이터입니다.",Toast.LENGTH_SHORT).show();


        else {

            Toast.makeText(ResultView.this, "현재 데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

            String str;
            str = di.getResult();
            TextView tv = (TextView) findViewById(R.id.resultView);
            tv.setText(str);
        }
    }

}
