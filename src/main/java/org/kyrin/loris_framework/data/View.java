package org.kyrin.loris_framework.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图类
 * @author kyrin
 *
 */
public class View {

	/**
	 * 视图路径
	 */
	private String path;

	/**
	 * 模型数据
	 */
	private Map<String, Object> model;

	public View(String path) {
		this.path = path;
		model = new HashMap<String, Object>();
	}

	public View addAttribute(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public String getPath() {
		return path;
	}

}
