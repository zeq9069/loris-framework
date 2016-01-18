package org.kyrin.loris_framework.core;

import java.io.InputStream;

/**
 * 封装上传文件参数
 * @author kyrin
 *
 */
public class FileParam {

	//表单的字段名
	private String fieldName;

	//文件名
	private String fileName;

	//文件大小
	private long fileSize;

	//表示上传文件的Content-type ,可以判断文件类型
	private String contentType;

	//上传文件的输入字节流
	private InputStream inputStream;

	public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.contentType = contentType;
		this.inputStream = inputStream;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

}
