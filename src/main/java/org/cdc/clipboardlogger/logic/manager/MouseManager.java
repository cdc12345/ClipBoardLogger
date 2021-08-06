package org.cdc.clipboardlogger.logic.manager;

import org.cdc.clipboardlogger.gui.ClipBoardLogger;

import java.awt.*;
import java.util.logging.Logger;

public class MouseManager {
    public static int getMouseX(){
        return MouseInfo.getPointerInfo().getLocation().x;
    }
    public static int getMouseY(){
        return MouseInfo.getPointerInfo().getLocation().y;
    }
    public static boolean isMouseInTheWindowArea() {
        ClipBoardLogger clpd = ClipBoardLogger.getInstance();
        Logger.getGlobal().info("ЪѓБъ"+MouseInfo.getPointerInfo().getLocation());
        if (clpd.getLocation().x <= getMouseX() && getMouseX() <= clpd.getLocation().x + clpd.getWidth()) {
            return clpd.getLocation().y <= getMouseY() && getMouseY() <= clpd.getLocation().y + clpd.getHeight();
        }
        return false;
    }
}
