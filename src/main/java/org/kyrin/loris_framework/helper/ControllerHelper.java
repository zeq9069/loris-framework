package org.kyrin.loris_framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kyrin.loris_framework.annatation.Action;
import org.kyrin.loris_framework.core.Handler;
import org.kyrin.loris_framework.core.Request;
import org.kyrin.loris_framework.utils.ArrayUtil;
import org.kyrin.loris_framework.utils.CollectionUtil;

/**
 * 
 * 控制器助手类
 * @author kyrin
 *
 */
public final class ControllerHelper {

	/**
	 * 用于存放请求与处理类的映射关系
	 */
	private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

	static {
		//获取所有的controller类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (CollectionUtil.isNotEmpty(controllerClassSet)) {
			//遍历controller
			for (Class<?> controllerClass : controllerClassSet) {
				//获取controller中类定义的方法
				Method[] methods = controllerClass.getDeclaredMethods();
				for (Method method : methods) {
					//判断当前方法是否带有  Action 注解
					if (method.isAnnotationPresent(Action.class)) {
						//从Action中获取url映射规则
						Action action = method.getAnnotation(Action.class);
						String mapping = action.value();
						//验证 URL 规则
						if (mapping.matches("\\w+:/\\w*")) {
							String[] array = mapping.split(":");
							if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
								//获取请求方法和路径
								String requestMethod = array[0];
								String requestPath = array[1];
								Request request = new Request(requestMethod, requestPath);
								Handler handler = new Handler(controllerClass, method);

								//初始化 action map
								ACTION_MAP.put(request, handler);
							}
						}
					}
				}
			}
		}
	}

	public static Handler getHandler(String requestMethod, String requestPath) {
		Request request = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(request);
	}
}
