package org.kyrin.loris_framework.aop;

/**
 * 代理接口
 * @author kyrin
 *
 */
public interface Proxy {

	/**
	 * 执行链式代理
	 */
	Object doProxy(ProxyChain proxyChain);

}
