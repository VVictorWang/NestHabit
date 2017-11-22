package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.PunchRepository;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ShareActivity extends BaseActivity {

    public static final String TAG = "@victor ShareActivity";
    private View toolbar;
    private android.widget.EditText sharetext;
    private android.support.v7.widget.CardView sharecardlayout;
    private android.widget.Button submit;
    private String nestId = null;

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
        if (getIntent() != null) {
            nestId = getIntent().getStringExtra("nestId");
        }
        this.submit = (Button) findViewById(R.id.submit);
        this.sharecardlayout = (CardView) findViewById(R.id.share_card_layout);
        this.sharetext = (EditText) findViewById(R.id.share_text);
        this.toolbar = findViewById(R.id.toolbar);
        setToolbar();
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
                if (sharetext.getText().toString().isEmpty()) {
                    sharetext.setError("请输入打卡感想!");
                } else {
                    daka();
                }
            }
        });
    }

    private void setToolbar() {
        ((TextView) (toolbar.findViewById(R.id.title_text))).setText(getString(R.string
                .share_and_record));
        (toolbar.findViewById(R.id.right_text)).setVisibility(View.GONE);
    }

    private void daka() {
        PunchRepository punchRepository = PunchRepository.getInstance();
        PunchInfo punchInfo = new PunchInfo();
        punchInfo.setUserId(Utils.getUserId());
        punchInfo.setContents(sharetext.getText().toString());
        punchRepository.punch(punchInfo, new ReposityCallback<AddResponse>() {
            @Override
            public void callSuccess(AddResponse data) {
                NestRepository nestRepository = NestRepository.getInstance();
                nestRepository.loadNestInfo(nestId, new NetWorkBoundUtils.CallBack<NestInfo>() {
                    @Override
                    public void callSuccess(Observable<NestInfo> result) {
                        result.subscribeOn(Schedulers.io())
                                .doOnNext(nestInfo -> {
                                    List<String> punchLogs = new ArrayList<>();
                                    punchLogs.addAll(nestInfo.getPunchlogs());
                                    punchLogs.add(data.getObjectId());
                                    nestInfo.setPunchlogs(punchLogs);
                                    List<NestInfo.MembersBean> membersBeans = new ArrayList<>();
                                    membersBeans.addAll(nestInfo.getMembers());
                                    for (int i = 0; i < membersBeans.size(); i++) {
                                        NestInfo.MembersBean bean = membersBeans.get(i);
                                        if (bean.getUserId().equals(Utils.getUserId())) {
                                            bean.setConstant_days(bean.getConstant_days() + 1);
                                            bean.setKept_days(bean.getKept_days() + 1);
                                            membersBeans.set(i, bean);
                                            break;
                                        }
                                    }
                                    nestInfo.setMembers(membersBeans);
                                })
                                .subscribe(nestInfo -> {
                                    nestRepository.changeNestInfo(nestInfo, new
                                            ReposityCallback<UpdateInfo>() {
                                                @Override
                                                public void callSuccess(UpdateInfo data) {

                                                }

                                                @Override
                                                public void callFailure(String errorMessage) {

                                                }
                                            });
                                });
                    }

                    @Override
                    public void callFailure(String errorMessage) {

                    }
                });

                ActivityManager.startActivityForResult(getActivity(), ShareSuccessActivity.class,
                        101);
            }

            @Override
            public void callFailure(String errorMessage) {
                showToast(errorMessage);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101 && resultCode == 102) {
            ActivityManager.finishActivity(getActivity());
        }

    }
}