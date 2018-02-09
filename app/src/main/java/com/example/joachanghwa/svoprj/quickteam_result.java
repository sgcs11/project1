package com.example.joachanghwa.svoprj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class quickteam_result extends AppCompatActivity {

    private ArrayList<CustomData_solve_team> teamList;
    private ArrayList<CustomData_solve_people> peopleList;
    private ArrayList<CustomData_solve_people> peopleList2;
    private String teamText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickteamresult);

        teamList = CustomAdapter_solve_team.listViewItemList;
        peopleList = CustomAdapter_solve_people.listViewItemList;
        TextView teamView=(TextView)findViewById(R.id.teamView);

        for(int i=0; i<teamList.size(); i++){
            teamText += teamList.get(i).getTxt01() + " : \r\n\r\n";
        }

        teamView.setText(teamText);
        selectPeople();
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
            for(int j=0; j<num; j++){

                if(teamPeople[j][1] == String.valueOf(i))
                    peopleText += teamPeople[j][0] + " ";
            }

            peopleText += "\r\n\r\n";
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