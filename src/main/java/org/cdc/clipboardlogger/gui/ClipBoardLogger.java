package org.cdc.clipboardlogger.gui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.cdc.clipboardlogger.logic.listener.*;
import org.cdc.clipboardlogger.logic.manager.ClipBoardManager;
import org.cdc.clipboardlogger.logic.manager.JavaManager;
import org.cdc.clipboardlogger.logic.manager.WindowsManager;

/**
 * UI主类
 * @author cdc
 */
public class ClipBoardLogger extends JFrame {
	private static final long serialVersionUID = 1L;

	private static ClipBoardLogger instance;

	public static ClipBoardLogger getInstance(){
		return instance;
	}

	public TrayIcon trayIcon;

	public Clipboard clp;
	//组件变量
	public DefaultListModel<String> defaultListModel;
	public JList<String> copy_history;
	public JLabel copied;
	//储存剪贴板数据对象
	public HashMap<Integer,Transferable> special = null;

	public ClipBoardLogger() throws Exception {
		instance = this;
		special = new HashMap<>();
		clp = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	/**
	 * 初始化
	 * @throws Exception 一堆
	 */
	public void init() throws Exception {
		clp.setContents(clp.getContents(null),ClipBoardManager.getInstance());
		Image img= getIconImageFromDirectory();
		Logger.getGlobal().info("图标设置完毕,开始构建窗口");
		buildWindow(img);
	}
	/**
	 * 构建窗口
	 * @param img 图标
	 * @throws Exception 一堆
	 */
	private void buildWindow(Image img) throws Exception {
		//注册任务栏
		trayIcon = new TrayIcon(img);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(TrayActionListener.getInstance());
		SystemTray.getSystemTray().add(trayIcon);

		copy_history = new JList<>();
		defaultListModel = new DefaultListModel<>();
		copy_history.setModel(defaultListModel);
		copy_history.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		copied = new JLabel("当前剪贴:" + getContent());
		JPanel infoJPanel = new JPanel(new FlowLayout());
		infoJPanel.add(copied);


		JScrollPane scrollPane = new JScrollPane(copy_history);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		if (JavaManager.checkClass("org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper")){
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} else {
			JOptionPane.showMessageDialog(null, "无法加载主题,已经切换至系统默认");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		defaultListModel.addElement(getContent());
		//事件监听
		addWindowListener(ClipBoardLoggerWindowsAdapter.getInstance());
		copy_history.addListSelectionListener(ListListSelectionListener.getInstance());
		//配置初始化
		setAlwaysOnTop(true);
		setTitle("剪贴板监视");
		setLayout(new BorderLayout());
		setResizable(false);
		Dimension dimension= Toolkit.getDefaultToolkit().getScreenSize();
		setSize(250,300);
		setLocation(dimension.width-250, dimension.height/2-300);
		java.util.logging.Logger.getGlobal().info(dimension.width+","+dimension.height/2);
		setIconImage(img);
		//加入组件
		add(scrollPane, BorderLayout.CENTER);
		add(infoJPanel,BorderLayout.NORTH);

	}

	/**
	 * 得到程序的图标
	 * @return 图标对象
	 */
	private Image getIconImageFromDirectory(){
		try {
			Logger.getGlobal().info("正在寻找内部图标文件");
			return ImageIO.read(ClassLoader.getSystemResource("icon.jpg"));
		} catch (IOException e) {
			Logger.getGlobal().warning("内部图标文件丢失,即将尝试寻找外部图标文件");
			try {
				return ImageIO.read(new File("icon.jpg"));
			} catch (IOException ex) {
				Logger.getGlobal().warning("放弃尝试,使用java默认图标");
			}
		}
		return null;
	}
	/*
	后期绝对会删
	 */
	@Deprecated
	public String getContent() {
		return ClipBoardManager.getContent();
	}
	@Deprecated
	public void setContent(Clipboard clp, Transferable data) {
		ClipBoardManager.setContent(clp, data);
	}

}
