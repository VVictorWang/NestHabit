package com.victor.nesthabit.bean;

import android.arch.persistence.room.Entity;

/**
 * Created by victor on 8/21/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class PostFileResponse {


    /**
     * filename : myPicture.jpg
     * url : http://bmob-cdn-24.b0.upaiyun.com/2016/04/14/9306f2e74090d668801eac8814b3f56f.jpg
     * cdn : upyun
     */

    private String filename;
    private String url;
    private String cdn;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }
}
