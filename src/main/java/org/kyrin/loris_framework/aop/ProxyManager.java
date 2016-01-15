package org.kyrin.loris_framework.aop;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理管理类
 * @author kyrin
 *
 */
public class ProxyManager {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {

		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			public Object intercept(Object targetObject, Method method, Object[] args, MethodProxy methodProxy)
					throws Throwable {
				return new ProxyChain(targetClass, targetObject, method, methodProxy, args, proxyList).doProxyChain();
			}
		});
	}
}
