package org.kyrin.loris_framework.core;

import java.util.Map;

import org.kyrin.loris_framework.utils.CastUtil;

/**
 * 请求参数对象
 * @author kyrin
 *
 */
public class Param {

	private Map<String, Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 根据参数名获取long型参数
	 */
	public long getLong(String name) {
		return CastUtil.castLong(paramMap.get(name));
	}

	/**
	 * 根据参数名获取int型参数
	 */
	public int getInt(String name) {
		return CastUtil.castInt(paramMap.get(name));
	}

	/**
	 * 根据参数名获取String型参数
	 */
	public String getString(String name) {
		return CastUtil.castString(paramMap.get(name));
	}

	/**
	 * 根据参数名获取boolean型参数
	 */
	public boolean getBoolean(String name) {
		return CastUtil.castBoolean(paramMap.get(name));
	}

	/**
	 * 根据参数名获取boolean型参数
	 */
	public double getDouble(String name) {
		return CastUtil.castDouble(paramMap.get(name));
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

}
