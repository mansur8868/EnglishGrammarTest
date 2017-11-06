package vn.edu.uit.a15520275.englishgrammartest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;

import android.widget.TabHost;

import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.uit.a15520275.adapter.Test_Adapter;
import vn.edu.uit.a15520275.adapter.Topic_Adapter;
import vn.edu.uit.a15520275.model.Test;

import vn.edu.uit.a15520275.model.Topic;



public class MainActivity extends AppCompatActivity {

    private  TabHost tabHost;
    private HashMap<Topic,List<Test>> hmTopic;
    private Topic_Adapter topicAdapter;
    private ExpandableListView eplvTopic;

    private ArrayList<Test> listTest;
    private Test_Adapter testAdapter;
    private GridView gvTest;

    private List<Topic> listGroup;
    private List<Test> listChild;

    public static String DATABASE_NAME = "EnglishGrammarTest.sqlite";
    String DB_PATH_SUFIX = "/databases/";
    public static SQLiteDatabase database = null;

    public static boolean isActivityTest = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        xuLySaoChepDATABASEIntoSystems();
            addControl();
            addEvents();

    }

    private void addControl() {


        addTabHost();
        addLvTopic();
        addGvTest();
    }

    private void xuLySaoChepDATABASEIntoSystems() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                CopyDatabaseFromAsset();
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CopyDatabaseFromAsset() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFIX);
            //kiểm tra file tồn tại hay chưa
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream myOuput = new FileOutputStream(outFileName);
            byte[]buffer = new byte [1024];
            int length;
            while ((length = myInput.read(buffer)) > 0){
                myOuput.write(buffer,0,length);
            }
            myOuput.flush();
            myOuput.close();
            myInput.close();
        }catch (Exception e) {
            Log.e("Loi sao chep",e.toString());
        }
    }

    private String layDuongDanLuuTru() {
        return getApplicationInfo().dataDir+DB_PATH_SUFIX+DATABASE_NAME;
    }

    private void addTabHost() {
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Topic");
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Test");
        tabHost.addTab(tab2);
    }
    private void addLvTopic() {
           eplvTopic = (ExpandableListView) findViewById(R.id.eplvTopic);

        DisplayMetrics displayMetrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //int width = displayMetrics.widthPixels;
        //eplvTopic.setIndicatorBounds(width-dp2px(10),width-dp2px(10));

        addDataTopic();
    }
    private void addGvTest() {
        addDataTest();
        gvTest = (GridView) findViewById(R.id.gvTest);
        testAdapter = new Test_Adapter(MainActivity.this,R.layout.item_gvtest,listTest);
        gvTest.setAdapter(testAdapter);

    }

    private void addDataTest() {
        listTest = new ArrayList<>();
        queryDataTest();
    }

    private void queryDataTest() {
        database  =openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("Test",null,"idTopic=?",new String[]{"T18"},null,null,null);
        listTest.clear();
        int k = 1;
        while(cursor.moveToNext()){
            String idTest = cursor.getString(0);
            Short isCompleted = cursor.getShort(2);

            Test test = new Test();
            test.setIdTest(idTest);
            test.setTest("Test " + k );
            test.setCompleted(isCompleted);
            listTest.add(test);
            k++;
        }
        cursor.close();
    }

    private void addDataTopic() {
        //prepare Data
        //data for header group
        listGroup = new ArrayList<>();
        //for data for child view;
        listChild = new ArrayList<>();
        hmTopic = new HashMap<>();

        queryDataTopic();

        topicAdapter = new Topic_Adapter(MainActivity.this,listGroup,hmTopic);
        eplvTopic.setAdapter(topicAdapter);


    }

    private void queryDataTopic() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("Topic",null,null,null,null,null,null);
        listGroup.clear();
        while(cursor.moveToNext()){
            String idTopic = cursor.getString(0);
            String topic = cursor.getString(1);

            Topic topic1 = new Topic();
            topic1.setIdTopic(idTopic);
            topic1.setTopic(topic);
            listGroup.add(topic1);
        }
        cursor.close();

        for(int i=0;i<listGroup.size()-1;i++){//bỏ qua Topic sau cùng:  T18
            Cursor cursor1 = database.query("Test",null,"idTopic=?",new String[]{listGroup.get(i).getIdTopic()}
                    ,null,null,null);
            listChild = new ArrayList<>();
            String t = "Test ";
            short k = 1;
            while (cursor1.moveToNext()){
                String idTest = cursor1.getString(0);
                short isCompleted = cursor1.getShort(2);

                Test test = new Test();
                test.setIdTest(idTest);
                test.setTest(String.valueOf(t + k));
                test.setCompleted(isCompleted);
                listChild.add(test);
                k++;
            }
            hmTopic.put(listGroup.get(i),listChild);
            cursor1.close();
        }
    }

    private int dp2px(int dp) {
        //get the screen's density scale
        final  float density =getResources().getDisplayMetrics().density;
        //Convert the dps to pixels, based on density scale
        return  (int) (dp*density + 0.5f);
    }

    private void addEvents() {
        eplvTopic.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });
        eplvTopic.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                isActivityTest = false;
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("TestTopic",hmTopic.get(listGroup.get(i)).get(i1));
                startActivity(intent);
                return true;
            }
        });
        gvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isActivityTest = true;
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("Test",listTest.get(i));
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
