package gui;

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


    public HomeBox() {

        super("Nome do Projeto");

        this.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.BLUE);
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.CYAN);
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.GREEN);

        message = new JTextArea();
        message.setEditable(false);
        this.add(message);

        send_message = new JTextField(20);
        send_message.addKeyListener(this);
        send_message.requestFocus();
        centerPanel.add(send_message);

        JButton send = new JButton("Send");
        send.addMouseListener(this);
        centerPanel.add(send);

        JButton clear = new JButton("Clear");
        clear.addMouseListener(this);
        centerPanel.add(clear);

        JLabel group = new JLabel("Select a Group:");
        leftPanel.add(group,BorderLayout.PAGE_START);


        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, message, centerPanel);
        JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,sp);
        JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sp1,rightPanel);

        sp.setResizeWeight(1.0);
        sp1.setResizeWeight(0.3);
        sp2.setResizeWeight(0.5);

        sp.setEnabled(false);
        sp1.setEnabled(false);
        sp2.setEnabled(false);

        this.add(sp2, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);
        this.setVisible(true);

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
