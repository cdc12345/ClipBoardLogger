package org.cdc.clipboardlogger.logic.manager;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;

import java.awt.*;
import java.util.logging.Logger;

public class WindowsManager {
    public static int screenWidth=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    public static int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

    /**
     * 贴边隐藏
     */
    public static void HideWindows(){
        ClipBoardLogger jf=ClipBoardLogger.getInstance();
        Point p = jf.getLocation();
        int frameWidth = jf.getWidth();
        //如果是在左半边的屏幕
        if (p.x+frameWidth<=screenWidth/2){
            jf.setLocation(-frameWidth+10,p.y);
            Logger.getGlobal().info("窗口在左半边,当前坐标"+jf.getLocation());
        } else {
            jf.setLocation(screenWidth-10,p.y);
            Logger.getGlobal().info("窗口在右半边,当前坐标"+jf.getLocation());
        }
    }

    /**
     * 显示
     */
    public static void ShowWindow(){
        ClipBoardLogger jf=ClipBoardLogger.getInstance();
        Point p = jf.getLocation();
        int frameWidth = jf.getWidth();
        if (p.x+frameWidth<=screenWidth/2){
            jf.setLocation(0,p.y);
            Logger.getGlobal().info("窗口在左半边,当前坐标"+jf.getLocation());
        } else {
            jf.setLocation(screenWidth-frameWidth,p.y);
            Logger.getGlobal().info("窗口在右半边,当前坐标"+jf.getLocation());
        }
    }

    /**
     * 是否处于隐藏状态
     * @return 状态
     */
    public static boolean isHidden(){
        ClipBoardLogger jf=ClipBoardLogger.getInstance();
        Point p = jf.getLocation();
        if (p.x == screenWidth-10 || p.x == -jf.getWidth()+10){
            Logger.getGlobal().info("调用了隐藏显示api,返回结果为true");
            return true;
        }
        Logger.getGlobal().info("调用了隐藏显示api,返回结果为false");
        return false;
    }
}
