package vn.edu.uit.a15520275.englishgrammartest;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.edu.uit.a15520275.model.Test;

import static vn.edu.uit.a15520275.englishgrammartest.MainActivity.database;


public class Main3Activity extends AppCompatActivity {

    private short numberTestCorrect;
    private short numberTestWrong;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        takeDataFromActivity2();

    }

    private void takeDataFromActivity2() {
        Intent intent = getIntent();

        numberTestCorrect = intent.getShortExtra("numberTestCorrect", (short) -1);
        numberTestWrong = intent.getShortExtra("numberTestWrong", (short) -1);
        test = (Test) intent.getSerializableExtra("test");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        updateDataSQlite();

        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
        startActivity(intent);
    }

    private void updateDataSQlite() {
        ContentValues row = new ContentValues(); // 1 dòng dữ liệu
        row.put("isCompleted", true);
        int ret = database.update("Test", row, "idTest=?", new String[]{test.getIdTest()});
    }


}
