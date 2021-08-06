package org.cdc.clipboardlogger;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;
import org.cdc.clipboardlogger.logic.eventcreator.EventCreator;
import org.cdc.clipboardlogger.logic.eventcreator.MouseEventCreator;
import org.cdc.clipboardlogger.logic.listener.ClipBoardLoggerMouseMotionListener;
import org.cdc.clipboardlogger.logic.manager.ProcessManager;
import org.cdc.clipboardlogger.logic.manager.WindowsManager;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * �����
 * @author cdc
 */
public class Main {
    public static void main(String[] args) {
        Logger.getGlobal().info("�������ڳ�ʼ��");
        try {
            Logger.getGlobal().info("���ڼ�Ȿ�����Ƿ��Ѿ����ڽ���");
            if (ProcessManager.checkExist()) {
                JOptionPane.showMessageDialog(null, "�����Ѿ����У��޷��࿪");
                Logger.getGlobal().warning("�����Ѿ����ڽ���,�˳�����");
                System.exit(0);
            }
            Logger.getGlobal().info("������");
            WindowsManager.buildWindow();
            EventRun();
            Logger.getGlobal().info("��ʼ�����,���ڹرճ�ʼ������");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null,e.fillInStackTrace().toString(),"����",JOptionPane.WARNING_MESSAGE);
            Logger.getGlobal().warning(e.fillInStackTrace().toString());
        }
    }
    private static void EventRun(){
        //�¼�ע��
        EventCreator.createEvent(new MouseEventCreator());
        //ע���¼�
        MouseEventCreator.registerEvent(ClipBoardLoggerMouseMotionListener.getInstance());
    }
}
