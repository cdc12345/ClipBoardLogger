package org.cdc.clipboardlogger;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;
import org.cdc.clipboardlogger.logic.eventcreator.EventCreator;
import org.cdc.clipboardlogger.logic.eventcreator.MouseEventCreator;
import org.cdc.clipboardlogger.logic.listener.ClipBoardLoggerMouseMotionListener;
import org.cdc.clipboardlogger.logic.manager.EventManager;
import org.cdc.clipboardlogger.logic.manager.ProcessManager;
import org.cdc.clipboardlogger.logic.manager.WindowsManager;
import sun.jvmstat.monitor.MonitorException;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * 入口类
 * @author cdc
 */
public class Main {
    public static void main(String[] args) {
        Logger.getGlobal().info("程序正在初始化");
        try {
            ProcessManager.processCheck();
            WindowsManager.buildWindow();
            EventManager.EventRun();
            Logger.getGlobal().info("初始化完毕,正在关闭初始化进程");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null,e.fillInStackTrace().toString(),"报错",JOptionPane.WARNING_MESSAGE);
            Logger.getGlobal().warning(e.fillInStackTrace().toString());
        }
    }
}
