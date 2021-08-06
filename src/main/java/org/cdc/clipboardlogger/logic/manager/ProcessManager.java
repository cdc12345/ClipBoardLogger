package org.cdc.clipboardlogger.logic.manager;

import sun.jvmstat.monitor.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * ���̹���
 */
public class ProcessManager {
	public static boolean checkExist() throws IOException, URISyntaxException, MonitorException {
		 if (checkFileExist()) return false;
		return !checkProcessExist();
	}
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
		int count=0;
		for(Object process : vmlist) {
			MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
			// ��ȡ����
			String processname = MonitoredVmUtil.mainClass(vm, true);
			if ("org.cdc.clipboardlogger.Main".equals(processname)){
				count+=1;
			}
		}
		return count==1;
	}
}
