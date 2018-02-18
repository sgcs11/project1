package com.JOA.joachanghwa.svoprj;

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

import java.util.ArrayList;
import java.util.Random;

public class quickteam_result extends AppCompatActivity {

    private ArrayList<CustomData_solve_team> teamList;
    private ArrayList<CustomData_solve_people> peopleList;
    private ArrayList<CustomData_solve_people> peopleList2;

    private EditText inputText;
    private Button addText;
    private String inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("팀 정하기 - 결과");
        setContentView(R.layout.activity_quickteamresult);

        teamList = CustomAdapter_solve_team.listViewItemList;
        peopleList = CustomAdapter_solve_people.listViewItemList;

        final Spinner spinner_quickteam = (Spinner)findViewById(R.id.spinner_quickteam);
        ArrayAdapter adapter_quickteam = ArrayAdapter.createFromResource(this, R.array.array_quickteam, android.R.layout.simple_spinner_item);
        adapter_quickteam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_quickteam.setAdapter(adapter_quickteam);

        selectPeople();

        inputText = (EditText)findViewById(R.id.inputText);
        addText = (Button)findViewById(R.id.btn_add_text);
        addText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                if(spinner_quickteam.getSelectedItemPosition() == 0){
                    if(inputText.getText().length() == 0){
                        Toast.makeText(quickteam_result.this, "팀을 입력하세요.",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        inputData = inputText.getText().toString();
                        quickteam.customAdapter.addItem(inputData);
                        quickteam.customAdapter.notifyDataSetChanged();
                        Toast.makeText(quickteam_result.this,"팀이 추가되었습니다.",Toast.LENGTH_SHORT).show();
                        inputText.setText("");

                        quickteam.mLvList.setSelection(quickteam.customAdapter.getCount()-1);
                        selectPeople();
                    }
                }

                else if(spinner_quickteam.getSelectedItemPosition() == 1){
                    if(inputText.getText().length() == 0){
                        Toast.makeText(quickteam_result.this, "팀원을 입력하세요.",Toast.LENGTH_SHORT).show();
                    }

                    else{
                        inputData = inputText.getText().toString();
                        quickteam_people.customAdapter.addItem(inputData);
                        quickteam_people.customAdapter.notifyDataSetChanged();
                        Toast.makeText(quickteam_result.this,"팀원이 추가되었습니다.",Toast.LENGTH_SHORT).show();
                        inputText.setText("");

                        quickteam_people.mLvList.setSelection(quickteam_people.customAdapter.getCount()-1);
                        selectPeople();
                    }
                }

                else{
                    Toast.makeText(quickteam_result.this, "잘못된 접근입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectPeople(){

        int num = peopleList.size();
        int teamNum = -1;
        String peopleText = "";
        peopleList2 = (ArrayList<CustomData_solve_people>)peopleList.clone();
        String[][] teamPeople = new String[num][2];

        Random random = new Random();
        int idx, idx2;

        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i=0; i<teamList.size(); i++)
            numbers.add(i);


        //////////////// 랜덤 섞기 ////////////////////////

        int trash = peopleList.size() % teamList.size();

        for(int i=0; i<num-trash; i++){

            teamNum++;
            if(teamNum == teamList.size())
                teamNum = 0;

            idx = random.nextInt(peopleList2.size());
            teamPeople[i][0] = peopleList2.get(idx).getTxt01();
            peopleList2.remove(idx);

            teamPeople[i][1] = String.valueOf(teamNum);
        }

        for(int i=num-trash; i<num; i++){

            idx = random.nextInt(peopleList2.size());
            teamPeople[i][0] = peopleList2.get(idx).getTxt01();
            peopleList2.remove(idx);

            idx2 = random.nextInt(numbers.size());
            teamPeople[i][1] = String.valueOf(numbers.get(idx2));
            numbers.remove(idx2);
        }



        ////////////// 출력 /////////////////////////////

        TextView peopleView = (TextView)findViewById(R.id.peopleView);

        for(int i=0; i<teamList.size(); i++){

            peopleText += "\r\n    " + teamList.get(i).getTxt01() + "(팀)  :  ";

            for(int j=0; j<num; j++){

                if(teamPeople[j][1] == String.valueOf(i))
                    peopleText += teamPeople[j][0] + "  ";
            }

            peopleText += "  \r\n";
        }

        peopleView.setText(peopleText);
    }

    public void onClickedReset_quickteam(View v) {
        selectPeople();
    }

    public void onClickedFinish_quickteam(View v){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}