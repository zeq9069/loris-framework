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

import org.apache.commons.lang3.ClassUtils.Interfaces;
import org.kyrin.loris_framework.config.ConfigHelper;
import org.kyrin.loris_framework.core.Handler;
import org.kyrin.loris_framework.core.HelperLoad;
import org.kyrin.loris_framework.core.Param;
import org.kyrin.loris_framework.core.RequestHelper;
import org.kyrin.loris_framework.data.Data;
import org.kyrin.loris_framework.data.View;
import org.kyrin.loris_framework.helper.BeanHelper;
import org.kyrin.loris_framework.helper.ControllerHelper;
import org.kyrin.loris_framework.upload.UploadHelper;
import org.kyrin.loris_framework.utils.ArrayUtil;
import org.kyrin.loris_framework.utils.CodecUtil;
import org.kyrin.loris_framework.utils.JsonUtil;
import org.kyrin.loris_framework.utils.ReflectionUtil;
import org.kyrin.loris_framework.utils.StreamUtil;
import org.kyrin.loris_framework.utils.StringUtil;

/**
 * 请求分发器 （存在一个bug，当上传的参数是个数组时，解析不全的问题）
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
		
		UploadHelper.init(servletContext);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取请求方法和路径
		String requestMethod = req.getMethod().toLowerCase();
		String requestPath = req.getPathInfo();
		
		if(requestPath.equals("/favicon.ico")){
			return;
		}

		//获取action 处理类
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if (handler != null) {
			//获取controller类及其实例
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);

			Param param;
			if(UploadHelper.isMultipart(req)){
				param = UploadHelper.createParam(req);
			}else{
				param = RequestHelper.createParam(req);
			}
			
			Object result;
			
			Method actionMethod = handler.getActionMethod();
			if(param.isEmpty()){
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
			}else{
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			}
			
			if(result instanceof View){
				handleView((View)result, req, resp);
			}else if(result instanceof Data){
				handleDataResult((Data)result,resp);
			}
		}
	}
	
	private void handleView(View view,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		String path = view.getPath();
		if (StringUtil.isNotEmpty(path)) {
			if (path.startsWith("/")) {
				response.sendRedirect(request.getContextPath() + path);
			} else {
				Map<String, Object> model = view.getModel();
				for (Map.Entry<String, Object> entry : model.entrySet()) {
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
			}
		}
	}
	
	private void handleDataResult(Data data,HttpServletResponse response) throws IOException{
		//返回json数据
		Object model = data.getModel();
		if (model != null) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(JsonUtil.parseString(model));
			pw.flush();
			pw.close();
		}
	}
}
