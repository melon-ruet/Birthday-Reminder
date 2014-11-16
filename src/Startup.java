import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.prefs.Preferences;

/**
 * Created by melon on 6/5/14.
 */
public class Startup {

    public static boolean checkSelection = true;

    private Method regOpenKey = null;
    private Method regCloseKey = null;
    private Method regQueryValueEx = null;
    private Method regSetValueEx = null;
    private Method regDeleteValue = null;

    private static final int HKEY_LOCAL_MACHINE = 0x80000002;
    private static final int KEY_ALL_ACCESS = 0xf003f;
    private static final int KEY_READ = 0x20019;
    Preferences systemRoot = Preferences.systemRoot();

    public void linuxStartup(JCheckBox box) throws Exception {
        FileWriter writer = new FileWriter(System.getProperty("user.home") + "/Birthday.desktop");
        PrintWriter printWrite = new PrintWriter(writer);

        writer.append("[Desktop Entry]");
        printWrite.println();
        writer.append("Encoding=UTF-8");
        printWrite.println();
        writer.append("Version=1.0");
        printWrite.println();
        writer.append("Type=Application");
        printWrite.println();
        writer.append("Terminal=false");
        printWrite.println();
        writer.append("Exec=/usr/bin/java -jar \"" +
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " ") + "\"");
        printWrite.println();
        writer.append("Name=Birthday");

        writer.close();

        Process process = Runtime.getRuntime().exec("gksu cp " + System.getProperty("user.home") + "/Birthday.desktop" +
                "  /etc/xdg/autostart/Birthday.desktop");
        process.waitFor();
        if(process.exitValue() == 0){
            GlobalVariables.prefs.putBoolean("LOAD_STARTUP", true);
        }
        else{
            checkSelection = false;
            box.setSelected(false);
        }
    }

    private void RegInitialize() {
        try {
            Class<? extends Preferences> userClass = Preferences.userRoot().getClass();
            regOpenKey = userClass.getDeclaredMethod("WindowsRegOpenKey",
                    new Class[] { int.class, byte[].class, int.class });
            regOpenKey.setAccessible(true);
            regCloseKey = userClass.getDeclaredMethod("WindowsRegCloseKey",
                    new Class[] { int.class });
            regCloseKey.setAccessible(true);
            regQueryValueEx = userClass.getDeclaredMethod("WindowsRegQueryValueEx",
                    new Class[] { int.class, byte[].class });
            regQueryValueEx.setAccessible(true);

            regSetValueEx = userClass.getDeclaredMethod(
                    "WindowsRegSetValueEx", new Class[] { int.class,
                    byte[].class, byte[].class });
            regSetValueEx.setAccessible(true);
            regDeleteValue = userClass.getDeclaredMethod(
                    "WindowsRegDeleteValue", new Class[] { int.class,
                    byte[].class });
            regDeleteValue.setAccessible(true);
        }
        catch (Exception e) { }
    }

    public void writeStringValue (String path, String key, String value)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        RegInitialize();
        int[] handles = (int[]) regOpenKey.invoke(systemRoot, HKEY_LOCAL_MACHINE, toCstr(path), KEY_ALL_ACCESS);

        regSetValueEx.invoke(systemRoot,
                handles[0], toCstr(key), toCstr(value));
        regCloseKey.invoke(systemRoot, handles[0]);
    }

    private byte[] toCstr(String str) {
        byte[] result = new byte[str.length() + 1];

        for (int i = 0; i < str.length(); i++)
            result[i] = (byte) str.charAt(i);

        result[str.length()] = 0;
        return result;
    }

    public String readString(String value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        RegInitialize();
        int[] handles = null;
        try{
            handles = (int[]) regOpenKey.invoke(systemRoot, HKEY_LOCAL_MACHINE,
                    toCstr("SOFTWARE\\Refresh Desk\\Birthday reminder"), KEY_READ);
        }
        catch(NullPointerException nu) {return null;}

        byte[] valb = (byte[]) regQueryValueEx.invoke(systemRoot, handles[0], toCstr(value));
        regCloseKey.invoke(systemRoot, handles[0]);
        return (valb != null ? new String(valb).trim() : null);
    }

    public void deleteValue(String key, String value) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException
    {
        RegInitialize();
        int[] handles = (int[]) regOpenKey.invoke(systemRoot, HKEY_LOCAL_MACHINE, toCstr(key), KEY_ALL_ACCESS);

        regDeleteValue.invoke(systemRoot, handles[0], toCstr(value));
        regCloseKey.invoke(systemRoot, handles[0]);
    }
}
