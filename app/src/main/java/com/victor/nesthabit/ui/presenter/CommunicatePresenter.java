package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.UpdateInfo;
import com.victor.nesthabit.repository.ChatRepository;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.ReposityCallback;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.CommunicateContract;
import com.victor.nesthabit.util.NetWorkBoundUtils;
import com.victor.nesthabit.util.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 8/22/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class CommunicatePresenter extends RxPresenter implements CommunicateContract.Presenter {

    public static final String TAG = "@victor CommPresenter";
    private CommunicateContract.View mView;
    private ChatRepository mChatRepository;

    public CommunicatePresenter(CommunicateContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mChatRepository = ChatRepository.getInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }

    @Override
    public void sendMessage() {
        ChatInfo chatInfo = new ChatInfo();
        chatInfo.setUserId(Utils.getUserId());
        chatInfo.setContents(mView.getMessage());
        mChatRepository.addChat(chatInfo, new ReposityCallback<AddResponse>() {
            @Override
            public void callSuccess(AddResponse data) {
                mChatRepository.getChatInfo(data.getObjectId(), new NetWorkBoundUtils
                        .CallBack<ChatInfo>() {

                    @Override
                    public void callSuccess(Observable<ChatInfo> result) {
                        result.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(chatInfo1 -> mView.addItem(chatInfo1));
                    }

                    @Override
                    public void callFailure(String errorMessage) {

                    }
                });
                NestRepository nestRepository = NestRepository.getInstance();
                nestRepository.loadNestInfo(mView.getNestId(), new NetWorkBoundUtils
                        .CallBack<NestInfo>() {
                    @Override
                    public void callSuccess(Observable<NestInfo> result) {
                        result.subscribeOn(Schedulers.io())
                                .doOnNext(nestInfo -> {
                                    List<String> chats = new ArrayList<>();
                                    chats.addAll(nestInfo.getChatlogs());
                                    chats.add(data.getObjectId());
                                    nestInfo.setChatlogs(chats);
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
            }

            @Override
            public void callFailure(String errorMessage) {
                mView.showToast(errorMessage);
            }
        });
    }
}
