package com.nanshanstudio.customview.cviewgroup.dailyschedule;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.nanshanstudio.customview.R;
import com.nanshanstudio.customview.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Time:2017-03-17 17:44:38
 * 参见：http://www.jianshu.com/p/e9fccf62deac
 * 总结：本文以一种代码生成的方式，用RelativeLayout来代码布局，用ScrollView来滑动的方式来创造一个自定义的控件
 *      重点是有关日期的计算
 */
public class DailyScheduleActivity extends AppCompatActivity {

    private CourseTableView courseTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_schedule);

        courseTableView = (CourseTableView) findViewById(R.id.ctv);

        List<Course> list = new ArrayList<>();

        Course c1 = new Course();
        c1.setDay(1);
        c1.setDes("IELTS");
        c1.setJieci(1);
        list.add(c1);

        Course c2 = new Course();
        c2.setDay(2);
        c2.setDes("Fitness");
        c2.setJieci(7);
        list.add(c2);

        Course c3 = new Course();
        c3.setDay(3);
        c3.setDes("AI");
        c3.setJieci(3);
        list.add(c3);

        Course c4 = new Course();
        c4.setDay(4);
        c4.setDes("Fitness");
        c4.setJieci(6);
        list.add(c4);

        Course c5 = new Course();
        c5.setDay(5);
        c5.setDes("AI");
        c5.setJieci(4);
        list.add(c5);

        Course c6 = new Course();
        c6.setDay(6);
        c6.setDes("Fitness");
        c6.setJieci(7);
        list.add(c6);

        courseTableView.updateCourseViews(list);
        final String[] choices = new String[]{"AI", "Fitness"};
        courseTableView.setOnCourseItemClickListener(new CourseTableView.OnCourseItemClickListener() {
            @Override
            public void onCourseItemClick(final TextView tv, int jieci, int day, String des) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DailyScheduleActivity.this);
                builder.setSingleChoiceItems(choices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv.setText(choices[which]);
                        dialog.cancel();
                    }
                });
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

}
