package com.june.task;

import java.util.Properties;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Sigar;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.june.entity.ServerInfoFormMap;
import com.june.mapper.ServerInfoMapper;
import com.june.util.Common;
import com.june.util.EmailUtils;
import com.june.util.MessageUtil;
import com.june.util.PropertiesUtils;
import com.june.util.SystemInfo;

/**
 * Spring调度，指定时间执行
 * 利用了spring中使用注解的方式来进行任务调度。 
 * @author june
 * 2015-05-19
 * @Email: wjw.happy.love@163.com
 * @version 3.0v
 */
@Component
@Lazy(false)
public class SpringTaskController {
	@Inject
	private ServerInfoMapper serverInfoMapper;

	private static final Logger logger = Logger.getLogger(SpringTaskController.class);
	
//	public static void main(String[] args) {
//		SpringTaskController action = new SpringTaskController();
//		try {
//			action.task();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 与用户设置的使用率比较 spirng 调度
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "1 * *  * * ? ")
	public void task() throws Exception {
		ServerInfoFormMap usage = SystemInfo.usage(new Sigar());
		String cpuUsage = usage.get("cpuUsage")+"";// CPU使用率
		String serverUsage = usage.get("ramUsage")+"";// 系统内存使用率
		String JvmUsage = usage.get("jvmUsage")+"";// 计算JVM内存使用率
		Properties prop = PropertiesUtils.getProperties();
		String cpu = prop.getProperty("cpu");
		String jvm = prop.getProperty("jvm");
		String ram = prop.getProperty("ram");
		String email = prop.getProperty("toEmail");
		// 当系统消耗内存大于或等于用户设定的内存时，发送邮件
		String cpubool = "";
		String jvmbool = "";
		String rambool = "";
		String mark = "<font color='red'>";
		if (Double.parseDouble(cpuUsage) > Double.parseDouble(cpu)) {
			cpubool = info;
			mark += MessageUtil.resource("warn_cpu",cpuUsage,cpu);//"CPU当前：" + cpuUsage + "%,超出预设值  " + cpu + "%<br>";
		}
		if (Double.parseDouble(JvmUsage) > Double.parseDouble(jvm)) {
			jvmbool = info;
			mark += MessageUtil.resource("warn_jvm",JvmUsage,jvm);//"JVM当前：" + JvmUsage + "%,超出预设值 " + jvm + "%<br>";
		}
		if (Double.parseDouble(serverUsage) > Double.parseDouble(ram)) {
			rambool = info;
			mark += MessageUtil.resource("warn_ram",serverUsage,ram);//"内存当前：" + serverUsage + "%,超出预设值  " + ram + "%";
		}
		mark += "</font>";
		// 邮件内容

		String title = MessageUtil.resource("info_server_pre", Common.fromDateH());
		String css = MessageUtil.resource("info_css_monitor_1");
		css += MessageUtil.resource("info_css_monitor_2");
		css += MessageUtil.resource("info_css_monitor_3");
		css += MessageUtil.resource("info_css_monitor_4");

		String centent = MessageUtil.resource("info_mail_content", Common.fromDateH(), css);

		centent += MessageUtil.resource("info_mail_content_2", cpubool, cpuUsage);
		centent += MessageUtil.resource("info_mail_content_3", cpu, rambool, serverUsage, ram);
		centent += MessageUtil.resource("info_mail_content_4", jvmbool, JvmUsage, jvm);
		
		mark = mark.replaceAll("'","\"");
		if (!Common.isEmpty(cpubool) || !Common.isEmpty(jvmbool) || !Common.isEmpty(rambool)) {
			try {
				EmailUtils.sendMail(prop.getProperty("fromEmail"), email, prop.getProperty("emailName"), prop.getProperty("emailPassword"), title, centent);
				// 保存预警信息
				usage.put("setCpuUsage", cpu);
				usage.put("setJvmUsage", jvm);
				usage.put("setRamUsage", ram);
				usage.put("email", email);
				usage.put("mark", mark);
				serverInfoMapper.addEntity(usage);
				logger.info(MessageUtil.resource("info_mail_send"));
			} catch (Exception e) {
				logger.error(MessageUtil.resource("error_mail_send"));
			}
		}
	}
	
	private static final String info = MessageUtil.resource("info_mail_data_red");
}