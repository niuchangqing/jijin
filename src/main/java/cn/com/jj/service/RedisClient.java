package cn.com.jj.service;

import redis.clients.jedis.Jedis;

public class RedisClient {
	public static Jedis getInstance() {
		Jedis jedis = new Jedis("18.191.214.185", 6979);
		jedis.auth("foobared");
		return jedis;
	}
}
