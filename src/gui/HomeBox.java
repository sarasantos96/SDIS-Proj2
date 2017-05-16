package gui;

import javafx.scene.control.SelectionModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by rita on 12-05-2017.
 */
public class HomeBox extends JFrame implements WindowListener,MouseListener,KeyListener{

    private JTextArea message = null;
    private JTextField send_message = null;
    private String username = null;

    public HomeBox(){

        super("Nome do Projeto");
        this.addWindowListener(this);
        this.setSize(800,600);
        this.setResizable(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        message = new JTextArea();
        message.setEditable(false);
        this.add(message,"Center");

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        panel1.setLayout(new FlowLayout());
        panel2.setLayout(new FlowLayout());
        panel3.setLayout(new FlowLayout());

        send_message = new JTextField(20);
        send_message.addKeyListener(this);
        panel1.add(send_message);


        JButton send = new JButton("Send");
        send.addMouseListener(this);
        panel1.add(send);

        JButton clear = new JButton("Clear");
        clear.addMouseListener(this);
        panel1.add(clear);

        JList groups = new JList();
        groups.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groups.setLayoutOrientation(JList.VERTICAL_WRAP);

        groups.add(new JButton("sdis"));
        groups.add(new JButton("lbaw"));
        groups.add(new JButton("ppin"));
        groups.add(new JButton("iart"));
        groups.add(new JButton("comp"));


        panel3.add(groups,"Center");

        panel2.setBackground(Color.BLUE);
        //panel3.setBackground(Color.RED);

        //this.add(panel1, "South");
        //this.add(panel2,"East");
        this.add(panel3, "North");

        this.setVisible(true);

        send_message.requestFocus();

    }

    public static void main(String[] args){

        HomeBox h = new HomeBox();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
