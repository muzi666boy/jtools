package com.jtools.message.redis.pubsub;

import com.jtools.message.redis.util.JedisPoolProxy;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

/**
 * 消息发布方
 *
 * @author yamikaze
 */
public class Publisher {

    static final String CHANNEL_KEY = "channel:message";
    private Jedis jedis;

    private Publisher() {
        jedis = JedisPoolProxy.getInstance().getResource();
    }

    private void publishMessage(String message) {
        if (StringUtils.isBlank(message)) {
            return;
        }
        jedis.publish(CHANNEL_KEY, message);
    }

    public static void main(String[] args) {
        Publisher publisher = new Publisher();
        publisher.publishMessage("2Hello Redis!" + System.currentTimeMillis());
//        publisher.publishMessage("exit");
    }
}
