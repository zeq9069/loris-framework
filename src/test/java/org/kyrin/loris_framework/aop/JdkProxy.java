package org.kyrin.loris_framework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk 动态代理
 * @author kyrin
 *
 */
public class JdkProxy implements InvocationHandler {

	Object target;

	public JdkProxy(Object target) {
		this.target = target;
	}

	public Object getProxy() {
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		Object result = method.invoke(target, args);
		after();
		return result;
	}

	public void before() {
		System.out.println("----------before----------");
	}

	public void after() {
		System.out.println("----------after----------");
	}

}
