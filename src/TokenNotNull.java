import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.json.JsonObject;
import com.restfb.types.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by melon on 6/22/14.
 */

public class TokenNotNull extends Thread{

    private String AccessToken;
    Calendar birthdayCalendar = Calendar.getInstance();
    private JFrame inFrame;

    TokenNotNull(JFrame frame){

    }

    @Override
    public void run() {
        try {
            if((java.lang.Runtime.getRuntime().exec("ping www.facebook.com")).waitFor() != 0)
            {
                JOptionPane.showMessageDialog(null, "Check Internet connection!!!",
                        "Network Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (Exception ex) { }


        File dir = new File(GlobalVariables.prefs.get("IMAGE_PATH", ""));
        if(!dir.exists())
            dir.mkdir();

        facebookFrame.remove(loginPanel);
        facebookFrame.setTitle("Downloading...");
        facebookFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        JPanel progressPanel = new JPanel();
        progressPanel.setSize(230, 220);
        progressPanel.setLocation(0, 0);

        JProgressBar dataProgress = new JProgressBar();
        dataProgress.setSize(150, 40);
        dataProgress.setLocation(40, 70);
        dataProgress.setStringPainted(true);
        progressPanel.add(dataProgress);

        facebookFrame.add(progressPanel);
        facebookFrame.repaint();


        final FacebookClient facebookClient = new DefaultFacebookClient(AccessToken);
        Connection<User> myFriends;

        myFriends = facebookClient.fetchConnection("me/friends", User.class,
                Parameter.with("fields", "name,birthday"));

        dataProgress.setMaximum(myFriends.getData().size());
        dataProgress.setMinimum(0);
        int progress = 0;
        if(myFriends!=null)
            for(User friend: myFriends.getData()){
                if(friend.getBirthday()!=null){
                    String birthdayString = friend.getBirthday();
                    if(birthdayString.length()<=6)
                        birthdayString+="/2013";

                    Date date = null;
                    try {
                        date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(birthdayString);
                    } catch (ParseException ex) {}

                    birthdayCalendar.setTime(date);
                    boolean checkQuery=false;
                    try
                    {
                        ResultSet resultSet = BirthdayReminder.SqlStatement.executeQuery("SELECT * from BirthDayTable where day="+
                                (birthdayCalendar.get(Calendar.DATE)-1) +" and month="+
                                birthdayCalendar.get(Calendar.MONTH) +" and  name='"+
                                friend.getName()+"'");
                        while(resultSet.next()){
                            checkQuery=true;
                            if(removeNadd.isSelected()){
                                (new File(resultSet.getString("photo_link"))).delete();
                                BirthdayReminder.SqlStatement.execute("DELETE from BirthDayTable where day="+
                                        (birthdayCalendar.get(Calendar.DATE)-1) +" and month="+
                                        birthdayCalendar.get(Calendar.MONTH) +" and  name='"+
                                        friend.getName()+"'");
                                checkQuery=false;
                            }
                            break;
                        }

                        if(!checkQuery)
                        {
                            JsonObject jsonObject = facebookClient.fetchObject(friend.getId(),
                                    JsonObject.class, Parameter.with("fields", "picture.width(200).height(200)"));
                            String profilePicture = jsonObject.getJsonObject("picture").getString("data");
                            profilePicture = profilePicture.substring(profilePicture.indexOf("http"),
                                    profilePicture.indexOf("\"}"));

                            //Download friend's picture
                            String photoLink = GlobalVariables.prefs.get("IMAGE_PATH", "") +
                                    friend.getName()+
                                    birthdayCalendar.get(Calendar.DATE)+
                                    birthdayCalendar.get(Calendar.MONTH)+".jpg";


                            URL url = new URL(profilePicture);
                            InputStream in = new BufferedInputStream(url.openStream());
                            ByteArrayOutputStream out = new ByteArrayOutputStream();

                            byte[] buf = new byte[1024];
                            int n = 0;
                            while (-1!=(n=in.read(buf)))
                                out.write(buf, 0, n);

                            out.close();
                            in.close();
                            byte[] response = out.toByteArray();


                            FileOutputStream fos = new FileOutputStream(photoLink);
                            fos.write(response);
                            fos.close();
                            ////download and save complete

                            BirthdayReminder.SqlStatement.execute("INSERT INTO BirthDayTable values("+
                                    (birthdayCalendar.get(Calendar.DATE)-1) +","+
                                    birthdayCalendar.get(Calendar.MONTH) +",'"+
                                    friend.getName()+"','"+
                                    photoLink+"')");
                        }
                    }
                    catch (Exception ex) { }
                }
                dataProgress.setValue(progress++);
            }


        //viewAllEntryClass facebookViewAlls = new viewAllEntryClass();
        //facebookViewAlls.actionPerformed(new ActionEvent(facebookViewAlls,
                //ActionEvent.ACTION_PERFORMED, facebookViewAlls.toString()));

        facebookFrame.remove(progressPanel);
        facebookFrame.add(loginPanel);
        facebookFrame.repaint();
        facebookFrame.setTitle("Connect Facebook");
        facebookFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        okButton.setEnabled(false);
    }
}

