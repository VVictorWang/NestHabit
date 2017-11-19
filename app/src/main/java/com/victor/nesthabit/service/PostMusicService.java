package com.victor.nesthabit.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import com.victor.nesthabit.api.NestHabitApi;
import com.victor.nesthabit.bean.PostMusicResponse;
import com.victor.nesthabit.util.PrefsUtils;
import com.victor.nesthabit.util.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by victor on 8/27/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class PostMusicService extends IntentService {

    private static OnAlarmAdded sOnAlarmAdded;
    private String musicUri = null, musicName = null;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public PostMusicService() {
        super("postMusic");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            musicUri = intent.getStringExtra("musicUri");
            musicName = intent.getStringExtra("name");
        }
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(musicUri);
        File file = new File(musicUri);
        String musicType = getContentResolver().getType(uri);
        RequestBody requestFile = RequestBody.create(MediaType.parse
                (musicType), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("name", file.getName(),
//                requestFile);

        Observable<PostMusicResponse> observable = NestHabitApi.getInstance().postMusic(Utils
                .getUsername(), file.getName(), requestFile, Utils.getHeader(), musicType);

        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PostMusicResponse>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(PostMusicResponse postMusicResponse) {
                        if (sOnAlarmAdded != null) {
                            sOnAlarmAdded.onAlarmAdded(postMusicResponse.get_id());
                        }
                        PrefsUtils.putValue(PostMusicService.this, postMusicResponse.get_id(),
                                musicUri);
                    }
                });
    }


    public static void setOnAlarmAdded(OnAlarmAdded onAlarmAdded) {
        sOnAlarmAdded = onAlarmAdded;
    }


    public interface OnAlarmAdded {
        void onAlarmAdded(String musicId);
    }
}
