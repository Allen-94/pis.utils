package com.yuyisz.pis.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SystemApi {

	// 在Linux操作系统中执行命令，返回执行结果
	public static String exec(String cmd) {
		String str = "error";
		try {
			String[] cmdA = { "/bin/sh", "-c", cmd };
			Process pr = Runtime.getRuntime().exec(cmdA);
			pr.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			str = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	// 获取指定集群服务信息
	public static String findServiceInfo(String clusterName) {
		String cmd = "grep '^"
				+ clusterName
				+ "' /opt/Core_service_password.csv|awk -F ',' '{if($2==\"\") printf $3\",\"$6\",\"$7;else printf $2\",\"$6\",\"$7}'";
		return exec(cmd);
	}

	// 获取系统服务服务信息
	public static String findSystemServiceInfo() {
		String cmd = "grep '^SystemManagement' /opt/Core_service_password.csv|awk -F ',' '{if($2==\"\") printf $3;else printf $2}'";
		return exec(cmd);
	}

	// 获取数据库服务信息
	public static String findDBInfo() {
		String cmd = "grep 'gp' /opt/Core_service_password.csv|awk -F ',' '{if($8==\"gp\") {if($2==\"\") printf $3\",\"$6\",\"$7;else printf $2\",\"$6\",\"$7}}'";
		return exec(cmd);
	}

	// 获取消息中心服务信息
	public static String findMQInfo() {
		String cmd = "grep 'rabbitmq' /opt/Core_service_password.csv|awk -F ',' '{if($8==\"rabbitmq\") {if($2==\"\") printf $3\",\"$6\",\"$7;else printf $2\",\"$6\",\"$7}}'";
		return exec(cmd);
	}

	// 获取HDFS服务信息
	public static String findHDFSInfo() {
		String cmd = "grep 'hadoop' /opt/Core_service_password.csv|awk -F ',' '{if($8==\"hadoop\") {if($2==\"\") printf $3\",\"$6\",\"$7;else printf $2\",\"$6\",\"$7}}'";
		return exec(cmd);
	}

	// 获取FTP服务信息
	public static String findFTPInfo() {
		String cmd = "a=($(grep 'ftp' /opt/Core_service_password.csv|awk -F ',' '{if($8==\"ftp\") {if($2==\"\") print $1\",\"$3\",\"$6\",\"$7;else print $1\",\"$2\",\"$6\",\"$7}}'));for((i=0;i<${#a[@]};i++));do echo -n [${a[$i]}];((b=${#a[@]}-1));[ ${i} -ne ${b} ] && echo -n ',';done";
		return exec(cmd);
	}

	// 获取NTP服务信息
	public static String findNTPInfo() {
		String cmd = "grep 'ntp' /opt/Core_service_password.csv|awk -F ',' '{if($8==\"ntp\") {if($2==\"\") printf $3;else printf $2}}'";
		return exec(cmd);
	}

	// 获取流媒体服务信息
	public static String findStreamInfo() {
		String cmd = "a=($(grep 'stream' /opt/Core_service_password.csv|awk -F ',' '{if($8==\"stream\") {if($2==\"\") print $1\",\"$3\",\"$6\",\"$7;else print $1\",\"$2\",\"$6\",\"$7}}'));for((i=0;i<${#a[@]};i++));do echo -n [${a[$i]}];((b=${#a[@]}-1));[ ${i} -ne ${b} ] && echo -n ',';done";
		return exec(cmd);
	}

	// 流媒体服务获取本身标识；仅在流媒体服务上调接口才能使用;在流媒体服务虚拟机中的/opt/service中有一个flag文件，里面有当前流媒体服务的标识信息
	public static String findStreamFlag() {
		String cmd = "cat /opt/service/flag";
		return exec(cmd);
	}

	// 获取所有服务的信息
	public static String findServiceInfo() {
		// String
		// cmd="a=($(cat /opt/Core_service_password.csv|awk -F ',' '{if($2==\"\") printf $1\",\"$3\",\"$6\",\"$7;else printf $1\",\"$2\",\"$6\",\"$7}'));for((i=0;i<${#a[@]};i++));do echo -n [${a[$i]}];((b=${#a[@]}-1));[ ${i} -ne ${b} ] && echo -n ',';done";
		// return exec(cmd);
		String str = "{greenplum:" + findDBInfo().trim() + "},{RabbitMQ:"
				+ findMQInfo().trim() + "},{NTP:" + findNTPInfo().trim()
				+ "},{FTP:" + findFTPInfo().trim() + "},{Stream:"
				+ findStreamInfo().trim() + "}";
		return str;
	}

}
