package org.cdc.clipboardlogger.logic.eventcreator;

import org.cdc.clipboardlogger.logic.eventcreator.annotation.Delay;

import java.util.LinkedList;
import java.util.List;

public abstract class EventCreator implements Runnable{
    private static List<EventCreator> eventCreators=new LinkedList<>();
    public static List<EventCreator> getEventCreators(){
        return eventCreators;
    }
    public static void createEvent(EventCreator run){
        eventCreators.add(run);
        Thread th=new Thread(run);
        th.start();
    }


    public void run(){
        long delay = 0;
        try {
            delay = this.getClass().getMethod("check").getAnnotation(Delay.class).value();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                Thread.sleep(delay);
                if (check())
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public abstract boolean check() throws Exception;
}
