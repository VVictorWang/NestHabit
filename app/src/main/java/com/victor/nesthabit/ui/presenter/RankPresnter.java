package com.victor.nesthabit.ui.presenter;

import com.victor.nesthabit.bean.NestInfo;
import com.victor.nesthabit.bean.RankItem;
import com.victor.nesthabit.bean.UserInfo;
import com.victor.nesthabit.repository.NestRepository;
import com.victor.nesthabit.repository.UserRepository;
import com.victor.nesthabit.ui.base.RxPresenter;
import com.victor.nesthabit.ui.contract.RankContract;
import com.victor.nesthabit.ui.fragment.RankTotalFragment;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 8/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class RankPresnter extends RxPresenter implements RankContract.Presenter {

    public static final String TAG = "@victor RankPresnter";
    private RankContract.View mView;

    private UserRepository mUserRepository;
    private NestRepository mNestRepository;

    public RankPresnter(RankContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mNestRepository = NestRepository.getInstance();
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public void start() {
        String nestId = mView.getNestId();
        if (nestId != null) {
            mNestRepository.loadNestInfo(nestId, new NetWorkBoundUtils.CallBack<NestInfo>() {
                @Override
                public void callSuccess(Observable<NestInfo> result) {
                    result.subscribeOn(Schedulers.io())
                            .map(nestInfo -> nestInfo.getMembers())
                            .subscribe(membersBeans -> {
                                for (NestInfo.MembersBean bean : membersBeans) {
                                    mUserRepository.getUserInfo(bean.getUserId(), new
                                            NetWorkBoundUtils.CallBack<UserInfo>() {

                                                @Override
                                                public void callSuccess(Observable<UserInfo>
                                                                                result) {
                                                    result.subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers
                                                                    .mainThread())
                                                            .subscribe(userInfo -> {
                                                                RankItem rankItem = new RankItem();
                                                                rankItem.setDays(mView.getType() ==
                                                                        RankTotalFragment
                                                                                .TOTAL_TYPE ?
                                                                        bean.getKept_days() : bean
                                                                        .getConstant_days());
                                                                rankItem.setName(userInfo
                                                                        .getUsername());
                                                                rankItem.setAvatarUrl(userInfo
                                                                        .getAvatar());
                                                                mView.addItem(rankItem);
                                                            });

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

                }
            });
        }

    }

    @Override
    public void unscribe() {
        unSubscribe();
    }
}
