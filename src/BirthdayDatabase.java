import javax.swing.*;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * Created by melon on 6/2/14.
 */
public class BirthdayDatabase {

    private Connection connection;
    private Statement SqlStatement;

    public void DatabaseCreate() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        } catch (Exception ex) {
        }


        try {
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
        } catch (SQLException ex) {
            try {
                connection = DriverManager.getConnection("jdbc:derby:Birthday Database;create=true");
                SqlStatement = connection.createStatement();
                SqlStatement.execute("create table BirthDayTable(day int, month int, "
                        + "name varchar(40), photo_link varchar(300))");
            } catch (SQLException ext) {
            }
        }
    }

    public ResultSet getTodaysBirthday() {
        DatabaseCreate();
        try {
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
            SqlStatement = connection.createStatement();

            Calendar now = GregorianCalendar.getInstance();
            return SqlStatement.executeQuery("SELECT name,photo_link FROM BirthDayTable where month="
                    + now.get(Calendar.MONTH) + " and day=" + (now.get(Calendar.DATE) - 1));
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean[] getBirthdayBymonth(int month, String search) {
        DatabaseCreate();
        ResultSet resultSet;
        boolean ListOfResult[] = new boolean[32];
        try {
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
            SqlStatement = connection.createStatement();

            if(month < 12)
            resultSet = SqlStatement.executeQuery("SELECT * FROM BirthDayTable where month=" +
                    month + " and LOWER(name) like '%"+search+"%' order by day");
            else
            resultSet = SqlStatement.executeQuery("SELECT * FROM BirthDayTable order by month,day");

            GlobalVariables.DatabaseImport.removeAllElements();
            while (resultSet.next()) {
                Vector<String> getData = new Vector<String>();
                getData.add(String.valueOf(false));
                getData.add(resultSet.getString("name"));
                getData.add(String.valueOf(resultSet.getInt("day") + 1) + " " +
                        new GlobalVariables().strmon[resultSet.getInt("month")]);
                GlobalVariables.DatabaseImport.add(getData);
                if(month < 12)
                    ListOfResult[resultSet.getInt("day")] = true;
            }
        } catch (SQLException ex) {
        }
        return ListOfResult;
    }

    public String getDataBySearch(String name, int day, int month) {
        DatabaseCreate();
        String photoPath = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
            SqlStatement = connection.createStatement();
            ResultSet searchResult = SqlStatement.executeQuery("SELECT * from BirthDayTable where"
                    + " name='" + name + "'"
                    + " and day=" + day
                    + " and month=" + month);
            while (searchResult.next()) {
                photoPath = searchResult.getString("photo_link");
                break;
            }
        } catch (SQLException ex) {
        }
        return photoPath;
    }

    public boolean InsertData(String name, int day, int month, String imagePath,
                              boolean state,String prevName, int prevDay, int prevMonth) {
        if ("".equals(name) || name.charAt(0) == ' ') {
            JOptionPane.showMessageDialog(null, "Name can not Empty or First letter can not a space!!!",
                    "Entry Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            boolean checkQuery = false;
            if (getDataBySearch(name, day, month) != null)
                checkQuery = true;

            if (checkQuery) {
                JOptionPane.showMessageDialog(null, String.format("%s", "Already Exist!!!"));
                return false;
            }
            DatabaseCreate();
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
            SqlStatement = connection.createStatement();

            if(state)
                SqlStatement.execute("INSERT INTO BirthDayTable values(" +
                    day + "," +
                    month + ",'" +
                    name + "','" + imagePath + "')");
            else{
                SqlStatement.execute("UPDATE BirthDayTable SET "
                        + " name='" + name + "'"
                        + " , day=" + day
                        + " , month=" + month
                        + " , photo_link='" + imagePath + "'"
                        + " where "
                        + " name='" + prevName + "'"
                        + " and day=" + prevDay
                        + " and month=" + prevMonth);
            }

            // if ((add_day.getSelectedIndex() + 1) == now.get(Calendar.DATE) &&
            //  add_month.getSelectedIndex() == now.get(Calendar.MONTH))
            // Birthday_tray.showTray();

            GlobalVariables.prefs.put("PHOTO_PATH", imagePath);

            if (month == LeftPanel.month.getSelectedIndex() && LeftPanel.month.getSelectedIndex() > 0) {
                LeftPanel.month.setSelectedIndex(month - 1);
            } else if (month == LeftPanel.month.getSelectedIndex()) {
                LeftPanel.month.setSelectedIndex(month + 1);
            }
            LeftPanel.month.setSelectedIndex(month);

            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can not insert!!!", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void deleteEntry(String name, int day, int month){
        try {
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
            SqlStatement = connection.createStatement();
            SqlStatement.execute("DELETE from BirthDayTable WHERE name='"+
                    name+
                    "' and day="+day+
                    " and month="+month);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Can not Delete Entry", "Deletion ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ResultSet getAllData(){
        ResultSet res = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:Birthday Database");
            SqlStatement = connection.createStatement();
            res = SqlStatement.executeQuery("SELECT name,day,month FROM BirthDayTable order by month,day");
            return res;
        } catch (SQLException e) {
        }
        return res;
    }
}
