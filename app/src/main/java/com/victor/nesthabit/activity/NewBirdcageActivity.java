package com.victor.nesthabit.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.victor.nesthabit.R;
import com.victor.nesthabit.utils.ActivityManager;
import com.victor.nesthabit.view.SwitchButton;

public class NewBirdcageActivity extends Activity  {

    private View newToolbar;
    private RelativeLayout introductionAndAdd;
    private TextView creatBriefIntroduction;
    private TextView addNewMember;
    private RelativeLayout beginTime;
    private TextView beginTimeTextTime;
    private RelativeLayout limitAmount;
    private SwitchButton limitAmountToogle;
    private RelativeLayout limitAmountPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_birdcage);
        ActivityManager.getInstance().pushActivity(NewBirdcageActivity.this);
        newToolbar = (View) findViewById(R.id.new_toolbar);
        introductionAndAdd = (RelativeLayout) findViewById(R.id.introduction_and_add);
        creatBriefIntroduction = (TextView) findViewById(R.id.creat_brief_introduction);
        addNewMember = (TextView) findViewById(R.id.add_new_member);
        beginTime = (RelativeLayout) findViewById(R.id.begin_time);
        beginTimeTextTime = (TextView) findViewById(R.id.begin_time_text_time);
        limitAmount = (RelativeLayout) findViewById(R.id.limit_amount);
        limitAmountToogle = (SwitchButton) findViewById(R.id.limit_amount_toogle);
        limitAmountPeople = (RelativeLayout) findViewById(R.id.limit_amount_people);
        initEvent();
    }

    private EditText getEditNewBirdcage(){
        return (EditText) findViewById(R.id.edit_new_birdcage);
    }

    private EditText getEditDay(){
        return (EditText) findViewById(R.id.edit_day);
    }

    private EditText getLimitAmountPeopleText(){
        return (EditText) findViewById(R.id.limit_amount_people_text);
    }

    private void initEvent() {
        beginTimeTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialCalendarView materialCalendarView = new MaterialCalendarView(NewBirdcageActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(NewBirdcageActivity.this)
                        .setView(materialCalendarView).create();
                dialog.show();
            }
        });
    }
}
