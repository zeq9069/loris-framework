package org.kyrin.loris_framework.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.kyrin.loris_framework.utils.ArrayUtil;
import org.kyrin.loris_framework.utils.CodecUtil;
import org.kyrin.loris_framework.utils.StreamUtil;
import org.kyrin.loris_framework.utils.StringUtil;

/**
 * 请求助手类
 * @author kyrin
 *
 */
public final class RequestHelper {
	
	/**
	 * 创建请求参数
	 * @throws IOException 
	 */
	public static Param createParam(HttpServletRequest request) throws IOException{
		List<FormParam> formParamList=new ArrayList<FormParam>();
		formParamList.addAll(parseParameterNames(request));
		formParamList.addAll(parseInputStream(request));
		return new Param(formParamList);
	}
	
	private static List<FormParam> parseParameterNames(HttpServletRequest request){
		List<FormParam> formParamList = new ArrayList<FormParam>();
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String fieldName = paramNames.nextElement();
			String[] values=request.getParameterValues(fieldName);
			if(ArrayUtil.isNotEmpty(values)){
				String fieldValue;
				if(values.length == 1){
						fieldValue = values[0];
				}else{
					StringBuffer str=new StringBuffer();
					for(int i = 0;i < values.length;i++){
						str.append(values[i]);
						if(i != values.length-1){
							str.append(StringUtil.SEPARAETOR);
						}
					}
					fieldValue = str.toString();
				}
				formParamList.add(new FormParam(fieldName, fieldValue));
			}
		}
		return formParamList;
	}
	
	private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException{
		List<FormParam> formParamList=new ArrayList<FormParam>();
		String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
		if(StringUtil.isNotEmpty(body)){
			String[] kvs = StringUtil.spliteString(body, "&");
			if(ArrayUtil.isNotEmpty(kvs)){
				for(String kv : kvs){
					String[] array = StringUtil.spliteString(kv, "=");
					if(ArrayUtil.isNotEmpty(array) && array.length == 2){
						String fieldName = array[0];
						String fieldValue = array[1];
						formParamList.add(new FormParam(fieldName, fieldValue));
					}
				}
			}
		}
		return formParamList;
	}
}
