package com.victor.nesthabit.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.victor.nesthabit.R;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.BaseActivity;
import com.victor.nesthabit.ui.base.BasePresenter;
import com.victor.nesthabit.util.ActivityManager;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;
import com.victor.nesthabit.view.CircleImageView;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class ProfileActivity extends BaseActivity {

    private android.widget.ImageView back;
    private android.widget.RelativeLayout toolbar;
    private com.victor.nesthabit.view.CircleImageView avatar;
    private android.support.v7.widget.CardView advicelayout;
    private android.support.v7.widget.CardView quitlayout;
    private com.victor.nesthabit.view.CircleImageView qq;
    private com.victor.nesthabit.view.CircleImageView qzone;
    private com.victor.nesthabit.view.CircleImageView wtchat;
    private com.victor.nesthabit.view.CircleImageView moment;
    private TextView name;

//    private IWXAPI mIWXAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name.setText(Utils.getUsername());

//        mIWXAPI = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WECHAT, true);
//        mIWXAPI.registerApp(Constants.APP_ID_WECHAT);
    }

    @Override
    protected BasePresenter getPresnter() {
        return null;
    }

    @Override
    protected Activity getActivity() {
        return ProfileActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initView() {
        this.moment = (CircleImageView) findViewById(R.id.moment);
        this.wtchat = (CircleImageView) findViewById(R.id.wtchat);
        this.qzone = (CircleImageView) findViewById(R.id.qzone);
        this.qq = (CircleImageView) findViewById(R.id.qq);
        this.quitlayout = (CardView) findViewById(R.id.quit_layout);
        this.advicelayout = (CardView) findViewById(R.id.advice_layout);
        this.avatar = (CircleImageView) findViewById(R.id.avatar);
        this.toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        this.back = (ImageView) findViewById(R.id.back);
        name = (TextView) findViewById(R.id.title_text);
    }

    @Override
    protected void initEvent() {
        advicelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.startActivity(ProfileActivity.this, AdviceActivity.class);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.finishActivity(ProfileActivity.this);
            }
        });

        wtchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                WXTextObject textObject = new WXTextObject();
//                textObject.text = "Hello wrold";
//                WXMediaMessage mediaMessage = new WXMediaMessage();
//                mediaMessage.mediaObject = textObject;
//                mediaMessage.description = "Hello wrold";
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = "text";
//                req.message = mediaMessage;
//                req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                mIWXAPI.sendReq(req);
            }
        });
    }

}
