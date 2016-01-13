package org.kyrin.loris_framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流操作工具
 * @author kyrin
 *
 */
public final class StreamUtil {

	private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

	/**
	 * 从输入流中获取字符串
	 */
	public static String getString(InputStream is) {
		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error("get String failure", e);
			throw new RuntimeException(e);
		}
		return sb.toString();
	}
}
