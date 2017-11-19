package com.victor.nesthabit.repository;

/**
 * The interface Reposity callback.
 *
 * @param <T> the type parameter
 * @author victor
 * @date 11 /18/17
 * @email chengyiwang @hustunique.com
 * @blog www.victorwan.cn #
 */
public interface ReposityCallback<T> {
    /**
     * Call success.
     *
     * @param data the data
     */
    void callSuccess(T data);

    /**
     * Call failure.
     */
    void callFailure(String errorMessage);
}