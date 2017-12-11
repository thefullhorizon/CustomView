package com.nanshanstudio.customview.referedclassstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nanshanstudio.customview.R;
import com.nanshanstudio.customview.referedclassstudy.ssroller.ScrollerActivity;
import com.nanshanstudio.customview.referedclassstudy.sviewdraghelper.ViewDragHelperActivity;

public class ClassStudyForwardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_study_forward);

        Button bt_01 = (Button) findViewById(R.id.bt_01);
        Button bt_02 =(Button) findViewById(R.id.bt_02);
        Button bt_03 =(Button) findViewById(R.id.bt_03);

        bt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent cvIntent = new Intent();
                cvIntent.setClass(ClassStudyForwardActivity.this, ScrollerActivity.class);
                startActivity(cvIntent);
            }
        });

        bt_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cvgIntent = new Intent();
                cvgIntent.setClass(ClassStudyForwardActivity.this, ViewDragHelperActivity.class);
                startActivity(cvgIntent);
            }
        });
//        bt_03.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cvgIntent = new Intent();
////                cvgIntent.setClass(ClassStudyForwardActivity.this, MoveEventStudy.class);
//                startActivity(cvgIntent);
//            }
//        });

    }
}
