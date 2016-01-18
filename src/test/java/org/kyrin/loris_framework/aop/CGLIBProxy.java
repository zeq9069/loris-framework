package org.kyrin.loris_framework.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib实现
 * @author kyrin
 *
 */
public class CGLIBProxy implements MethodInterceptor {

	private CGLIBProxy() {
	}

	public static final class Instance {
		private static final CGLIBProxy proxy = new CGLIBProxy();
	}

	public static final CGLIBProxy newInstance() {
		return Instance.proxy;
	}

	@SuppressWarnings({ "unchecked" })
	public <T> T getProxy(Class<T> target) {
		return (T) Enhancer.create(target, this);
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		before();
		Object result = proxy.invokeSuper(obj, args);
		after();
		return result;
	}

	private void before() {
		System.out.println("----------before----------");
	}

	private void after() {
		System.out.println("----------after----------");
	}

}
