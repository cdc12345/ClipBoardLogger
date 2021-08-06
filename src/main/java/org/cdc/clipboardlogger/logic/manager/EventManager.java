package org.cdc.clipboardlogger.logic.manager;

import com.sun.tools.corba.se.idl.constExpr.Equal;
import org.cdc.clipboardlogger.logic.eventcreator.EventCreator;
import org.cdc.clipboardlogger.logic.eventcreator.MouseEventCreator;
import org.cdc.clipboardlogger.logic.listener.ClipBoardLoggerMouseMotionListener;

import java.util.logging.Logger;

public class EventManager {
    public static void EventRun() throws InstantiationException, IllegalAccessException {
        //�¼�ע��
        for (Class<?> classInstance:JavaManager.getClasses("org.cdc.clipboardlogger.logic.eventcreator")){
            if (classInstance.isInterface()||classInstance.isAnnotation()||"org.cdc.clipboardlogger.logic.eventcreator.EventCreator".equals(classInstance.getName())) continue;
            EventCreator.createEvent((EventCreator) classInstance.newInstance());
            Logger.getGlobal().info("ע���¼�"+classInstance.getSimpleName().replace("Creator",""));
        }
        //ע���¼�
        MouseEventCreator.registerEvent(ClipBoardLoggerMouseMotionListener.getInstance());
    }
}
