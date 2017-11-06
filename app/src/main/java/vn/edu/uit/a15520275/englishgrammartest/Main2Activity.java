package vn.edu.uit.a15520275.englishgrammartest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.uit.a15520275.adapter.Topic_Adapter;
import vn.edu.uit.a15520275.model.Test;
import vn.edu.uit.a15520275.model.TestContent;

import static vn.edu.uit.a15520275.englishgrammartest.MainActivity.DATABASE_NAME;
import static vn.edu.uit.a15520275.englishgrammartest.MainActivity.database;
import static vn.edu.uit.a15520275.englishgrammartest.MainActivity.isActivityTest;


public class Main2Activity extends AppCompatActivity {

    private  Test test;
    private ArrayList<TestContent> listTestContent ;
    private Button btnPage, btnNext, btnPre;
    private TextView txtTestContent,txtA,txtB,txtC,txtD;
    private  int location = 0;
    private short key = 0;
    private short numberTestCorrect = 0;
    private short numberTestWrong = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getDataFromActivity1();
        queryDataTestContent();
        addControl();
        addEvent();

    }


    private void addEvent() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextTest();
                key = 0;
            }
        });

        txtA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = 1;
                displayResult();
            }
        });
        txtB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = 2;
                displayResult();
            }
        });
        txtC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = 3;
                displayResult();
            }
        });
        txtD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = 4;
                displayResult();
            }
        });
    }

    private void nextTest() {
        if(key !=0){
            location++;
            if(location >= 10){ //have completed test
                truyenResultThroughActivity3();
            }else{
                setTestContent();
            }
        }
        else{
            Toast.makeText(this, "You have no answer", Toast.LENGTH_SHORT).show();
        }
    }




    private void truyenResultThroughActivity3() {
        Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
        intent.putExtra("numberTestCorrect",numberTestCorrect);
        intent.putExtra("numberTestWrong",numberTestWrong);
        intent.putExtra("test",test);
        startActivity(intent);
    }


    private void addControl() {
        btnPage = (Button) findViewById(R.id.btnPage);
        btnNext = (Button) findViewById(R.id.btnNext);
        txtTestContent = (TextView) findViewById(R.id.txtTestContent);
        txtA = (TextView) findViewById(R.id.txtA);
        txtB= (TextView) findViewById(R.id.txtB);
        txtC= (TextView) findViewById(R.id.txtC);
        txtD= (TextView) findViewById(R.id.txtD);

        setTestContent();

    }
    private void displayResult() {
        if(key == listTestContent.get(location).getKey()){
            numberTestCorrect++;
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        }else{
            numberTestWrong ++;
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void setTestContent() {
        btnPage.setText("Task " + String.valueOf(location + 1 ) + "/" + listTestContent.size());
        txtTestContent.setText(listTestContent.get(location).getContent());
        txtA.setText(listTestContent.get(location).getA());
        txtB.setText(listTestContent.get(location).getB());
        txtC.setText(listTestContent.get(location).getC());
        txtD.setText(listTestContent.get(location).getD());
    }

    public void getDataFromActivity1() {
        Intent intent = getIntent();
        if(isActivityTest){
            test = (Test) intent.getSerializableExtra("Test");
        }else{
             test = (Test) intent.getSerializableExtra("TestTopic");
        }
    }
    private void queryDataTestContent() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor =  database.query("TestContent",null,"idTest=?",new String[]{test.getIdTest()},null,null,null,null);
        listTestContent = new ArrayList();
        while(cursor.moveToNext()){
            String idTestContent = cursor.getString(0);
            String content = cursor.getString(2);
            String A = cursor.getString(3);
            String B = cursor.getString(4);
            String C = cursor.getString(5);
            String D = cursor.getString(6);
            short key = cursor.getShort(7);

            TestContent testContent = new TestContent();
            testContent.setIdTestContent(idTestContent);
            testContent.setContent(content);
            testContent.setA(A);
            testContent.setB(B);
            testContent.setC(C);
            testContent.setD(D);
            testContent.setKey(key);
            listTestContent.add(testContent);
        }
        cursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
