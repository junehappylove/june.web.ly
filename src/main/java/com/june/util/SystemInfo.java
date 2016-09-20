package com.june.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Swap;

import com.june.entity.ServerInfoFormMap;

/**
 * 
 * 系统信息工具类 <br>
 * 使用 的jar包获取相关系统信息
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年9月19日 下午5:54:16
 */
public class SystemInfo {
	private static final Logger logger = Logger.getLogger(SystemInfo.class);
	
	/**
	 * 获取系统基本信息，如ip地址、系统名称、架构、版本、临时路径等信息
	 * 
	 * @return
	 * @date 2016年9月19日 下午5:55:15
	 * @writer iscas
	 */
	public static ServerInfoFormMap SystemProperty() {
		ServerInfoFormMap monitorMap = new ServerInfoFormMap();
		Runtime r = Runtime.getRuntime();
		Properties props = System.getProperties();
		Map<String, String> map = System.getenv();//
		InetAddress addr = null;
		String ip = "";
		String hostName = "";
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			ip = MessageUtil.resource("info_ip_no_addr");
			hostName = MessageUtil.resource("info_ip_no_name");
		}
		if (null != addr) {
			try {
				ip = addr.getHostAddress();
			} catch (Exception e) {
				ip = MessageUtil.resource("info_ip_no_addr");
			}
			try {
				hostName = addr.getHostName();
			} catch (Exception e) {
				hostName = MessageUtil.resource("info_ip_no_name");
			}
		}
		String userName = map.get("USERNAME");// 获取用户名
		String computerName = map.get("COMPUTERNAME");// 获取计算机名
		String userDomain = map.get("USERDOMAIN");// 获取计算机域名

