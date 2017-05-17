package ubs.logic;

import java.lang.reflect.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import ubs.annotations.FallbackCacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheFallback {

	@Autowired
	private CacheManager cacheManager;

	public Object resolveCachedResultFromInvocation(Method method, Object arg){
		FallbackCacheable[] fallbacks = method.getAnnotationsByType(FallbackCacheable.class);
		FallbackCacheable fallback = fallbacks[0];
		String cachName = fallback.value();
		String key = fallback.key();
		Cache cache = cacheManager.getCache(cachName);
		if (cache != null){
			Cache.ValueWrapper wrapper = cacheManager.getCache(cachName).get(arg);
			if (wrapper != null){
				return wrapper.get();
			}
		}
		return null;
	}

}