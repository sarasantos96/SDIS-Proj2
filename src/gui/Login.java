package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by rita on 12-05-2017.
 */
public class Login extends JFrame{

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton cancelButton;


    public Login() throws HeadlessException {

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

        cancelButton = new JButton("Cancel");
        cs.gridx=1;
        cs.gridy=5;
        cs.gridwidth = 1;
        buttons.add(cancelButton,cs);

        add(panel,BorderLayout.CENTER);
        add(buttons,BorderLayout.PAGE_END);

        setVisible(true);

    }

    public static void main(String[] args){

        Login login = new Login();
    }
}
