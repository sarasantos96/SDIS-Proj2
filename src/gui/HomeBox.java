package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by rita on 12-05-2017.
 */
public class HomeBox extends JFrame {

    private JList<String> groupList;
    private  DefaultListModel<String> listModel;


    public HomeBox() throws HeadlessException {

        super("Home");

        setSize(100,100);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();

        listModel = new DefaultListModel<>();
        listModel.addElement("SDIS");
        listModel.addElement("LBAW");
        listModel.addElement("COMP");
        listModel.addElement("IART");
        listModel.addElement("PPIN");

        groupList = new JList<>(listModel);
        panel.add(groupList);
        panel.add(new JScrollPane(groupList));
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args){

        HomeBox h = new HomeBox();
    }
}
