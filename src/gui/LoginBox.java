package gui;

import org.json.JSONException;
import rest.Client;
import rest.JSONRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by rita on 12-05-2017.
 */
public class LoginBox extends JFrame{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton cancelButton;


    public LoginBox() throws HeadlessException{

        super("Login");

        setSize(300,150);
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
                                    dispose();
                                    HomeBox h = new HomeBox();
                                    h.setVisible(true);
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
                        StartBox s = new StartBox();
                        s.setVisible(true);
                    }
                }
        );

        add(panel,BorderLayout.CENTER);
        add(buttons,BorderLayout.PAGE_END);

        setVisible(true);

    }

    public String getUsernameField() {
        return usernameField.getText().trim();
    }

    public String getPasswordField() {
        return new String (passwordField.getPassword());
    }

    public static void main(String[] args){

        LoginBox loginBox = new LoginBox();
    }
}
