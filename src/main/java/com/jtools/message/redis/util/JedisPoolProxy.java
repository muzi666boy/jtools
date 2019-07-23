// Copyright 2015 Baidu Inc. All rights reserved.

package com.jtools.message.redis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.List;

import com.jtools.common.LogMessageBuilder;
import com.jtools.common.NetworkUtils;
import com.jtools.common.config.Config;
import com.jtools.common.exception.JtoolsException;
import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * Proxy for Jedis pool.
 *
 * @author Li Wenwei(liwenwei@baidu.com)
 */
public class JedisPoolProxy {
    private static final Logger logger = Logger.getLogger(JedisPoolProxy.class);

    private static final int RETRY_TIMES = 3;

    private static JedisPoolProxy instance;

    private List<JedisPool> jedisPools;

    private static void init() {
        Config config = null;
        try {
            config = new Yaml().loadAs(new FileInputStream(ResourceUtils.getFile("classpath:config-dev.yaml")),
                    Config.class);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
//        PropertyConfigurator.configure(config.getLogConfigPath());
//        Config.init(config);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(config.getRedisMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(config.getRedisMaxWaitMillis());
        jedisPoolConfig.setMaxIdle(config.getRedisMaxIdle());
        instance = new JedisPoolProxy(jedisPoolConfig, config.getRedisServerUrls());

        logger.info(new LogMessageBuilder().withMessage("Redis pool init successed")
                .withParameter("redisServerUrl", config.getRedisServerUrls())
                .withParameter("jedisPoolConfig", jedisPoolConfig));
    }

    public static JedisPoolProxy getInstance() {
        if (instance == null) {
            synchronized (JedisPoolProxy.class) {
                init();
            }
        }
        return instance;
    }

    private JedisPoolProxy(JedisPoolConfig jedisPoolConfig, List<String> ips) {
        Preconditions.checkNotNull(jedisPoolConfig);
        Preconditions.checkArgument(!CollectionUtils.isEmpty(ips));

        jedisPools = Lists.newArrayList();
        for (String ip : ips) {
            InetSocketAddress socketAddress = NetworkUtils.parseSocketAddress(ip);
            jedisPools.add(new JedisPool(jedisPoolConfig, socketAddress.getHostName(), socketAddress.getPort()));
        }
    }

    /**
     * Get resource from jedis pool proxy, we shoud retry get jedis 3 times if failed.
     *
     * @return The jedis.
     *
     * @throws JtoolsException Throw JtoolsException if get jedis failed.
     */
    public Jedis getResource() throws JtoolsException {
        // Try to get Jedis from pool.
        for (int i = 0; i < RETRY_TIMES; i++) {
            try {
                return jedisPools.get(RandomUtils.nextInt(0, jedisPools.size())).getResource();
            } catch (Exception e) {
                logger.error("Cannot get jedis from pool.", e);
            }
        }

        throw new JtoolsException("Cannot get jedis from pool.");
    }
}
