package org.kyrin.loris_framework.core;

import org.kyrin.loris_framework.helper.BeanHelper;
import org.kyrin.loris_framework.helper.ClassHelper;
import org.kyrin.loris_framework.helper.ControllerHelper;
import org.kyrin.loris_framework.helper.IocHelper;
import org.kyrin.loris_framework.utils.ClassUtil;

/**
 * 加载相应的Helper类
 * @author kyrin
 *
 */
public final class HelperLoad {

	public static void init() {
		Class<?>[] classList = { ClassHelper.class, BeanHelper.class, ControllerHelper.class, IocHelper.class };
		for (Class<?> clazz : classList) {
			ClassUtil.loadClass(clazz.getName());
		}
	}
}
