package gui;

import logic.Message;
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
    private JTextArea todo = null;
    private JTextField todo_text = null;
    private JPanel participantsPanel = null;
    private JPanel messagePanel = null;
    private JPanel todoPanel = null;
    private JPanel addToDoPanel = null;
    private JButton add = null;
    private JButton send = null;
    private JButton clear = null;
    private  DefaultListModel modelParticipants = null;


    public HomeBox() {

        super("Nome do Projeto");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        participantsPanel = new JPanel(new BorderLayout());
        messagePanel = new JPanel();
        todoPanel = new JPanel(new GridLayout(0, 1, 6, 1));
        addToDoPanel = new JPanel();

        message = new JTextArea();
        message.setEditable(false);
        printMessage();
        this.add(message);

        send_message = new JTextField(20);
        send_message.addKeyListener(this);
        send_message.requestFocus();
        messagePanel.add(send_message);

        send = new JButton("Send");
        sendButtonAction();
        messagePanel.add(send);

        clear = new JButton("Clear");
        clearButtonAction();
        messagePanel.add(clear);

        JLabel participantsLabel = new JLabel("Participants:");
        participantsPanel.add(participantsLabel,BorderLayout.PAGE_START);
        modelParticipants = new DefaultListModel();
        printParticipants();

        JLabel toDoLabel = new JLabel("To Do:");
        todoPanel.add(toDoLabel, BorderLayout.PAGE_START);

        todo = new JTextArea();
        todo.setEditable(false);
        this.add(todo);

        todo_text = new JTextField(10);
        todo_text.addKeyListener(this);
        todo_text.requestFocus();
        addToDoPanel.add(todo_text, BorderLayout.PAGE_END);

        add = new JButton("Add");
        addButtonAction();


        addToDoPanel.add(add, BorderLayout.PAGE_END);
        printToDos();

        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, message, messagePanel);
        JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,todoPanel,addToDoPanel);
        JSplitPane sp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sp2,sp);
        JSplitPane sp3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,participantsPanel,sp1);

        sp.setResizeWeight(1.0);
        sp1.setResizeWeight(0.5);
        sp2.setResizeWeight(0.95);
        sp3.setResizeWeight(0.5);


        sp.setEnabled(false);
        sp1.setEnabled(false);
        sp2.setEnabled(false);
        sp3.setEnabled(false);

        add(sp3, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

    public void printMessage(){

        try {
            List<Message> messages = Client.sendGETMessage("getMessagesGroup","1");
            for(Message m : messages){
               message.append(m.getSender().getUsername() + ": ");
               message.append(m.getContent() + "\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void printToDos(){
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
                todoPanel.add(n);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printParticipants(){
        try{
            java.util.List<User> users = Client.sendGETMessage("getUsers",""+Client.logUser.getId());
            for(User u : users){
                modelParticipants.addElement(u.getUsername());
            }
            JList participants =  new JList(modelParticipants);
            participants.setEnabled(false);
            participantsPanel.add(participants);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addButtonAction(){
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
    }

    public void clearButtonAction(){
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                send_message.setText("");
            }
        });
    }

    public void sendButtonAction(){
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
