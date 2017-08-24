package com.jiayuqicz.mygaode.route;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jiayuqicz.mygaode.R;

public class RouteActivity extends AppCompatActivity {

    private Byte schedule_index;
    private String[] schedule = null;
    private TextView currentSchedule = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        
        initView();
    }

    private void initView() {

        schedule_index = 0;

        schedule = getResources().getStringArray(R.array.schedule);
        currentSchedule = (TextView) findViewById(R.id.schedule);
        currentSchedule.setText(schedule[schedule_index]);

    }

    public void changeSchedule(View view) {

        schedule_index++;
        //循环控制不溢出
        if(schedule_index == schedule.length) {
            schedule_index = 0;
        }
        currentSchedule.setText(schedule[schedule_index]);

    }
}
