package cn.com.dhcc.credit.approval.utils;

import org.redisson.api.RedissonClient;

/**
 * redisson 工具类
 * @date 2020年6月29日
 * @author wanghaowei
 */
public class RedissonUtil {

    public static RedissonClient redissonClient(){
        return cn.com.dhcc.credit.platform.util.redis.RedissonUtil.getLocalRedisson();
    }
}
