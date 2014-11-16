import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Created by melon on 6/1/14.
 */
public class GlobalVariables {

    public static Connection connection = null;
    public static Statement SqlStatement = null;
    public static Vector<Vector<String>> DatabaseImport;

    public String strmon[] = {"January","February",
            "March","April","May","June","July","August","September","October","November","December"};
    private String cday[] = {"1","2","3","4","5",
            "6","7","8","9","10","11","12","13","14","15","16","17","18","19",
            "20","21","22","23","24","25","26","27","28","29","30","31"};

    public Vector<String> daysList;

    public Vector<String> setDaysList(int remove) {
        daysList = new Vector<String>();
        daysList.addAll(Arrays.asList(cday));
        while (remove-- > 0)
            daysList.remove(daysList.size()-1);
        return daysList;
    }

    private Class<? extends Preferences> userClass = Preferences.userRoot().getClass();
    public static Preferences prefs = Preferences.userRoot();
}
