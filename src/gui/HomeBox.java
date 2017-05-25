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
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        //leftPanel.setBackground(Color.BLUE);
        JPanel centerPanel = new JPanel();
        //centerPanel.setBackground(Color.CYAN);
        JPanel rightPanel = new JPanel(new GridLayout(0, 1, 6, 1));
        //rightPanel.setBackground(Color.GREEN);

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

        JLabel participantsLabel = new JLabel("Participants:");
        leftPanel.add(participantsLabel,BorderLayout.PAGE_START);

        DefaultListModel modelParticipants = new DefaultListModel();
        modelParticipants.addElement(new String("doggo123"));
        modelParticipants.addElement(new String("fofinha45"));
        modelParticipants.addElement(new String("hitler666"));
        JList participants =  new JList(modelParticipants);
        participants.setEnabled(false);
        leftPanel.add(participants);

        JLabel toDoLabel = new JLabel("To Do:");
        rightPanel.add(toDoLabel, BorderLayout.PAGE_START);


        JCheckBox n = new JCheckBox("Finish Car Class");
        JCheckBox j = new JCheckBox("Clean Graphic Interface");
        JCheckBox a = new JCheckBox("Choose a name for the project");
        JCheckBox b = new JCheckBox("Finish Car Class");
        JCheckBox c = new JCheckBox("Clean Graphic Interface");
        JCheckBox d = new JCheckBox("Choose a name for the project");
        JCheckBox e = new JCheckBox("Finish Car Class");
        JCheckBox f = new JCheckBox("Clean Graphic Interface");
        JCheckBox g = new JCheckBox("Choose a name for the project");


        rightPanel.add(n);
        rightPanel.add(j);
        rightPanel.add(a);
        rightPanel.add(b);
        rightPanel.add(c);
        rightPanel.add(d);
        rightPanel.add(e);
        rightPanel.add(f);
        rightPanel.add(g);



        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, message, centerPanel);
        JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,sp);
        JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sp1,rightPanel);

        sp.setResizeWeight(1.0);
        sp1.setResizeWeight(0.5);
        sp2.setResizeWeight(0.6);

        sp.setEnabled(false);
        sp1.setEnabled(false);
        sp2.setEnabled(false);

        add(sp2, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

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
