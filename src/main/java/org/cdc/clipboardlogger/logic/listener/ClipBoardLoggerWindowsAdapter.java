package org.cdc.clipboardlogger.logic.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.cdc.clipboardlogger.gui.ClipBoardLogger;

public class ClipBoardLoggerWindowsAdapter extends WindowAdapter {
    private static ClipBoardLoggerWindowsAdapter instance;
    private ClipBoardLogger clpd=ClipBoardLogger.getInstance();
    public static ClipBoardLoggerWindowsAdapter getInstance() {
        if (instance == null) instance = new ClipBoardLoggerWindowsAdapter();
         return instance;
    }

    private ClipBoardLoggerWindowsAdapter() {}

    public void windowIconified(WindowEvent e) {
        this.clpd.setVisible(false);
    }

    public void windowClosing(WindowEvent e) {
        int option = JOptionPane.showConfirmDialog(this.clpd, "你想要关闭本程序吗？", "关闭询问", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        switch(option) {
            case 0:
                Logger.getGlobal().info("程序进程正在关闭");
                System.exit(0);
                break;
            case 1:
                this.clpd.setVisible(false);
        }

    }
}
