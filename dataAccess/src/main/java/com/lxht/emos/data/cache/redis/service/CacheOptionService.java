package com.lxht.emos.data.cache.redis.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

/**
 * Created by yulifan on 16/7/18.
 */
@Service(value="cacheOptionService")
public class CacheOptionService {
    @Autowired
    public ShardedJedisPool shardedJedisPool;

    protected Logger logger = Logger.getLogger(this.getClass());

    public void closeRedis( ShardedJedis jedis) {
        if(jedis != null) {
            try{
                jedis.close();
            }catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public void updateValueToRedis(String key,String field,String newVal) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            jedis.hset(key, field, newVal);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeRedis(jedis);
        }
    }


    /**
     * 断该键是否存在
     * @param key
     * @return
     */
    public Boolean existsKey(String key){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {
            logger.error(e);
            return false;
        } finally {
            closeRedis(jedis);
        }

    }

    public Long incrKey(String key,Long value){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
             boolean isExists = jedis.exists(key);
            if(!isExists){
                jedis.set(key,"0");
            }
            return  jedis.incrBy(key, value);

        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public String  getValue(String key){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
           return jedis.get(key);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public String  getValue(String key,String field){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }


    public Long  remove(String key){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public String  setValue(String key,String value){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }
    public Long  setValue(String key,String field,String value,int expire){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            Long result =  jedis.hset(key, field, value);
            jedis.expire(key,expire);
            return result;
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }

    public Long  hdelData(String key,String field){
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error(e);
            return null;
        } finally {
            closeRedis(jedis);
        }
    }


    /**
     * 从redis中获取值
     * @param key
     * @param field
     * @return
     */
    public String getValueFromRedis(String key,String field) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
           return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(e);
            return  null;
        } finally {
            closeRedis(jedis);
        }
    }

    public void clearDataByKeys(List<String> keys) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            for(String key :keys) {
                jedis.del(key);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            closeRedis(jedis);
        }
    }

    public String getValueByKey(String key,String field,ShardedJedis jedis) {
        return jedis.hget(key,field);
    }
}
