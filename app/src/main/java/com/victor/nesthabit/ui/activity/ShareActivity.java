package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.DakaResponse;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.Utils;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShareActivity extends BaseActivity {

    public static final String TAG = "@victor ShareActivity";
    private View toolbar;
    private android.widget.EditText sharetext;
    private android.support.v7.widget.CardView sharecardlayout;
    private android.widget.Button submit;
    private String nestId = null;
    private static OnDakaAdded sOnDakaAdded;

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return ShareActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected void initView() {
        this.submit = (Button) findViewById(R.id.submit);
        this.sharecardlayout = (CardView) findViewById(R.id.share_card_layout);
        this.sharetext = (EditText) findViewById(R.id.share_text);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
        if (getIntent() != null) {
            nestId = getIntent().getStringExtra("nestId");
        }
    }

    @Override
    protected void initEvent() {
        (toolbar.findViewById(R.id.back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(getActivity());
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daka();
            }
        });
    }

    private void setToolbar() {
        ((TextView) (toolbar.findViewById(R.id.title_text))).setText(getString(R.string
                .share_and_record));
        (toolbar.findViewById(R.id.right_text)).setVisibility(View.GONE);
    }

    private void daka() {
//        Observable<DakaResponse> observable = NestHabitApi.getInstance().punch(nestId, sharetext
//                .getText().toString(), System.currentTimeMillis(), Utils.getHeader());
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<DakaResponse>() {
//                    @Override
//                    public void onCompleted() {
//                        ActivityManager.startActivity(getActivity(), ShareSuccessActivity.class);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        showToast("打卡失败");
//                        Log.e(TAG, "failuire: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(DakaResponse dakaResponse) {
//                        sOnDakaAdded.OnDakaItemAdded(dakaResponse);
//                    }
//                });
    }

    public static void setOnDakaAdded(OnDakaAdded onDakaAdded) {
        sOnDakaAdded = onDakaAdded;
    }

    public interface OnDakaAdded {
        void OnDakaItemAdded(DakaResponse dakaResponse);
    }

}