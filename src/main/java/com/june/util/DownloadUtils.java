package com.june.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 下載工具类 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年9月19日 下午5:40:35
 */
public class DownloadUtils {
	private static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);

	/**
	 * 下载
	 * @param fileName 
	 * @param filepath
	 * @param request
	 * @param response
	 * @throws Exception
	 * @date 2016年9月19日 下午5:41:18
	 * @writer iscas
	 */
	public static void download(String fileName, String filepath, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext().getRealPath("/") + filepath;
		try {
			File file = new File(ctxPath);
			if (!file.exists())
				logger.warn("download agent is error ! messages --->> " + ctxPath + " is not exists !");
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			bis = new BufferedInputStream(new FileInputStream(ctxPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			logger.warn("download agent is error ! messages --->> " + e.fillInStackTrace());
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
	}
}
