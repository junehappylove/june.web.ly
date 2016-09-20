/**
 * 
 */
package com.june.util;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import com.june.common.MessagePropertyPlaceholderConfiger;

/**
 * 
 * 消息工具类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年9月19日 下午5:48:30
 */
public class MessageUtil {

	private static String formatMessage_(String msgId, String[] messageParam) {
		try {
			String message = getMessage(msgId);
			message = StringUtils.isNotBlank(message) ? message : msgId;
			return setMessageByParam(message, messageParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据消息id获取配置文件中的消息内容
	 * 
	 * @param msgId
	 * @param params
	 * @return
	 */
	public static String resource(String msgId, String... params) {
		if (params != null) {
			return formatMessage_(msgId, params);
		} else {
			return resource(msgId);
		}
	}

	/**
	 * 获取资源文件中的属性
	 * 
	 * @param key
	 * @return: String
	 */
	public static String resource(String key) {
		return getMessage(key);
	}

	// 设定message
	private static String setMessageByParam(String message, String[] messageParam) throws Exception {
		MessageFormat messageFormat = new MessageFormat(message);
		return messageFormat.format(messageParam);
	}

	// 根据id获取配置文件消息
	private static String getMessage(String msgId) {
		return (String) MessagePropertyPlaceholderConfiger.getContextProperty(msgId);
	}
}
