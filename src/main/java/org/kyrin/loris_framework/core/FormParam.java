package org.kyrin.loris_framework.core;

/**
 * 封装参数
 * @author kyrin
 *
 */
public class FormParam {

	private String fieldName;

	private Object fieldValue;

	public FormParam(String fieldName, Object fieldValue) {
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}

}