		monitorMap.put("userName", userName);// 用户名
		monitorMap.put("computerName", computerName);// 计算机名
		monitorMap.put("userDomain", userDomain);// 计算机域名
		monitorMap.put("hostIp", ip);// 本地ip地址
		monitorMap.put("hostName", hostName);// 本地主机名
		monitorMap.put("osName", props.getProperty("os.name"));// 操作系统的名称
		monitorMap.put("arch", props.getProperty("os.arch"));// 操作系统的构架
		monitorMap.put("osVersion", props.getProperty("os.version"));// 操作系统的版本
		monitorMap.put("processors", r.availableProcessors());// JVM可以使用的处理器个数
		monitorMap.put("javaVersion", props.getProperty("java.version"));// Java的运行环境版本
		monitorMap.put("vendor", props.getProperty("java.vendor"));// Java的运行环境供应商
		monitorMap.put("javaUrl", props.getProperty("java.vendor.url"));// Java供应商的URL
		monitorMap.put("javaHome", props.getProperty("java.home"));// Java的安装路径
		monitorMap.put("tmpdir", props.getProperty("java.io.tmpdir"));// 默认的临时文件路径
		monitorMap.put("totalMemory", r.totalMemory());// JVM可以使用的总内存
		monitorMap.put("freeMemory", r.freeMemory());// JVM可以使用的剩余内存
		monitorMap.put("fileSep", props.getProperty("file.separator"));// 文件分隔符
		monitorMap.put("pahtSep", props.getProperty("path.separator"));// 路径分隔符
		monitorMap.put("lineSep", props.getProperty("line.separator"));// 行分隔符
		monitorMap.put("userName", props.getProperty("user.name"));// 用户的账户名称
		monitorMap.put("userHome", props.getProperty("user.home"));// 用户的主目录
		monitorMap.put("userDir", props.getProperty("user.dir"));// 用户的当前工作目录
		return monitorMap;
	}

	/**
	 * 获取系统内存相关信息
	 * 
	 * @param sigar
	 * @return
	 * @date 2016年9月19日 下午5:56:42
	 * @writer iscas
	 */
	public static ServerInfoFormMap memory(Sigar sigar) {
		ServerInfoFormMap monitorMap = new ServerInfoFormMap();
		try {
			Runtime r = Runtime.getRuntime();
			monitorMap.put("jvmTotal", Common.div(r.totalMemory(), (1024 * 1024), 2) + "M");// java总内存
			monitorMap.put("jvmUse", Common.div(r.totalMemory() - r.freeMemory(), (1024 * 1024), 2) + "M");// JVM使用内存
			monitorMap.put("jvmFree", Common.div(r.freeMemory(), (1024 * 1024), 2) + "M");// JVM剩余内存
			monitorMap.put("jvmUsage", Common.div(r.totalMemory() - r.freeMemory(), r.totalMemory(), 2));// JVM使用率

			Mem mem = sigar.getMem();
			// 内存总量
			monitorMap.put("ramTotal", Common.div(mem.getTotal(), (1024 * 1024 * 1024), 2) + "G");// 内存总量
			monitorMap.put("ramUse", Common.div(mem.getUsed(), (1024 * 1024 * 1024), 2) + "G");// 当前内存使用量
			monitorMap.put("ramFree", Common.div(mem.getFree(), (1024 * 1024 * 1024), 2) + "G");// 当前内存剩余量
			monitorMap.put("ramUsage", Common.div(mem.getUsed(), mem.getTotal(), 2));// 内存使用率

			Swap swap = sigar.getSwap();
			// 交换区总量
			monitorMap.put("swapTotal", Common.div(swap.getTotal(), (1024 * 1024 * 1024), 2) + "G");
			// 当前交换区使用量
			monitorMap.put("swapUse", Common.div(swap.getUsed(), (1024 * 1024 * 1024), 2) + "G");
			// 当前交换区剩余量
			monitorMap.put("swapFree", Common.div(swap.getFree(), (1024 * 1024 * 1024), 2) + "G");
			monitorMap.put("swapUsage", Common.div(swap.getUsed(), swap.getTotal(), 2));//

		} catch (Exception e) {
		}
		return monitorMap;
	}

	/**
	 * 获取系统jvm、内存、cpu的使用率信息
	 * 
	 * @param sigar
	 * @return
	 * @date 2016年9月19日 下午5:57:12
	 * @writer iscas
	 */
	public static ServerInfoFormMap usage(Sigar sigar) {
		ServerInfoFormMap monitorMap = new ServerInfoFormMap();
		try {
			Runtime r = Runtime.getRuntime();
			monitorMap.put("jvmUsage",
					Math.round(Common.div(r.totalMemory() - r.freeMemory(), r.totalMemory(), 2) * 100));// JVM使用率

			Mem mem = sigar.getMem();
			// 内存总量
			monitorMap.put("ramUsage", Math.round(Common.div(mem.getUsed(), mem.getTotal(), 2) * 100));// 内存使用率

			List<ServerInfoFormMap> cpu = cpuInfos(sigar);
			double b = 0.0;
			for (ServerInfoFormMap m : cpu) {
				b += Double.valueOf(m.get("cpuTotal") + "");
			}
			monitorMap.put("cpuUsage", Math.round(b / cpu.size()));// cpu使用率
		} catch (Exception e) {
		}
		return monitorMap;
	}

	/**
	 * 获取系统cup的相关信息
	 * 
	 * @param sigar
	 * @return
	 * @date 2016年9月19日 下午5:57:43
	 * @writer iscas
	 */
	public static List<ServerInfoFormMap> cpuInfos(Sigar sigar) {
		List<ServerInfoFormMap> monitorMaps = new ArrayList<ServerInfoFormMap>();
		try {
			CpuPerc cpuList[] = sigar.getCpuPercList();
			for (CpuPerc cpuPerc : cpuList) {
				ServerInfoFormMap monitorMap = new ServerInfoFormMap();
				monitorMap.put("cpuUserUse", Math.round(cpuPerc.getUser() * 100));// 用户使用率
				monitorMap.put("cpuSysUse", Math.round(cpuPerc.getSys() * 100));// 系统使用率
				monitorMap.put("cpuWait", Math.round(cpuPerc.getWait() * 100));// 当前等待率
				monitorMap.put("cpuFree", Math.round(cpuPerc.getIdle() * 100));// 当前空闲率
				monitorMap.put("cpuTotal", Math.round(cpuPerc.getCombined() * 100));// 总的使用率
				monitorMaps.add(monitorMap);
			}
		} catch (Exception e) {
		}
		return monitorMaps;
	}

	public static List<ServerInfoFormMap> netInfos(Sigar sigar) {
		List<ServerInfoFormMap> monitorMaps = new ArrayList<ServerInfoFormMap>();
		try {
			String[] ifNames = sigar.getNetInterfaceList();
			for (String name : ifNames) {
				ServerInfoFormMap monitorMap = new ServerInfoFormMap();
				NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
				monitorMap.put("name", name);// 网络设备名
				monitorMap.put("address", ifconfig.getAddress());// IP地址
				monitorMap.put("netmask", ifconfig.getNetmask());// 子网掩码
				if ((ifconfig.getFlags() & 1L) <= 0L) {
					// System.out.println("!IFF_UP...skippinggetNetInterfaceStat");
					continue;
				}
				NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
				monitorMap.put("getRxPackets", ifstat.getRxPackets());// 接收的总包裹数
				monitorMap.put("getTxPackets", ifstat.getTxPackets());// 发送的总包裹数
				monitorMap.put("getRxBytes", ifstat.getRxBytes());// 接收到的总字节数
				monitorMap.put("getTxBytes", ifstat.getTxBytes());// 发送的总字节数
				monitorMap.put("getRxErrors", ifstat.getRxErrors());// 接收到的错误包数
				monitorMap.put("getTxErrors", ifstat.getTxErrors());// 发送数据包时的错误数
				monitorMap.put("getRxDropped", ifstat.getRxDropped());// 接收时丢弃的包数
				monitorMap.put("getTxDropped", ifstat.getTxDropped());// 发送时丢弃的包数
				monitorMaps.add(monitorMap);
			}
		} catch (Exception e) {
			logger.debug("net info error");
		}
		return monitorMaps;
	}

	/**
	 * 获取文件系统类型名，比如本地硬盘、光驱、网络文件系统等
	 * 
	 * @param sigar
	 * @return
	 * @throws Exception
	 * @date 2016年9月19日 下午5:58:01
	 * @writer iscas
	 */
	public List<ServerInfoFormMap> diskInfos(Sigar sigar) throws Exception {
		List<ServerInfoFormMap> monitorMaps = new ArrayList<ServerInfoFormMap>();
		FileSystem fslist[] = sigar.getFileSystemList();
		for (int i = 0; i < fslist.length; i++) {
			ServerInfoFormMap monitorMap = new ServerInfoFormMap();
			FileSystem fs = fslist[i];
			// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
			FileSystemUsage usage = null;
			usage = sigar.getFileSystemUsage(fs.getDirName());
			switch (fs.getType()) {
			case 0: // TYPE_UNKNOWN ：未知
				break;
			case 1: // TYPE_NONE
				break;
			case 2: // TYPE_LOCAL_DISK : 本地硬盘
				monitorMap.put("diskName", fs.getDevName());// 系统盘名称
				monitorMap.put("diskType", fs.getSysTypeName());// 盘类型
				// 文件系统总大小
				monitorMap.put("diskTotal", fs.getSysTypeName());
				// 文件系统剩余大小
				monitorMap.put("diskFree", usage.getFree());
				// 文件系统已经使用量
				monitorMap.put("diskUse", usage.getUsed());
				double usePercent = usage.getUsePercent() * 100D;
				// 文件系统资源的利用率
				monitorMap.put("diskUsage", usePercent);// 内存使用率
				monitorMaps.add(monitorMap);
				break;
			case 3:// TYPE_NETWORK ：网络

				break;
			case 4:// TYPE_RAM_DISK ：闪存

				break;
			case 5:// TYPE_CDROM ：光驱

				break;
			case 6:// TYPE_SWAP ：页面交换
				monitorMap.put("swapRead", usage.getDiskReads());
				monitorMap.put("swapWrite", usage.getDiskWrites());
				monitorMaps.add(monitorMap);
				break;
			}
		}
		return monitorMaps;
	}

}
