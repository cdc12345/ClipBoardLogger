package org.cdc.clipboardlogger.logic.manager;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;

import java.awt.*;
import java.util.logging.Logger;

public class WindowsManager {
    public static int screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    /**
     * ��������
     */
    public static void HideWindows(){
        ClipBoardLogger jf=ClipBoardLogger.getInstance();
        Point p = jf.getLocation();
        int frameWidth = jf.getWidth();
        //����������ߵ���Ļ
        if (p.x+frameWidth<=screenWidth/2){
            jf.setLocation(-frameWidth+10,p.y);
            Logger.getGlobal().info("����������,��ǰ����"+jf.getLocation());
        } else {
            jf.setLocation(screenWidth-10,p.y);
            Logger.getGlobal().info("�������Ұ��,��ǰ����"+jf.getLocation());
        }
    }

    /**
     * ��ʾ
     */
    public static void ShowWindow(){
        ClipBoardLogger jf=ClipBoardLogger.getInstance();
        Point p = jf.getLocation();
        int frameWidth = jf.getWidth();
        if (p.x+frameWidth<=screenWidth/2){
            jf.setLocation(0,p.y);
            Logger.getGlobal().info("����������,��ǰ����"+jf.getLocation());
        } else {
            jf.setLocation(screenWidth-frameWidth,p.y);
            Logger.getGlobal().info("�������Ұ��,��ǰ����"+jf.getLocation());
        }
    }

    /**
     * �Ƿ�������״̬
     * @return ״̬
     */
    public static boolean isHidden(){
        ClipBoardLogger jf=ClipBoardLogger.getInstance();
        Point p = jf.getLocation();
        if (p.x == screenWidth-10 || p.x == -jf.getWidth()+10){
            Logger.getGlobal().info("������������ʾapi,���ؽ��Ϊtrue");
            return true;
        }
        Logger.getGlobal().info("������������ʾapi,���ؽ��Ϊfalse");
        return false;
    }
}
