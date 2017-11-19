package com.victor.nesthabit.repository;

/**
 * @author victor
 * @date 11/18/17
 * @email chengyiwang@hustunique.com
 * @blog www.victorwan.cn                                            #
 */

public interface ReposityCallback<T> {
    void call(T data);
}
