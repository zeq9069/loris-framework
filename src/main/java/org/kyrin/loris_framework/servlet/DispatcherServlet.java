package org.kyrin.loris_framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kyrin.loris_framework.config.ConfigHelper;
import org.kyrin.loris_framework.core.Handler;
import org.kyrin.loris_framework.core.HelperLoad;
import org.kyrin.loris_framework.core.Param;
import org.kyrin.loris_framework.data.Data;
import org.kyrin.loris_framework.data.View;
import org.kyrin.loris_framework.helper.BeanHelper;
import org.kyrin.loris_framework.helper.ControllerHelper;
import org.kyrin.loris_framework.utils.ArrayUtil;
import org.kyrin.loris_framework.utils.CodecUtil;
import org.kyrin.loris_framework.utils.JsonUtil;
import org.kyrin.loris_framework.utils.ReflectionUtil;
import org.kyrin.loris_framework.utils.StreamUtil;
import org.kyrin.loris_framework.utils.StringUtil;

/**
 * 请求分发器
 * @author kyrin
 *
 */
@WebServlet(value = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1746713402448299480L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		//初始化相关helper类
		HelperLoad.init();

		//获取 ServletContext 对象（用于注册servlet）
		ServletContext servletContext = config.getServletContext();

		//注册处理jsp的servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

		//注册处理静态资源的默认servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求方法和路径
		String requestMethod = req.getMethod().toLowerCase();
		String requestPath = req.getPathInfo();

		//获取action 处理类
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if (handler != null) {
			//获取controller类及其实例
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);

			//创建请求参数对象
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Enumeration<String> paramNames = req.getParameterNames();
			while (paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				String paramValue = req.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}
			String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
			if (StringUtil.isNotEmpty(body)) {
				String[] params = StringUtil.spliteString(body, "&");
				if (ArrayUtil.isNotEmpty(params)) {
					for (String param : params) {
						String[] array = StringUtil.spliteString(param, "=");
						if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			Param param = new Param(paramMap);

			//调用Action方法
			Method actionMethod = handler.getActionMethod();
			Object result;
			actionMethod.getParameterCount();
			result = ReflectionUtil.invokeMethod(controllerBean, actionMethod,param);
			//处理action方法返回值
			if (result instanceof View) {
				View view = (View) result;
				String path = view.getPath();
				if (StringUtil.isNotEmpty(path)) {
					if (path.startsWith("/")) {
						resp.sendRedirect(req.getContextPath() + path);
					} else {
						Map<String, Object> model = view.getModel();
						for (Map.Entry<String, Object> entry : model.entrySet()) {
							req.setAttribute(entry.getKey(), entry.getValue());
						}
						req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
					}
				}
			} else if (result instanceof Data) {
				//返回json数据c
				Data data = (Data) result;
				Object model = data.getModel();
				if (model != null) {
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					PrintWriter pw = resp.getWriter();
					pw.write(JsonUtil.parseString(model));
					pw.flush();
					pw.close();
				}
			}
		}
	}
}
