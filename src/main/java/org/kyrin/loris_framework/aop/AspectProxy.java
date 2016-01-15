package org.kyrin.loris_framework.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 切面代理
 * @author kyrin
 *
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

	public Object doProxy(ProxyChain proxyChain) {
		Object result = null;
		Class<?> clazz = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		begin();
		try {
			if (intercepter(clazz, method, params)) {
				before(clazz, method, params);
				result = proxyChain.doProxyChain();
				after(clazz, method, params);
			} else {
				result = proxyChain.doProxyChain();
			}
		} catch (Throwable e) {
			logger.error("proxy failure", e);
			throw new RuntimeException();
		} finally {
			end();
		}
		return result;
	}

	public void begin() {

	}

	public void end() {

	}

	public boolean intercepter(Class<?> clazz, Method method, Object[] params) {
		return true;
	}

	public void before(Class<?> clazz, Method method, Object[] params) {

	}

	public void after(Class<?> clazz, Method method, Object[] params) {

	}

}
