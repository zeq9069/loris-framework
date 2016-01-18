package org.kyrin.loris_framework.aop;

/**
 * 
 * @author kyrin
 *
 */
public class HelloImpl implements Hello {

	public String sayHello(String content) {
		System.out.println(content);
		return "hello";
	}

}
