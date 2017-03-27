/*
 * Copyright 1998-2012 360buy.com All right reserved. This software is the confidential and proprietary information of
 * 360buy.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with 360buy.com.
 */
package com.lxht.emos.data.cache.redis;

import java.io.Serializable;

/**
 * 类BaseCache.java的实现描述：缓存基础接口
 * 
 */
public interface BaseCache  {

    /**
     * 缓存一个对象到cache中，此对象只包括新增操作，如果对象已存在会覆盖掉之前的对象，没有锁操作，并发场景有问题.
     * 
     * @param key 键
     * @param value 值
     * @return boolean 是否存储成功,true:成功,false:失败.
     * @throws Exception
     */
    boolean put(String key, Serializable value);

    /**
     * 缓存一个对象到cache中，此对象只包括新增操作，如果对象已存在会覆盖掉之前的对象，没有锁操作，并发场景有问题.此方法可以设置缓存失效时间.
     * 
     * @param key
     * @param value
     * @param expirationTime 失效时间,以秒为单位.
     * @throws Exception
     */
    boolean put(String key, Serializable value, int expirationTime);

    /**
     * 获取指定key的val.
     * 
     * @param key
     * @return 如果没有查到返回null.
     */
    <V extends Serializable> V get(String key);

    /**
     * 根据key删除缓存中的数据.
     * 
     * @param key
     */
    void remove(String key);

}
