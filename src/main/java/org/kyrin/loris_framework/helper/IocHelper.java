package org.kyrin.loris_framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.kyrin.loris_framework.annatation.Inject;
import org.kyrin.loris_framework.utils.ArrayUtil;
import org.kyrin.loris_framework.utils.CollectionUtil;
import org.kyrin.loris_framework.utils.ReflectionUtil;

/**
 * 依赖注入助手类
 * @author kyrin
 *
 */
public final class IocHelper {

	static {
		//获取所有的 Bean 类和 Bean 实例对应的关系
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

		if (CollectionUtil.isNotEmpty(beanMap)) {
			//遍历 Bean Map
			for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				//从beanMap中获取bean和bean实例
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				//获取所有bean类定义中的所有成员变量
				Field[] beanFields = beanClass.getDeclaredFields();
				if (ArrayUtil.isNotEmpty(beanFields)) {
					//遍历 bean field
					for (Field beanField : beanFields) {
						//判断 bean Field是否带有 Inject注解
						if (beanField.isAnnotationPresent(Inject.class)) {
							//在 Bean Map 中获取 bean Field对应的实例
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if (beanFieldInstance != null) {
								//通过反射初始化BeanField的值
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}
}
