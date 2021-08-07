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
 * ���̹���
 */
public class ProcessManager {
	public static void processCheck() throws IOException, URISyntaxException, MonitorException {
		Logger.getGlobal().info("���ڼ�Ȿ�����Ƿ��Ѿ����ڽ���");
		if (ProcessManager.checkExist()) {
			JOptionPane.showMessageDialog(null, "�����Ѿ����У��޷��࿪");
			Logger.getGlobal().warning("�����Ѿ����ڽ���,�˳�����");
			System.exit(0);
		}
		Logger.getGlobal().info("������,ȷ������");
	}
	public static boolean checkExist() throws URISyntaxException, MonitorException {
			return !checkProcessExist();
	}

	/**
	 * �����ļ��ģʽ
	 * @return �Ƿ���ڽ���
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
			JOptionPane.showMessageDialog(null, "�޷����ý��̼���");
			Logger.getGlobal().warning("�޷����н��̼��");
			return false;
		}
		// ��ȡ�������
		MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
		// ȡ�������ڻ�����������
		Set<?> vmlist = new HashSet<Object>(local.activeVms());
		Logger.getGlobal().info("������jvm����"+vmlist);
		MonitoredVm currentVm = local.getMonitoredVm(new VmIdentifier("//" + getProcessID()));
		String currentProcessName = MonitoredVmUtil.mainClass(currentVm, true);
		int count=0;
		for(Object process : vmlist) {
			MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
			// ��ȡ����
			String processName = MonitoredVmUtil.mainClass(vm, true);
			Logger.getGlobal().info(processName);
			if (currentProcessName.equals(processName)){
				count+=1;
				Logger.getGlobal().info("����������:"+processName);
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
