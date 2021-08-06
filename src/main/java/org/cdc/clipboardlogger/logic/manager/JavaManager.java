package org.cdc.clipboardlogger.logic.manager;

public class JavaManager {

    public static boolean checkClass(String classname){
        try {
            Class.forName(classname);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    public static String getJavaVersion(){
        return System.getProperty("java.version");
    }
}
