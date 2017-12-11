package com.nanshanstudio.customview.cview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nanshanstudio.customview.R;
import com.nanshanstudio.customview.cview.focus.CVActivity;
import com.nanshanstudio.customview.cviewgroup.focus.CVGActivity;
import com.nanshanstudio.customview.cviewgroup.slidelistview.SlideListViewActivity;

public class CVForwardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview_temp);

        Button bt_01 = (Button) findViewById(R.id.bt_01);
        Button bt_02 =(Button) findViewById(R.id.bt_02);
        Button bt_03 =(Button) findViewById(R.id.bt_03);

        bt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cvIntent = new Intent();
                cvIntent.setClass(CVForwardActivity.this, CVActivity.class);
                startActivity(cvIntent);
            }
        });

//        bt_02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cvgIntent = new Intent();
////                cvgIntent.setClass(CVForwardActivity.this, SlideListViewActivity.class);
//                startActivity(cvgIntent);
//            }
//        });
//        bt_03.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cvgIntent = new Intent();
////                cvgIntent.setClass(CVForwardActivity.this, MoveEventStudy.class);
//                startActivity(cvgIntent);
//            }
//        });

    }
}
