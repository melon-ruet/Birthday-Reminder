import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by melon on 6/23/14.
 */
/*
public class FacebookActions implements ActionListener{

    int button;

    FacebookActions(int clickedButton){
        button = clickedButton;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(button == 1){
            (JButton)actionEvent.getSource().setEnabled(false);

            //disconnect from facebook
            if(disconnectFb.isSelected()){
                AccessToken = "";
                GlobalVariables.prefs.put("ACCESS_TOKEN", "");
            }

            //Sync data with facebook
            if((removeNadd.isSelected() || Synchronize.isSelected()) && AccessToken.equals(""))
            {
                NativeInterface.open();
                NativeInterface.runEventPump();

                try {
                    if((java.lang.Runtime.getRuntime().exec("ping www.facebook.com")).waitFor() == 0)
                    {
                        facebookFrame.remove(loginPanel);
                        facebookFrame.repaint();
                        facebookFrame.setSize(700, 520);


                        webBrowser.setJavascriptEnabled(true);
                        facebookFrame.setResizable(true);
                        //webBrowser.setBarsVisible(false);
                        //webBrowser.setStatusBarVisible(false);
                        webBrowser.setLocation(0, 0);
                        webBrowser.setSize(680, 480);
                        facebookFrame.add(webBrowser);
                                    webBrowser.navigate("https://graph.facebook.com/oauth/authorize?"
                                            + "type=user_agent&"
                                            + "client_id=615823635109651&"
                                            + "redirect_uri=http://www.google.com&"
                                            + "scope=publish_actions,friends_birthday,publish_stream,friends_photos");

                        webBrowser.navigate("https://graph.facebook.com/oauth/authorize?"
                                + "type=user_agent&"
                                + "client_id=615823635109651&"
                                + "redirect_uri=http://www.google.com");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Check Internet connection!!!",
                                "Network Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {}
            }

            else if((removeNadd.isSelected() || Synchronize.isSelected()) && !AccessToken.equals("")){
                Thread tokenNull = new TokenNotNull();
                tokenNull.start();
            }
        }
    }
}
*/