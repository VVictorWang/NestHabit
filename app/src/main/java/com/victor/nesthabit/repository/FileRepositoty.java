package com.victor.nesthabit.repository;

import com.victor.nesthabit.MyApplication;
import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.PostFileResponse;
import com.victor.nesthabit.util.ACache;
import com.victor.nesthabit.util.Utils;

import java.io.File;
import java.io.IOException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author victor
 * @date 11/22/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public class FileRepositoty {
    private final NestHabitApi mNestHabitApi;

    private static FileRepositoty instance;

    private FileRepositoty(NestHabitApi nestHabitApi) {
        mNestHabitApi = nestHabitApi;
    }

    public static FileRepositoty getInstance() {
        if (instance == null) {
            instance = new FileRepositoty(NestHabitApi.getInstance());
        }
        return instance;
    }

    public void postMusic(String fileName, File file, ReposityCallback<PostFileResponse> callback) {
        mNestHabitApi.postMusic(fileName, file).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()) {
                        callback.callSuccess(response.body());
                        ACache.get(MyApplication.getInstance()).put(fileName, file);
                    } else {
                        try {
                            callback.callFailure(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getMusic(PostFileResponse data, ReposityCallback<File> callback) {
        File file = ACache.get(MyApplication.getInstance()).getAsFile(data.getFilename());
        if (file != null) {
            callback.callSuccess(file);
        } else {
            mNestHabitApi.download(data.getUrl()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBodyResponse -> {
                        if (responseBodyResponse.isSuccessful()) {
                            try {
                                callback.callSuccess(Utils.Bytes2File(data.getFilename(),
                                        responseBodyResponse.body().bytes()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                callback.callFailure(responseBodyResponse.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }
}
