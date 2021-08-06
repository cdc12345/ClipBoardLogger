package org.cdc.clipboardlogger.logic.manager;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;

/**
 * 剪贴板管理
 */
public class ClipBoardManager implements ClipboardOwner {

	private static ClipBoardManager instance;
	private static String content;
	public static ClipBoardManager getInstance() {
		if (instance == null) instance = new ClipBoardManager();
		return instance;
	}
	public static String getContent(){
		return content;
	}

	/**
	 * 仅供监听者使用
	 * @param clp 对象
	 * @return 剪贴板数据对象字符串
	 */
	private static String getContentFromSystem(Clipboard clp) {
		ClipBoardLogger clpd = ClipBoardLogger.getInstance();
		Transferable content = clp.getContents(null);
		clpd.special.put(clpd.defaultListModel.getSize(),content);
		if (content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			String contentString;
			try {
				contentString = (String) content.getTransferData(DataFlavor.stringFlavor);
				ClipBoardManager.content = contentString;
				return contentString;
			} catch (UnsupportedFlavorException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (content.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			try {
				List<File> fl = (List<File>) content.getTransferData(DataFlavor.javaFileListFlavor);
				ClipBoardManager.content = fl.toString();
				return fl.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  else if (content.isDataFlavorSupported(DataFlavor.imageFlavor)){
			try {
				Image image= (Image) content.getTransferData(DataFlavor.imageFlavor);
				ClipBoardManager.content = image.toString();
				return image.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.isDataFlavorSupported(DataFlavor.allHtmlFlavor)){
			try {
				String str= (String) content.getTransferData(DataFlavor.allHtmlFlavor);
				ClipBoardManager.content = str;
				return str;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void setContent(Clipboard clp, Transferable data) {
		clp.setContents(data,getInstance());
		getInstance().lostOwnership(clp,data);
		ClipBoardLogger clpd = ClipBoardLogger.getInstance();
		clpd.copied.setText("当前剪贴:"+ClipBoardManager.getContent());
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		ClipBoardLogger clp = ClipBoardLogger.getInstance();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clipboard.setContents(clipboard.getContents(null), this);
		String content = getContentFromSystem(clipboard);
		clp.defaultListModel.addElement(content);
		clp.copied.setText("当前剪贴:"+content);
		java.util.logging.Logger.getGlobal().info("监听到剪贴板变化,剪贴内容:"+getContent());
	}
}
