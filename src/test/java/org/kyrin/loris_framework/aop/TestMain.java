package org.kyrin.loris_framework.aop;

/**
 * 测试动态代理
 * @author kyrin
 *
 */
public class TestMain {

	private static Object jdkProxy() {
		JdkProxy proxy = new JdkProxy(new HelloImpl());
		Hello hello = (Hello) proxy.getProxy();
		return hello.sayHello("OK , Kyrin say Hello for JDKProxy !");
	}

	private static Object cglibProxy() {
		Hello hello = CGLIBProxy.newInstance().getProxy(HelloImpl.class);
		return hello.sayHello("OK , Kyrin say Hello for cglibProxy !");
	}

	public static void main(String[] args) {
		jdkProxy();
		cglibProxy();
	}

}
