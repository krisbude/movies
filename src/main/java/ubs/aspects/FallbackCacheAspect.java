package ubs.aspects;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ubs.logic.CacheFallback;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
@Component
public class FallbackCacheAspect {

	@Autowired
	private CacheFallback cacheFallback;

	private Logger logger = Logger.getLogger(getClass().getName());

	@Around("@annotation(ubs.annotations.FallbackCacheable) && execution(* *(..))")
	public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (Throwable t) {
			logger.log(Level.SEVERE, t, ()-> t.getMessage());
			return cacheFallback(joinPoint);
		}

	}

	private Object cacheFallback(ProceedingJoinPoint joinPoint){
		Method method = resolveMethod(joinPoint);
		if (method != null){
			return cacheFallback.resolveCachedResultFromInvocation(method, joinPoint.getArgs()[0]);
		}
		return null;
	}

	private Method resolveMethod(ProceedingJoinPoint joinPoint){
		final String methodName = joinPoint.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature) joinPoint
				.getSignature();
		Method method = methodSignature.getMethod();
		try{
		method = joinPoint.getTarget().getClass()
				.getDeclaredMethod(methodName, method.getParameterTypes());
		} catch(NoSuchMethodException e){
			//log error
			return null;
		}
		return method;		
	}

}
