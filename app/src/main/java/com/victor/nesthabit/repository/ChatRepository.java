package com.victor.nesthabit.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.AddResponse;
import com.victor.nesthabit.bean.ChatInfo;
import com.victor.nesthabit.db.ChatDao;
import com.victor.nesthabit.db.NestHabitDataBase;
import com.victor.nesthabit.util.AppUtils;
import com.victor.nesthabit.util.NetWorkBoundUtils;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/22/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class ChatRepository {
    private final NestHabitApi mNestHabitApi;
    private final ChatDao mChatDao;

    private ChatRepository(NestHabitApi nestHabitApi, ChatDao chatDao) {
        mNestHabitApi = nestHabitApi;
        mChatDao = chatDao;
    }

    private static ChatRepository instance;

    public static ChatRepository getInstance() {
        if (instance == null) {
            instance = new ChatRepository(NestHabitApi.getInstance(), NestHabitDataBase
                    .getInstance(AppUtils.getAppContext()).chatDao());
        }
        return instance;
    }

    public void addChat(ChatInfo chatInfo, ReposityCallback<AddResponse> callback) {
        mNestHabitApi.chat(chatInfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addResponseResponse -> {
                    if (addResponseResponse.isSuccessful()) {
                        callback.callSuccess(addResponseResponse.body());
                    } else {
                        try {
                            callback.callFailure(addResponseResponse.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getChatInfo(String chatId, NetWorkBoundUtils.CallBack<ChatInfo> callBack) {
        new NetWorkBoundUtils<ChatInfo, ChatInfo>(callBack) {
            @Override
            protected void saveCallResult(@NonNull ChatInfo item) {
                mChatDao.insert(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable ChatInfo data) {
                return data == null;
            }

            @NonNull
            @Override
            protected Observable<ChatInfo> loadFromDb() {
                return Observable.just(mChatDao.loadChat(chatId));
            }

            @NonNull
            @Override
            protected Observable<Response<ChatInfo>> createCall() {
                return mNestHabitApi.getChatInfo(chatId);
            }
        };
    }
}
