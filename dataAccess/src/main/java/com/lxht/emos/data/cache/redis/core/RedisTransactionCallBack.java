/*
 * Copyright 1998-2012 360buy.com All right reserved. This software is the confidential and proprietary information of
 * 360buy.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with 360buy.com.
 */
package com.lxht.emos.data.cache.redis.core;

import redis.clients.jedis.Transaction;

/**
 * 类RedisTransaction.java的实现描述：Redis 事务处理回掉方法.
 * 
 */
public interface RedisTransactionCallBack<T> {

    /**
     * Redis 命令回调方法。
     * 
     * @param t
     * @return
     */
    T doTransaction(Transaction t);
}
