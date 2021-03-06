package com.nanshanstudio.viewstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nanshanstudio.viewstudy.cview.CVForwardActivity;
import com.nanshanstudio.viewstudy.cviewgroup.CVGForwardActivity;
import com.nanshanstudio.viewstudy.moveevent.MoveEventStudy;
import com.nanshanstudio.viewstudy.referedclassstudy.ClassStudyForwardActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        refer DataBinding character
//        DataBindingUtil.setContentView(this,R.layout.activity_main);



        Button bt_01 = findViewById(R.id.bt_01);
        Button bt_02 = findViewById(R.id.bt_02);
        Button bt_03 = findViewById(R.id.bt_03);
        Button bt_04 = findViewById(R.id.bt_04);

        bt_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent cvIntent = new Intent();
            cvIntent.setClass(MainActivity.this, CVForwardActivity.class);
            startActivity(cvIntent);
            }
        });

        bt_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent cvgIntent = new Intent();
                cvgIntent.setClass(MainActivity.this, CVGForwardActivity.class);
            startActivity(cvgIntent);
            }
        });
        bt_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent cvgIntent = new Intent();
                cvgIntent.setClass(MainActivity.this, MoveEventStudy.class);
            startActivity(cvgIntent);
            }
        });
        bt_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cvgIntent = new Intent();
                cvgIntent.setClass(MainActivity.this, ClassStudyForwardActivity.class);
                startActivity(cvgIntent);
            }
        });
    }


}
