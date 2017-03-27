/*
 * Copyright 1998-2012 360buy.com All right reserved. This software is the confidential and proprietary information of
 * 360buy.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with 360buy.com.
 */
package com.lxht.emos.data.cache.redis;

import com.lxht.emos.data.cache.redis.core.RedisPiplineCallBack;
import com.lxht.emos.data.cache.redis.core.RedisTransactionCallBack;
import redis.clients.jedis.JedisPubSub;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类RedisCache.java的实现描述：Redis接口方法。
 * 
 */
public interface RedisCache extends BaseCache {

    /**
     * 缓存一个k,v值，如果key不存在执行，已经存在不执行。
     * 
     * @param key
     * @param value
     * @return
     */
    boolean putNx(String key, Serializable value);


     /**
     * 缓存一个k,v值，如果key不存在执行，已经存在不执行。可以设置失效时间.
     *
     * @param key
     * @param value
     * @return
     */
    boolean putNx(String key, Serializable value, int expTime);

    /**
     * 缓存一个Map到Cache中，增量。可以设置失效时间.
     * 
     * @param key
     * @param map
     * @param expTime
     * @return
     */
    boolean putMap(String key, Map<String, Serializable> map, int expTime);

    /**
     * 缓存一个Map到Cache中,增量
     * 
     * @param key
     * @param map
     * @return
     */
    boolean putMap(String key, Map<String, Serializable> map);

    /**
     * 添加，或者一个已存在的key 的field.
     * 
     * @param key Hash表的key.
     * @param field 字段的key
     * @param value 每个字段的值
     * @return
     */
    boolean putMap(String key, String field, Serializable value);

    /**
     * 批量添加key,value.
     * 
     * @param map
     * @param expTime
     * @return
     */
    List<Object> putMap(Map<String, Serializable> map, int expTime);

    /**
     * HGETALL key返回哈希表key中，所有的域和值。
     * 
     * @param key
     * @return
     */
    Map<String, Serializable> getMapAll(String key);

    /**
     * HMGET key field [field ...]返回哈希表key中，一个或多个给定域的值。
     * 
     * @param key
     * @param fields
     * @return
     */
    <V extends Serializable> List<V> getMap(String key, String... fields);

    /**
     * 根据key,field查询 Map中的值。
     * 
     * @param key
     * @param field
     * @return
     */
    <V extends Serializable> V getMap(String key, String field);

    /**
     * 批量获取数据.
     * 
     * @param keys
     * @return
     */
    <V extends Serializable> List<V> getList(String... keys);

    /**
     * HDEL key field [field ...]删除哈希表key中的一个或多个指定域。
     * 
     * @param key
     * @param field
     */
    void remove(String key, String... fields);

    /**
     * EXISTS 检查给定key是否存在。
     * 
     * @param key
     * @return
     */
    Boolean exists(String key);

    /**
     * EXISTS 检查给定key是否存在。
     * 
     * @param key
     * @return
     */
    Long publish(String channel, String message);

    /**
     * EXISTS 检查给定key是否存在。
     * 
     * @param key
     * @return
     */
    Boolean subscribe(JedisPubSub listner, String channel);

    /**
     * HEXISTS key field查看哈希表key中，给定域field是否存在。
     * 
     * @param key
     * @param field
     * @return
     */
    Boolean exists(String key, String field);

    /**
     * pipline模式执行，可以批量请求，批量响应.<br/>
     * 详细说明：管道通过一次性写入请求，然后一次性读取响应。也就是说jedis是：request response，request response，...；pipeline则是：request request...
     * response response的方式。这样无需每次请求都等待server端的响应。
     * 
     * @param callBack 回调方法.
     * @return
     */
    <V> V executePiplineCommand(final RedisPiplineCallBack<V> callBack);

    /**
     * 事务执行模式，对key进行加锁，可以保证原子性，当事件操作过程中如果有client修改则会再次重试，重试次数5次。
     * 
     * @param key
     * @param callBack 回调
     * @return
     */
    <V> V executeTransactionCommand(final String key, final RedisTransactionCallBack<V> callBack);

    /**
     * close redis
     */
    void close();
    
    /**
     * 添加一个sorted set值
     * 
     * @param key 
     * @param score  
     * @param value 
     * @return
     */
    boolean zadd(String key, String score, Serializable value);
    
    /**
     * 批量缓存sorted set值
     * 
     * @param key 
     * @param scoreMembers 
     * @return
     */
    Long zaddMap(final String key, final Map<String, Serializable> scoreMembers, int expTime);
    
    /**
     * 根据score删除sorted set值（删除单个）
     * 
     * @param key 
     * @param score
     * @return
     */
    void zremByScore(String key, String score);
    
    /**
     * 根据score删除sorted set值（批量删除）
     * 注：改造成与Map使用方式相同
     * 
     * @param key 
     * @param scores
     * @return
     */
    void zremByScores(String key, String... scores);
    
    /**
     * sorted set (原本的删除方法，保留)
     * 
     * @param key
     * @param start
     * @param end
     */
    void zremrangeByScore(final String key, final Double start, final Double end);
    
    /**
     * 根据score 查询单个值
     * 
     * @param key
     * @param score
     * @return
     */
    <V extends Serializable> V getSet(String key, String score);
    
    /**
     * 根据sorted set中的索引查询，此start，end是索引
     * 如果start和end都等于0,查询全部
     * 
     * @param key
     * @param start
     * @param end 
     * @return
     */
    <V extends Serializable> List<V> getSets(String key, int start, int end);
    
    /**
     * 获取set某key的count数
     * @param key
     * @return
     */
    Long countSet(String key);
    
    /**
     * 获取map中的所有field
     * @param keys
     * @return
     */
    <V extends Serializable> List<V> hKeys(String keys);
    
    /**
     * 获取map中的所有val
     * @param keys
     * @return
     */
    <V extends Serializable> List<V> hVals(String keys);
    /**
     * 向set添加数据
     * @param keys
     * @param v
     */
    <V extends Serializable> void sadd(String key, V v);
    
    
}
