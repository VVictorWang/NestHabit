package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.ui.contract.AddNestContract;
import com.victor.nesthabit.ui.presenter.AddNestPresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.DateUtils;
import com.victor.nesthabit.view.SwitchButton;

import java.util.Calendar;

public class AddNestActivity extends BaseActivity implements AddNestContract.View {

    private android.widget.ImageView back;
    private android.widget.EditText name;
    private TextView start_time;
    private android.widget.EditText introduction, amount;
    private android.widget.EditText day;
    private android.widget.RelativeLayout beginlayout;
    private SwitchButton limittoggle;
    private SwitchButton openToggle;

    private Button submit;
    private AddNestContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new AddNestPresenter(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected BasePresenter getPresnter() {
        return mPresenter;
    }

    @Override
    protected Activity getActivity() {
        return AddNestActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_nest;
    }

    @Override
    protected void initView() {
        this.limittoggle = (SwitchButton) findViewById(R.id.limit_toggle);
        this.beginlayout = (RelativeLayout) findViewById(R.id.begin_layout);
        this.day = (EditText) findViewById(R.id.day);
        this.introduction = (EditText) findViewById(R.id.introduction);
        this.name = (EditText) findViewById(R.id.name);
        this.back = findViewById(R.id.back);
        start_time = (TextView) findViewById(R.id.start_time);
        amount = findViewById(R.id.amount);
        openToggle = findViewById(R.id.open_toggle);
        submit = (Button) findViewById(R.id.submit);
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(v -> ActivityManager.finishActivity(AddNestActivity.this));
        limittoggle.setOnToggleChanged(on -> {
            if (on) {
                amount.setEnabled(true);
            } else {
                amount.setEnabled(false);
            }
        });

        submit.setOnClickListener(v -> mPresenter.finish(new ReposityCallback<AddResponse>() {
            @Override
            public void callSuccess(AddResponse data) {
                showToast("添加成功");
                mPresenter.notifyNestAdded(data.getObjectId());
                ActivityManager.finishActivity(getActivity());
            }

            @Override
            public void callFailure(String errorMessage) {
                showToast("添加失败 " + errorMessage);
            }
        }));
        beginlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout
                        .calendar_dialog, null);
                TextView finish = (TextView) view.findViewById(R.id.finish);
                MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id
                        .calendar);
                calendarView.state().edit().setMinimumDate(Calendar.getInstance().getTime()
                ).commit();
                calendarView.setSelectedDate(CalendarDay.from(DateUtils.StringToDate(getStartTime
                        ())));
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
                finish.setOnClickListener(v1 -> {
                    setStartTime(DateUtils.DatetoString(calendarView.getSelectedDate().getDate
                            ()));
                    dialog.dismiss();
                });

            }
        });

    }

    @Override
    public void setPresenter(AddNestContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyToast(String description) {
        showToast(description);
    }

    @Override
    public String getName() {
        return name.getText().toString();
    }

    @Override
    public void showNameError() {
        name.setError(getString(R.string.can_not_be_empty));
    }


    @Override
    public String getIntroduction() {
        return introduction.getText().toString();
    }

    @Override
    public String getDay() {
        return day.getText().toString();
    }

    @Override
    public void showDayError() {
        day.setError(getString(R.string.can_not_be_empty));
    }

    @Override
    public String getStartTime() {
        return start_time.getText().toString();
    }

    @Override
    public void setStartTime(String date) {
        start_time.setText(date);
    }

    @Override
    public boolean IsAmountLimited() {
        return limittoggle.getToogle();
    }

    @Override
    public boolean isOPen() {
        return openToggle.getToogle();
    }

    @Override
    public String getAmount() {
        return amount.getText().toString();
    }

    @Override
    public void showAmountError() {
        amount.setError(getString(R.string.can_not_be_empty));
    }

    @Override
    public void finishActivity() {
        ActivityManager.finishActivity(getActivity());
    }
}
