/*
 * Copyright 1998-2012 360buy.com All right reserved. This software is the confidential and proprietary information of
 * 360buy.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with 360buy.com.
 */
package com.lxht.emos.data.cache.redis.core;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 类RedisTemplate.java的实现描述：Redis 模板类
 * 
 */
public class RedisTemplate<Key, Val> {

    private ShardedJedisPool shardedJedisPool;

    /**
     * redis 命令执行核心方法.
     * 
     * @param redisCallback
     * @return
     */
    public <V> V execute(RedisCallback<V> redisCallback) {

        ShardedJedis jedis = shardedJedisPool.getResource();
        try {
            return redisCallback.doInRedis(jedis);
        } catch (Exception e) {
        	shardedJedisPool.returnBrokenResource(jedis);
        	return null ;
		} finally {
			shardedJedisPool.returnResource(jedis) ;
        }
    }
    
    /**
     * shardedJedisPool destroy
     */
    public void destroy() {
        shardedJedisPool.destroy();
    }

    /**
     * @return the shardedJedisPool
     */
    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    /**
     * @param shardedJedisPool the shardedJedisPool to set
     */
    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }
}
