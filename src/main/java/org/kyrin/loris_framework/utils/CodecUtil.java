package org.kyrin.loris_framework.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编解码操作工具类
 * @author kyrin
 *
 */
public final class CodecUtil {

	private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 将 URL 编码
	 */
	public static String encodeURL(String source) {
		String target;
		try {
			target = URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("encode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}

	/**
	 * 将 URL解码
	 */
	public static String decodeURL(String source) {
		String target;
		try {
			target = URLDecoder.decode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("decode url failure", e);
			throw new RuntimeException(e);
		}
		return target;
	}
}
