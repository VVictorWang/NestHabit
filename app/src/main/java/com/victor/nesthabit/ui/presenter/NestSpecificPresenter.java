package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.PunchInfo;
import com.victor.nesthabit.repository.ChatRepository;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.PunchRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.NestSpecificContract;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class NestSpecificPresenter extends RxPresenter implements NestSpecificContract.Presenter {
    public static final String TAG = "@victor NsetSpecific";
    private NestSpecificContract.View mView;

    private NestRepository mNestRepository;
    private PunchRepository mPunchRepository;
    private ChatRepository mChatRepository;

    public NestSpecificPresenter(NestSpecificContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mNestRepository = NestRepository.getInstance();
        mPunchRepository = PunchRepository.getInstance();
        mChatRepository = ChatRepository.getInstance();
    }

    @Override
    public void start() {
        String objectId = mView.getNestId();
        if (objectId != null) {
            mNestRepository.loadNestInfo(objectId, new NetWorkBoundUtils.CallBack<NestInfo>() {
                @Override
                public void callSuccess(Observable<NestInfo> result) {
                    result.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(nestInfo -> {
                                mView.setMaxProgress(nestInfo.getChallenge_days());
                                mView.setToolbar(nestInfo.getName());
                                Observable.from(nestInfo.getMembers())
                                        .subscribeOn(Schedulers.io())
                                        .filter(membersBean -> membersBean.getUserId().equals(Utils
                                                .getUserId()))
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(membersBean -> {
                                            mView.setTotalday(membersBean.getKept_days() + 3);
                                            mView.setConstantDay(membersBean
                                                    .getConstant_days() + 2);
                                            mView.setTotalProgress(membersBean.getKept_days() + 3);
                                            mView.setConstantProgresss(membersBean
                                                    .getConstant_days() + 2);
                                        });
                                mView.clearPunchInfo();
                                for (String puchId : nestInfo.getPunchlogs()) {
                                    mPunchRepository.getPunchInfo(puchId, new NetWorkBoundUtils
                                            .CallBack<PunchInfo>() {
                                        @Override
                                        public void callSuccess(Observable<PunchInfo> result) {
                                            result.subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(punchInfo -> mView.addPunchIno
                                                            (punchInfo));
                                        }

                                        @Override
                                        public void callFailure(String errorMessage) {
                                        }
                                    });
                                }
                                mView.clearChatInfo();
                                for (String chatId : nestInfo.getChatlogs()) {
                                    mChatRepository.getChatInfo(chatId, new NetWorkBoundUtils
                                            .CallBack<ChatInfo>() {

                                        @Override
                                        public void callSuccess(Observable<ChatInfo> result) {
                                            result.subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(chatInfo -> mView.addChatInfo
                                                            (chatInfo));
                                        }

                                        @Override
                                        public void callFailure(String errorMessage) {
                                        }
                                    });
                                }
                            });
                }

                @Override
                public void callFailure(String errorMessage) {
                    mView.showMyToast("error: " + errorMessage);
                }
            });
        }
    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void checkin() {
        mView.setTotalday(mView.getTotalday() + 1);
        mView.setConstantDay(mView.getConstantDay() + 1);
        mView.setTotalProgress(mView.getTotalday());
        mView.setConstantProgresss(mView.getConstantDay());
    }

}
