package org.kyrin.loris_framework.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.kyrin.loris_framework.config.ConfigHelper;
import org.kyrin.loris_framework.core.FileParam;
import org.kyrin.loris_framework.core.FormParam;
import org.kyrin.loris_framework.core.Param;
import org.kyrin.loris_framework.utils.CollectionUtil;
import org.kyrin.loris_framework.utils.FileUtil;
import org.kyrin.loris_framework.utils.StreamUtil;
import org.kyrin.loris_framework.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件上传助手类
 * @author kyrin
 *
 */
public final class UploadHelper {

	private static final Logger logger = LoggerFactory.getLogger(UploadHelper.class);

	/**
	 * apache Commons FileUpload 提供的Servlet文件上传对象
	 */
	private static ServletFileUpload servletFileUpload;

	/**
	 * 初始化
	 */
	public static void init(ServletContext servletContext) {

		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,
				repository));
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if (uploadLimit != 0) {
			servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
		}
	}

	/**
	 * 判断请求是否为 multipart 类型
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		return servletFileUpload.isMultipartContent(request);
	}

	/**
	 * 创建请求对象
	 */
	public static Param createParam(HttpServletRequest request) {

		List<FormParam> formParamList = new ArrayList<FormParam>();
		List<FileParam> fileParamList = new ArrayList<FileParam>();

		try {
			Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
			if (CollectionUtil.isNotEmpty(fileItemListMap)) {
				for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
					String fieldName = fileItemListEntry.getKey();
					List<FileItem> fileItemList = fileItemListEntry.getValue();
					if (CollectionUtil.isNotEmpty(fileItemList)) {
						for (FileItem fileItem : fileItemList) {
							if (fileItem.isFormField()) {
								String fieldValue = fileItem.getString("UTF-8");
								formParamList.add(new FormParam(fieldName, fieldValue));
							} else {
								String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(),
										"UTF-8"));
								if (StringUtil.isNotEmpty(fileName)) {
									long fileSize = fileItem.getSize();
									String contentType = fileItem.getContentType();
									InputStream inputStream = fileItem.getInputStream();
									fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType,
											inputStream));
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("create param failure", e);
			throw new RuntimeException(e);
		}
		return new Param(formParamList, fileParamList);
	}

	public static void uploadFile(String basePath, FileParam fileParam) {
		try {
			if (fileParam != null) {
				String filePath = basePath + fileParam.getFileName();
				FileUtil.createFile(filePath);
				InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
				OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
				StreamUtil.copyStream(inputStream, outputStream);
			}

		} catch (Exception e) {
			logger.error("upload file failure",e);
			throw new RuntimeException(e);
		}
	}
	
	public static void uploadFile(String basePath,List<FileParam> fileParmList){
		try{
		if(CollectionUtil.isNotEmpty(fileParmList)){
			for(FileParam fileParam : fileParmList){
				uploadFile(basePath, fileParam);
			}
		}
		}catch(Exception e){
			logger.error("upload file failure",e);
			throw new RuntimeException(e);
		}
	}
}
