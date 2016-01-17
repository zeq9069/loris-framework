package org.kyrin.loris_framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kyrin.loris_framework.annatation.Service;
import org.kyrin.loris_framework.aop.AspectProxy;
import org.kyrin.loris_framework.aop.Proxy;
import org.kyrin.loris_framework.aop.ProxyManager;
import org.kyrin.loris_framework.aop.annatation.Aspect;
import org.kyrin.loris_framework.transaction.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * aop 辅助类
 * @author kyrin
 *
 */
public final class AopHelper {

	private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);

	static {
		try {
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			logger.error("aop failure", e);
			throw new RuntimeException(e);
		}
	}

	private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		Class<? extends Annotation> annotationClass = aspect.value();
		if (annotationClass != null && !annotationClass.equals(Aspect.class)) {
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotationClass));
		}
		return targetClassSet;
	}

	private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
		addTransactionProxy(proxyMap);
		addAspectProxy(proxyMap);
		return proxyMap;
	}

	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap)
			throws InstantiationException, IllegalAccessException {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
			Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(proxy);
				} else {
					List<Proxy> proxyList = new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
	
	private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for (Class<?> proxyClass : proxyClassSet) {
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}
	
	private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
		Set<Class<?>> serviceClass=ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TransactionProxy.class, serviceClass);
	}
}
