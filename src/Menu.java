/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author melon
 */
public class Menu extends JMenuBar implements ActionListener {

    JMenuItem item1, item11, item12, item2, item3, item4, item5, item6, item7, item8;
    private JMenuItem[] VirtualMenuItem;

    public JMenuBar setMenu() {
        JMenu[] VirtualMenu = new JMenu[3];
        VirtualMenu[0] = new JMenu();
        VirtualMenu[0].setText("File");
        VirtualMenuItem = new JMenuItem[6];;
        VirtualMenuItem[0] = new JMenuItem();
        VirtualMenuItem[0].setText("Export");
        VirtualMenuItem[0].addActionListener(new Export());
        VirtualMenu[0].add(VirtualMenuItem[0]);


        VirtualMenuItem[1] = new JMenuItem();
        VirtualMenuItem[1].setText("Exit");
        VirtualMenuItem[1].addActionListener(this);
        VirtualMenu[0].add(VirtualMenuItem[1]);
        add(VirtualMenu[0]);

        VirtualMenu[1] = new JMenu();
        VirtualMenu[1].setText("Settings");
        VirtualMenuItem[3] = new JMenuItem();
        VirtualMenuItem[3].setText("Basic");
        VirtualMenuItem[3].addActionListener(new Settings());
        VirtualMenu[1].add(VirtualMenuItem[3]);
        add(VirtualMenu[1]);

        VirtualMenuItem[4] = new JMenuItem();
        VirtualMenuItem[4].setText("Facebook Login");
        VirtualMenuItem[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //newbirthday.new facebookClass();
                new Facebook();
            }
        });
        VirtualMenu[1].add(VirtualMenuItem[4]);
        add(VirtualMenu[1]);

        VirtualMenu[2] = new JMenu();
        VirtualMenu[2].setText("Help");

        VirtualMenuItem[2] = new JMenuItem();
        VirtualMenuItem[2].setText("About");
        VirtualMenuItem[2].addActionListener(new About());
        VirtualMenu[2].add(VirtualMenuItem[2]);
        add(VirtualMenu[2]);
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == VirtualMenuItem[1]) new StaticTasks().QuitDialog();
    }
}
