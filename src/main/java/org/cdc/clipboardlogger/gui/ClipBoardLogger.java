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
 * UI����
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
	//�������
	public DefaultListModel<String> defaultListModel;
	public JList<String> copy_history;
	public JLabel copied;
	//������������ݶ���
	public HashMap<Integer,Transferable> special = null;

	public ClipBoardLogger() throws Exception {
		instance = this;
		special = new HashMap<>();
		clp = Toolkit.getDefaultToolkit().getSystemClipboard();
	}

	/**
	 * ��ʼ��
	 * @throws Exception һ��
	 */
	public void init() throws Exception {
		clp.setContents(clp.getContents(null),ClipBoardManager.getInstance());
		Image img= getIconImageFromDirectory();
		Logger.getGlobal().info("ͼ���������,��ʼ��������");
		buildWindow(img);
	}
	/**
	 * ��������
	 * @param img ͼ��
	 * @throws Exception һ��
	 */
	private void buildWindow(Image img) throws Exception {
		//ע��������
		trayIcon = new TrayIcon(img);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(TrayActionListener.getInstance());
		SystemTray.getSystemTray().add(trayIcon);

		copy_history = new JList<>();
		defaultListModel = new DefaultListModel<>();
		copy_history.setModel(defaultListModel);
		copy_history.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		copied = new JLabel("��ǰ����:" + getContent());
		JPanel infoJPanel = new JPanel(new FlowLayout());
		infoJPanel.add(copied);


		JScrollPane scrollPane = new JScrollPane(copy_history);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		if (JavaManager.checkClass("org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper")){
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} else {
			JOptionPane.showMessageDialog(null, "�޷���������,�Ѿ��л���ϵͳĬ��");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}

		defaultListModel.addElement(getContent());
		//�¼�����
		addWindowListener(ClipBoardLoggerWindowsAdapter.getInstance());
		copy_history.addListSelectionListener(ListListSelectionListener.getInstance());
		//���ó�ʼ��
		setAlwaysOnTop(true);
		setTitle("���������");
		setLayout(new BorderLayout());
		setResizable(false);
		Dimension dimension= Toolkit.getDefaultToolkit().getScreenSize();
		setSize(250,300);
		setLocation(dimension.width-250, dimension.height/2-300);
		java.util.logging.Logger.getGlobal().info(dimension.width+","+dimension.height/2);
		setIconImage(img);
		//�������
		add(scrollPane, BorderLayout.CENTER);
		add(infoJPanel,BorderLayout.NORTH);

	}

	/**
	 * �õ������ͼ��
	 * @return ͼ�����
	 */
	private Image getIconImageFromDirectory(){
		try {
			Logger.getGlobal().info("����Ѱ���ڲ�ͼ���ļ�");
			return ImageIO.read(ClassLoader.getSystemResource("icon.jpg"));
		} catch (IOException e) {
			Logger.getGlobal().warning("�ڲ�ͼ���ļ���ʧ,��������Ѱ���ⲿͼ���ļ�");
			try {
				return ImageIO.read(new File("icon.jpg"));
			} catch (IOException ex) {
				Logger.getGlobal().warning("��������,ʹ��javaĬ��ͼ��");
			}
		}
		return null;
	}
	/*
	���ھ��Ի�ɾ
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
