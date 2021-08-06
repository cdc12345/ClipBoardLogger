package org.cdc.clipboardlogger.logic.eventcreator;

import org.cdc.clipboardlogger.logic.event.CustomMouseEvent;
import org.cdc.clipboardlogger.logic.eventcreator.annotation.Delay;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MouseEventCreator extends EventCreator{
    private static List<CustomMouseEvent> events=new LinkedList<>();
    public static void registerEvent(CustomMouseEvent event){
        events.add(event);
    }
    private int lastX;
    private int lastY;

    @Override
    @Delay(1000)
    public boolean check() {
        Point p = MouseInfo.getPointerInfo().getLocation();
        if (lastX != p.x){
            lastX = p.x;
            for (CustomMouseEvent e : events){
                e.XChanged(p.x);
            }
        }
        if (lastY != p.y){
            lastY = p.y;
            for (CustomMouseEvent e : events){
                e.YChanged(p.y);
            }
        }
        return false;
    }
}
