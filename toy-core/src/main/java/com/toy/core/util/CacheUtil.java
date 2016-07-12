package com.toy.core.util;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import net.oschina.j2cache.J2Cache;

public class CacheUtil {

	public CacheUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void test() {
		CacheChannel cache = J2Cache.getChannel();
		cache.set("cache1", "key1", "OSChina.net");
		cache.get("cache1", "key1");
	}
	
	public static CacheObject getCache(String cache, String key){
		CacheChannel cc = J2Cache.getChannel();
		return cc.get(cache, key);
	}

}
