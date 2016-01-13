package org.kyrin.loris_framework.helper;

import java.util.HashSet;
import java.util.Set;

import org.kyrin.loris_framework.annatation.Controller;
import org.kyrin.loris_framework.annatation.Service;
import org.kyrin.loris_framework.config.ConfigHelper;
import org.kyrin.loris_framework.utils.ClassUtil;

/**
 * 类操作助手
 * @author kyrin
 *
 */
public final class ClassHelper {

	/**
	 * 定义类集合	(用于存放所有已加载的类)
	 */
	private static final Set<Class<?>> CLASS_SET;

	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}

	/**
	 * 获取应用包下所有已加载的类
	 */
	public static Set<Class<?>> getClassSet() {
		return CLASS_SET;
	}

	/**
	 * 获取应用包下所有  Service 类
	 */
	public static Set<Class<?>> getServiceClassSet() {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> clazz : CLASS_SET) {
			if (clazz.isAnnotationPresent(Service.class)) {
				classSet.add(clazz);
			}
		}
		return classSet;
	}

	/**
	 * 获取应用包下所有  Controller 类
	 */
	public static Set<Class<?>> getControllerClassSet() {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for (Class<?> clazz : CLASS_SET) {
			if (clazz.isAnnotationPresent(Controller.class)) {
				classSet.add(clazz);
			}
		}
		return classSet;
	}

	/**
	 * 获取应用包下所有 Bean类  (包括 Service、Controller等)
	 */
	public static Set<Class<?>> getBeanClassSet() {
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
}
