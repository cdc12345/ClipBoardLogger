package org.cdc.clipboardlogger.logic.listener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.logging.Logger;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;
import org.cdc.clipboardlogger.logic.manager.ClipBoardManager;

/**
 * 事件监听
 */
public class ListListSelectionListener implements ListSelectionListener {
	private static ListListSelectionListener instance;
	private Clipboard clp;
	private ClipBoardLogger clpd;
	public static ListListSelectionListener getInstance(){
		if (instance == null) instance = new ListListSelectionListener();
		return instance;
	}
	private ListListSelectionListener() {
		// TODO Auto-generated constructor stub
		this.clp= Toolkit.getDefaultToolkit().getSystemClipboard();
		this.clpd=ClipBoardLogger.getInstance();
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<String> jList=(JList<String>)e.getSource();
		// TODO Auto-generated method stub
		if (!jList.getSelectedValue().equals(ClipBoardManager.getContent())) {
			Logger.getGlobal().info(clpd.special.toString());
			ClipBoardManager.setContent(clp,clpd.special.get(jList.getSelectedIndex()));
			Logger.getGlobal().info("检测到列表改变事件,当前列表选择为"+"["+jList.getSelectedIndex()+"]"+jList.getSelectedValue());
		}
	}

}
