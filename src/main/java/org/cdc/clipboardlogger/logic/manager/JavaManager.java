package org.cdc.clipboardlogger.logic.manager;


import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    public static List<Class<?>> getClasses(String packageName) {

        // ��һ��class��ļ���
        List<Class<?>> classes = new ArrayList<Class<?>>();
        // ��ȡ�������� �������滻
        String packageDirName = packageName.replace('.', '/');
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // ѭ��������ȥ
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                // �õ�Э�������
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    // ��ȡ��������·��
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������
                    classes.addAll(findClassByDirectory(packageName, filePath));
                }
                else if ("jar".equals(protocol)) {
                    classes.addAll(findClassInJar(packageName, url));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * ���ļ�����ʽ����ȡ���µ�����Class
     *
     * @param packageName
     * @param packagePath
     */
    public static List<Class<?>> findClassByDirectory(String packageName, String packagePath) {
        // ��ȡ�˰���Ŀ¼ ����һ��File
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return new ArrayList<>(0);
        }

        File[] dirs = dir.listFiles();
        List<Class<?>> classes = new ArrayList<Class<?>>();
        // ѭ�������ļ�
        for (File file : dirs) {
            // �����Ŀ¼ �����ɨ��
            if (file.isDirectory()) {
                classes.addAll(findClassByDirectory(packageName + "." + file.getName(),
                        file.getAbsolutePath()));
            }
            else if (file.getName().endsWith(".class")) {
                // �����java���ļ���ȥ�������.class ֻ��������
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add(Class.forName(packageName + '.' + className));
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return classes;
    }

    public static List<Class<?>> findClassInJar(String packageName, URL url) {

        List<Class<?>> classes = new ArrayList<Class<?>>();

        String packageDirName = packageName.replace('.', '/');
        // ����һ��JarFile
        JarFile jar;
        try {
            // ��ȡjar
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                // ��ȡjar���һ��ʵ�� ������Ŀ¼ ��һЩjar����������ļ� ��META-INF���ļ�
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                String name = entry.getName();
                if (name.charAt(0) == '/') {
                    // ��ȡ������ַ���
                    name = name.substring(1);
                }

                // ���ǰ�벿�ֺͶ���İ�����ͬ
                if (name.startsWith(packageDirName) && name.endsWith(".class")) {
                    // ȥ�������".class"
                    String className = name.substring(0, name.length() - 6).replace('/', '.');
                    try {
                        // ��ӵ�classes
                        classes.add(Class.forName(className));
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }
}
