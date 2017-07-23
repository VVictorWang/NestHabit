package com.victor.nesthabit.data;

/**
 * Created by victor on 7/23/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */

public class LoginResponse {

    /**
     * msg : Logged in successfully!
     * Authorization :
     * 3bd75cef7ffb3e9692380855e51ed76d3efb87459aeb3be16d1e0479d59565dabf94c134591f4e54f7754144145cf57e
     */

    private String msg;
    private String Authorization;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String Authorization) {
        this.Authorization = Authorization;
    }
}
