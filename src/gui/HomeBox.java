package gui;

import logic.Task;
import logic.User;
import org.json.JSONException;
import rest.Client;
import rest.JSONRequest;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by rita on 12-05-2017.
 */
public class HomeBox extends JFrame implements WindowListener,MouseListener,KeyListener{

    private JTextArea message = null;
    private JTextField send_message = null;
    private String username = null;
    private JTextArea todo = null;
    private JTextField todo_text = null;


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
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message_text = send_message.getText();
                try{
                    JSONRequest sendMessageRequest = new JSONRequest("sendMessage","","","", "", ""+Client.logUser.getId(), "1","",message_text,"");
                    boolean sendMessage = Client.sendPOSTMessage(sendMessageRequest.getRequest());
                    if(sendMessage)
                        send_message.setText("");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        centerPanel.add(send);

        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                send_message.setText("");
            }
        });
        centerPanel.add(clear);

        JLabel participantsLabel = new JLabel("Participants:");
        leftPanel.add(participantsLabel,BorderLayout.PAGE_START);

        DefaultListModel modelParticipants = new DefaultListModel();
        try{
            java.util.List<User> users = Client.sendGETMessage("getUsers",""+Client.logUser.getId());
            for(User u : users){
                modelParticipants.addElement(u.getUsername());
            }
            JList participants =  new JList(modelParticipants);
            participants.setEnabled(false);
            leftPanel.add(participants);


        }catch (Exception e){
            e.printStackTrace();
        }


        JLabel toDoLabel = new JLabel("To Do:");
        rightPanel.add(toDoLabel, BorderLayout.PAGE_START);

        todo = new JTextArea();
        todo.setEditable(false);
        this.add(todo);

        todo_text = new JTextField(10);
        todo_text.addKeyListener(this);
        todo_text.requestFocus();
        rightPanel.add(todo_text);

        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String todo_message = todo_text.getText();
                try{
                    JSONRequest addToDoRequest = new JSONRequest("addToDo","","","", "", "", "1","","",""+todo_message);
                    boolean sendMessage = Client.sendPOSTMessage(addToDoRequest.getRequest());
                    if(sendMessage)
                        todo_text.setText("");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        rightPanel.add(add);

        try{
            List<Task> tasks = Client.sendGETMessage("getTodoGroup", "1");
            for(Task task : tasks){
                JCheckBox n = new JCheckBox(task.getTask(),task.isIsdone());
                if(task.isIsdone()){
                    n.setEnabled(false);
                }
                n.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try{
                            JSONRequest checkToDoRequest = new JSONRequest("checkToDo","","","", "", "", "",""+task.getId(),"","");
                            boolean checked = Client.sendPOSTMessage(checkToDoRequest.getRequest());
                            if(checked)
                                n.setEnabled(false);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });
                rightPanel.add(n);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


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
