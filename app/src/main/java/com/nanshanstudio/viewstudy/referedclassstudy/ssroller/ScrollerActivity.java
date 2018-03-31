package com.nanshanstudio.viewstudy.referedclassstudy.ssroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nanshanstudio.viewstudy.R;
import com.nanshanstudio.viewstudy.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ScrollerActivity extends AppCompatActivity {

    private OptionWidget mLayoutSort;
    private LinearLayout mLayout;
    private Button scrollTo;
    private Button scrollBy;
    private Button sort;
    private List<String> sortList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller);

        mLayout = (LinearLayout) findViewById(R.id.ll);
        scrollTo = (Button) findViewById(R.id.scrollTo);
        scrollBy = (Button) findViewById(R.id.scrollBy);

        scrollTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.scrollTo(100,0);
            }
        });
        scrollBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.scrollBy(100, 0);
            }
        });

        mLayoutSort = (OptionWidget) findViewById(R.id.layout);
        sortList = new ArrayList<String>();
        sortList.add("Time");
        sortList.add("Distance");
        mLayoutSort.setSortList(ScrollerActivity.this, sortList);
        mLayoutSort.setOnItemClickListener(new OptionWidget.OnItemClickListener(){

            @Override
            public void onClick(int potion) {
                Utils.toast(ScrollerActivity.this,potion+"");
            }
        });

        sort = (Button) findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutSort.showOrHide();
            }
        });


    }

}
