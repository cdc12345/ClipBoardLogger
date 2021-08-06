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
 * 入口类
 * @author cdc
 */
public class Main {
    public static void main(String[] args) {
        Logger.getGlobal().info("程序正在初始化");
        try {
            Logger.getGlobal().info("正在检测本程序是否已经存在进程");
            if (ProcessManager.checkExist()) {
                JOptionPane.showMessageDialog(null, "程序已经运行，无法多开");
                Logger.getGlobal().warning("程序已经存在进程,退出程序");
                System.exit(0);
            }
            Logger.getGlobal().info("检测完毕");
            WindowsManager.buildWindow();
            EventRun();
            Logger.getGlobal().info("初始化完毕,正在关闭初始化进程");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null,e.fillInStackTrace().toString(),"报错",JOptionPane.WARNING_MESSAGE);
            Logger.getGlobal().warning(e.fillInStackTrace().toString());
        }
    }
    private static void EventRun(){
        //事件注册
        EventCreator.createEvent(new MouseEventCreator());
        //注册事件
        MouseEventCreator.registerEvent(ClipBoardLoggerMouseMotionListener.getInstance());
    }
}
