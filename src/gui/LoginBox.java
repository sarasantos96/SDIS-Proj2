package gui;

import rest.Client;
import rest.JSONRequest;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginBox extends JFrame implements KeyListener{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton cancelButton;
    private StartBox startBox;


    public LoginBox(StartBox startBox) throws HeadlessException{

        super("Login");

        this.startBox = startBox;

        setSize(300,150);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttons = new JPanel();


        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;


        usernameLabel = new JLabel("Username:");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(usernameLabel,cs);

        usernameField = new JTextField(15);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(usernameField,cs);

        passwordLabel = new JLabel("Password:");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(passwordLabel,cs);

        passwordField = new JPasswordField(15);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(passwordField,cs);


        passwordField.addKeyListener(this);

        Box.createVerticalStrut(140);

        loginButton = new JButton("Login");
        cs.gridx=1;
        cs.gridy=3;
        cs.gridwidth = 1;
        buttons.add(loginButton,cs);

        loginButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent)  {
                        String username = usernameField.getText();
                        String password = passwordField.getText();

                        if(!username.equals("") && !password.equals("")){
                            try{
                                JSONRequest request = new JSONRequest("login",username,"", password,"", "", "", "", "", "");
                                boolean success = Client.sendPOSTMessage(request.getRequest());
                                if(success){
                                    HomeBox h = new HomeBox();
                                    startBox.setHomeBox(h);
                                    h.setVisible(true);
                                    dispose();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );


        cancelButton = new JButton("Cancel");
        cs.gridx=1;
        cs.gridy=5;
        cs.gridwidth = 1;
        buttons.add(cancelButton,cs);

        cancelButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        dispose();
                        startBox.setVisible(true);
                    }
                }
        );


        add(panel,BorderLayout.CENTER);
        add(buttons,BorderLayout.PAGE_END);

        setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {


    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if(key==KeyEvent.VK_ENTER){
            if(!getUsernameField().equals("") && !getPasswordField().equals("")){
                try{
                    JSONRequest request = new JSONRequest("login",getUsernameField(),"",getPasswordField(),"", "", "", "", "", "");
                    boolean success = Client.sendPOSTMessage(request.getRequest());
                    if(success){
                        HomeBox h = new HomeBox();
                        this.startBox.setHomeBox(h);
                        dispose();
                        h.setVisible(true);
                    }

                }catch (Exception t){
                    t.printStackTrace();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public String getUsernameField() {
        return usernameField.getText().trim();
    }

    public String getPasswordField() {
        return new String (passwordField.getPassword());
    }
}
