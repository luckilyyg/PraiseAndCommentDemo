package com.crazy.crazy.fenleijiemian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.crazy.crazy.R;

import java.util.ArrayList;
import java.util.Random;

public class Main3Activity extends AppCompatActivity {
    private RecyclerView rv;
    private ExpandableItemAdapter adapter;
    private ArrayList<MultiItemEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        rv = findViewById(R.id.rv);
        list = generateData();
        initAdapter(list);
    }

    private void initAdapter(ArrayList<MultiItemEntity> list) {
        adapter = new ExpandableItemAdapter(list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter.expandAll();
    }


    private ArrayList<MultiItemEntity> generateData() {
//        int lv0Count = 9;
        int lv1Count = 3;
        int personCount = 5;

        String[] nameList = {"Bob", "Andy", "Lily", "Brown", "Bruce"};
        Random random = new Random();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
//        for (int i = 0; i < lv0Count; i++) {
//            Level0Item lv0 = new Level0Item("This is " + i + "th item in Level 0", "subtitle of " + i);
//            for (int j = 0; j < lv1Count; j++) {
//                Level1Item lv1 = new Level1Item("Level 1 item: " + j, "(no animation)");
//                for (int k = 0; k < personCount; k++) {
//                    lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
//                }
//                lv0.addSubItem(lv1);
//            }
//            res.add(lv0);
//        }
//        res.add(new  Level0Item("This is " + lv0Count + "th item in Level 0", "subtitle of " + lv0Count));
        for (int j = 0; j < lv1Count; j++) {
            Level1Item lv1 = new Level1Item("Level 1 item: " + j, j);
            for (int k = 0; k < personCount; k++) {
                lv1.addSubItem(new Person(nameList[k], random.nextInt(40)));
            }
            res.add(lv1);
        }
//        Log.e("test", "generateData: "+res.toString() );
        return res;
    }
}
