package gui;

import sun.awt.X11.Screen;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by rita on 12-05-2017.
 */
public class Home extends JFrame {

    private JToolBar groupBar;
    private ArrayList<JButton> groups;

    public Home() throws HeadlessException {

        super("Home");

        setSize(1000,1000);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        groupBar = new JToolBar(JToolBar.HORIZONTAL);
        groupBar.setBounds(0,0, 400,40);
        groupBar.setFloatable(false);


        groups = new ArrayList<>();
        groups.add(new JButton("SDIS"));
        groups.add(new JButton("LBAW"));
        groups.add(new JButton("IART"));
        groups.add(new JButton("COMP"));

        for(int i=0; i < groups.size();i++){
            groupBar.add(groups.get(i));
        }

        panel.add(groupBar,BorderLayout.WEST);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args){

        Home h = new Home();
    }
}
