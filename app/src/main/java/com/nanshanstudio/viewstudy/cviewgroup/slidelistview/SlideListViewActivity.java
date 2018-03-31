package com.nanshanstudio.viewstudy.cviewgroup.slidelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nanshanstudio.viewstudy.R;

public class SlideListViewActivity extends AppCompatActivity {

    private SlideListView mListView;
    private String[] strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_list_view);

        mListView = (SlideListView) findViewById(R.id.slide_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_clistview, R.id.tv_item, strings);
        mListView.setAdapter(adapter);
        mListView.setOnSlideListener(new SlideListView.OnSlideListener() {
            @Override
            public void onDelete(int pos) {
                Toast.makeText(SlideListViewActivity.this, "position:" + pos, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
