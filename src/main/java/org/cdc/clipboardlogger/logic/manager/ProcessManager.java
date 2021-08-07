package org.cdc.clipboardlogger.logic.manager;

import sun.jvmstat.monitor.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * 进程管理
 */
public class ProcessManager {
	public static void processCheck() throws IOException, URISyntaxException, MonitorException {
		Logger.getGlobal().info("正在检测本程序是否已经存在进程");
		if (ProcessManager.checkExist()) {
			JOptionPane.showMessageDialog(null, "程序已经运行，无法多开");
			Logger.getGlobal().warning("程序已经存在进程,退出程序");
			System.exit(0);
		}
		Logger.getGlobal().info("检测完毕,确定运行");
	}
	public static boolean checkExist() throws URISyntaxException, MonitorException {
			return !checkProcessExist();
	}

	/**
	 * 以往的检测模式
	 * @return 是否存在进程
	 * @throws IOException
	 */
	@Deprecated
	private static boolean checkFileExist() throws IOException {
		File run=new File("run");
		if (run.exists()) return false;
		run.createNewFile();
		run.deleteOnExit();
		return true;
	}
	private static boolean checkProcessExist() throws URISyntaxException, MonitorException {
		if (!JavaManager.checkClass("sun.jvmstat.monitor.MonitoredHost")){
			JOptionPane.showMessageDialog(null, "无法启用进程监视");
			Logger.getGlobal().warning("无法进行进程检查");
			return false;
		}
		// 获取监控主机
		MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
		// 取得所有在活动的虚拟机集合
		Set<?> vmlist = new HashSet<Object>(local.activeVms());
		Logger.getGlobal().info("监听到jvm主机"+vmlist);
		MonitoredVm currentVm = local.getMonitoredVm(new VmIdentifier("//" + getProcessID()));
		String currentProcessName = MonitoredVmUtil.mainClass(currentVm, true);
		int count=0;
		for(Object process : vmlist) {
			MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
			// 获取类名
			String processName = MonitoredVmUtil.mainClass(vm, true);
			Logger.getGlobal().info(processName);
			if (currentProcessName.equals(processName)){
				count+=1;
				Logger.getGlobal().info("监听到进程:"+processName);
			}
		}
		return count<=1;
	}
	public static int getProcessID() {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		System.out.println(runtimeMXBean.getName());
		return Integer.valueOf(runtimeMXBean.getName().split("@")[0])
				;
	}
}
