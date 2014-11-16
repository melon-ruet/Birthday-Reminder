import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;

public class FacebookClass
{
    private String AccessToken;
    private JWebBrowser webBrowser = new JWebBrowser();
    private JPanel loginPanel;
    private JRadioButton Synchronize, disconnectFb, removeNadd ;
    private JButton okButton,cancelButton;
    private JFrame facebookFrame;

    FacebookClass()
    {
        AccessToken = GlobalVariables.prefs.get("ACCESS_TOKEN", "");

        webBrowser = new JWebBrowser();
        facebookFrame = new JFrame();
        facebookFrame.setIconImage((new ImageIcon(getClass().getResource("image/birthdayIcon.png"),
                "tray icon")).getImage());
        facebookFrame.setLayout(new BorderLayout());
        facebookFrame.setTitle("Connect Facebook");
        facebookFrame.setResizable(false);
        facebookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        okButton = new JButton("OK");
        okButton.setEnabled(false);

        cancelButton = new JButton("Cancel");

        Synchronize = new JRadioButton("Synchronize");
        Synchronize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                okButton.setEnabled(true);
            }
        });

        removeNadd = new JRadioButton("Remove and Add");
        removeNadd.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                okButton.setEnabled(true);
            }
        });

        disconnectFb = new JRadioButton("Disconnect facebook");
        disconnectFb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                okButton.setEnabled(true);
            }
        });

        ButtonGroup fbGroup = new ButtonGroup();
        fbGroup.add(Synchronize);
        fbGroup.add(disconnectFb);
        fbGroup.add(removeNadd);



        loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        loginPanel.setLocation(0, 0);
        loginPanel.setSize(410, 310);
        loginPanel.add(Synchronize);
        loginPanel.add(disconnectFb);
        loginPanel.add(removeNadd);
        loginPanel.add(okButton);
        loginPanel.add(cancelButton);
        facebookFrame.add(loginPanel, BorderLayout.CENTER);
        facebookFrame.setVisible(true);


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButton.setEnabled(false);

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
                                    /*webBrowser.navigate("https://graph.facebook.com/oauth/authorize?"
                                            + "type=user_agent&"
                                            + "client_id=615823635109651&"
                                            + "redirect_uri=http://www.google.com&"
                                            + "scope=publish_actions,friends_birthday,publish_stream,friends_photos");
                                    */
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
                    Thread tokenNull = new TokenNotNull(facebookFrame);
                    tokenNull.start();
                }
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                facebookFrame.dispose();
            }
        });


        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {

            @Override
            public void loadingProgressChanged(WebBrowserEvent wbe) {

                if(webBrowser.getResourceLocation().startsWith("http://www.google")){
                    AccessToken = webBrowser.getResourceLocation();
                    AccessToken = AccessToken.replaceFirst("http://www.google.com/#access_token=", "");
                    AccessToken = AccessToken.substring(0, AccessToken.indexOf("&expires_in="));
                    GlobalVariables.prefs.put("ACCESS_TOKEN", AccessToken);

                    facebookFrame.remove(webBrowser);
                    facebookFrame.repaint();
                    facebookFrame.setSize(230, 220);
                    facebookFrame.setResizable(false);
                    Thread tokenNull = new TokenNotNull();
                    tokenNull.start();
                }
            }


            @Override
            public void titleChanged(WebBrowserEvent wbe) {
                facebookFrame.setTitle(webBrowser.getPageTitle());
            }
        });
    }
}