package org.cdc.clipboardlogger.logic.listener;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;

public class TrayActionListener implements ActionListener {


	private static TrayActionListener instance;
	public static TrayActionListener getInstance(){if (instance == null) instance = new TrayActionListener();return instance;}
	private ClipBoardLogger clpd;
	private TrayActionListener() {
		// TODO Auto-generated constructor stub
		this.clpd=ClipBoardLogger.getInstance();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getGlobal().info("检测到任务栏图标点击事件");
		TrayIcon trayIcon=(TrayIcon)e.getSource();
		// TODO Auto-generated method stub
		if (clpd.isVisible()) {
			SystemTray.getSystemTray().remove(trayIcon);
			System.exit(0);
		} else {
			clpd.setVisible(true);
		}
	}

}
