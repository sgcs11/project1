package com.JOA.joachanghwa.svoprj;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class svogame2 extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{

    private EditText mEtInputText;
    private Button mBInputToList;
    private Button mBCleanList;
    private Button mBNextList;
    public static ListView mLvList;
    public static CustomAdapter_solve_2 customAdapter;
    public static String result;

    public int deletePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("주술목 게임 - 서술어를 입력하세요");
        setContentView(R.layout.activity_svogame);

        mEtInputText=(EditText)findViewById(R.id.ed_text_subject);
        mBInputToList=(Button)findViewById(R.id.btn_add_subject);
        mBCleanList=(Button)findViewById(R.id.btn_clear_subject);
        mBNextList=(Button)findViewById(R.id.btn_next_subject);
        mLvList=(ListView)findViewById(R.id.listView_subject);

        mBInputToList.setOnClickListener(this);
        mBCleanList.setOnClickListener(this);
        mBNextList.setOnClickListener(this);

        customAdapter=new CustomAdapter_solve_2(this);

        mLvList.setAdapter(customAdapter);

        mLvList.setOnItemClickListener(this);
    }

    public String getResult(){
        int idx;
        int size=customAdapter.getCount();
        Random random=new Random();
        idx=random.nextInt(size);
        result=customAdapter.getstringdata(idx);

        deletePosition = idx;

        return result;
    }

    public int getDelete(){

        if(customAdapter.getCount() == 1)
            return 0;


        else {
            customAdapter.remove(deletePosition);
            customAdapter.notifyDataSetChanged();
            return 1;
        }
    }

    public void onItemClick(AdapterView<?> parent, View v, final int position, long id){

        Object data=customAdapter.getItem(position);

        final EditText et=new EditText (svogame2.this);

        String message = "데이터를 수정/삭제하시겠습니까?";

        DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                customAdapter.remove(position);
                customAdapter.notifyDataSetChanged();
            }
        };

        DialogInterface.OnClickListener editListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String value=et.getText().toString();

                if(et.getText().length() == 0) {
                    Toast.makeText(svogame2.this, "데이터를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                else{
                    customAdapter.setItem(value,position);
                    customAdapter.notifyDataSetChanged();
                }
            }
        };

        new AlertDialog.Builder(this)
                .setTitle(customAdapter.getstringdata(position))
                .setMessage(Html.fromHtml(message))
                .setView(et)
                .setNegativeButton("수정",editListener)
                .setPositiveButton("삭제",deleteListener)
                .show();
    }

    public void onClick(View v){
        switch(v.getId()){

            case R.id.btn_add_subject:
                if(mEtInputText.getText().length()==0){
                    Toast.makeText(this,"데이터를 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    String data=mEtInputText.getText().toString();

                    customAdapter.addItem(data);

                    customAdapter.notifyDataSetChanged();

                    Toast.makeText(this,"데이터가 추가되었습니다.",Toast.LENGTH_SHORT).show();

                    mEtInputText.setText("");

                    mLvList.setSelection(customAdapter.getCount()-1);
                }

                break;
            case R.id.btn_clear_subject:
                String message="리스트를 초기화하시겠습니까?";

                DialogInterface.OnClickListener clearListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(),"초기화되었습니다.",Toast.LENGTH_SHORT).show();
                        customAdapter.cleanItem();
                        customAdapter.notifyDataSetChanged();
                    }
                };
                new AlertDialog.Builder(this)
                        .setMessage(Html.fromHtml(message))
                        .setPositiveButton("초기화",clearListener)
                        .show();

                break;
            case R.id.btn_next_subject:
                if(customAdapter.getCount()==0) {
                    Toast.makeText(getApplicationContext(),"데이터가 없습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), svogame3.class);
                    startActivity(intent);
                }

                break;

        }
    }

}

class CustomData_solve_2{
    String txt01;

    public CustomData_solve_2(){

    }
    public CustomData_solve_2(String txt01){
        setTxt01(txt01);
    }

    public void setTxt01(String txt01)
    {
        this.txt01=txt01;
    }
    public String getTxt01(){
        return this.txt01;
    }
}

class CustomAdapter_solve_2 extends BaseAdapter{
    private ArrayList<CustomData_solve_2> listViewItemList=null;
    private LayoutInflater mInflater=null;

    public CustomAdapter_solve_2(Context context){
        mInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listViewItemList=new ArrayList<CustomData_solve_2>();
    }
    @Override
    public int getCount(){
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.team_item,parent,false);
        }
        TextView textView01=(TextView)convertView.findViewById(R.id.textView1);
        CustomData_solve_2 customData = listViewItemList.get(position);

        textView01.setText(customData.getTxt01());
        return convertView;
    }
    @Override
    public long getItemId(int position){return position;}
    public Object getItem(int position){return listViewItemList.get(position);}
    public void addItem(String txt01){
        CustomData_solve_2 customData = new CustomData_solve_2(txt01);
        listViewItemList.add(customData);
    }
    public String getstringdata(int position){
        return listViewItemList.get(position).getTxt01();
    }
    public void setItem(String txt01,int position){
        CustomData_solve_2 customData = new CustomData_solve_2(txt01);
        this.remove(position);
        listViewItemList.add(position,customData);
    }
    public void cleanItem(){
        listViewItemList.clear();
    }
    public void remove(int position){
        listViewItemList.remove(position);
    }
}

