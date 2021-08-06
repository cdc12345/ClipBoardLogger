package org.cdc.clipboardlogger.logic.listener;

import org.cdc.clipboardlogger.logic.event.CustomMouseEvent;
import org.cdc.clipboardlogger.logic.manager.MouseManager;
import org.cdc.clipboardlogger.logic.manager.WindowsManager;

import java.util.logging.Logger;

public class ClipBoardLoggerMouseMotionListener implements CustomMouseEvent {
    private static ClipBoardLoggerMouseMotionListener instance;
    public static ClipBoardLoggerMouseMotionListener getInstance(){if (instance==null) instance = new ClipBoardLoggerMouseMotionListener(); return instance;}

    private ClipBoardLoggerMouseMotionListener(){}
    int screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;


    @Override
    public void XChanged(int x) {
        Logger.getGlobal().info(" Û±Í“∆∂Øº‡Ã˝");
        if (MouseManager.isMouseInTheWindowArea()){
            WindowsManager.ShowWindow();
        } else WindowsManager.HideWindow();
    }

    @Override
    public void YChanged(int y) {
        Logger.getGlobal().info(" Û±Í“∆∂Øº‡Ã˝");
        if (MouseManager.isMouseInTheWindowArea()){
            WindowsManager.ShowWindow();
        } else WindowsManager.HideWindow();
    }
}
