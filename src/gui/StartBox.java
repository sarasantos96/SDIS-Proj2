package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartBox extends JFrame {

    private JButton loginButton;
    private JButton registerButton;
    private HomeBox homeBox;
    private boolean viewHomeBox = false;


    public StartBox() throws HeadlessException {

        super("Welcome!");

        setSize(300,150);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        registerButton = new JButton("Register");
        cs.gridx=0;
        cs.gridy=0;
        cs.gridwidth = 1;
        panel.add(registerButton,cs);

        StartBox startBox = this;

        registerButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        dispose();
                        RegisterBox r = new RegisterBox(startBox);
                        r.setVisible(true);
                    }
                }
        );

        loginButton = new JButton("Login");
        cs.gridx=0;
        cs.gridy=3;
        cs.gridwidth = 1;
        panel.add(loginButton,cs);


        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        dispose();
                        LoginBox l = new LoginBox(startBox);
                        l.setVisible(true);
                    }
                }
        );

        add(panel,BorderLayout.CENTER);

        setVisible(true);

    }

    public boolean isViewHomeBox() {
        return viewHomeBox;
    }

    public void setHomeBox(HomeBox homeBox){
        this.homeBox = homeBox;
        this.viewHomeBox = true;
    }

    public void updateHomeBoxParticipants(){
        this.homeBox.updateParticipants();
    }

    public void updateHomeBoxMessages(){
        this.homeBox.updateMessages();
    }

    public void updateHomeBoxTasks(){
        this.homeBox.updateTaskPanel();
    }

    public static void main(String[] args){

        StartBox s = new StartBox();
    }
}
