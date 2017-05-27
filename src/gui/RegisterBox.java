package gui;

import rest.Client;
import rest.JSONRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by rita on 12-05-2017.
 */
public class RegisterBox extends JFrame implements KeyListener{

    private JTextField usernameField;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel nameLabel;
    private JButton registerButton;
    private JButton cancelButton;
    private StartBox startBox;


    public RegisterBox(StartBox startBox) throws HeadlessException {
        super("Register");
        this.startBox = startBox;

        setSize(300,150);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel buttons = new JPanel();

        GridBagConstraints cs = new GridBagConstraints();

        cs.fill = GridBagConstraints.HORIZONTAL;

        nameLabel = new JLabel("Name:");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(nameLabel,cs);

        nameField = new JTextField(15);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(nameField,cs);

        usernameLabel = new JLabel("Username:");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(usernameLabel,cs);

        usernameField = new JTextField(15);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(usernameField,cs);

        passwordLabel = new JLabel("Password:");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(passwordLabel,cs);

        passwordField = new JPasswordField(15);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(passwordField,cs);

        passwordField.addKeyListener(this);
        registerButton = new JButton("Register");
        cs.gridx=1;
        cs.gridy=3;
        cs.gridwidth = 1;
        buttons.add(registerButton,cs);

        registerButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String name = nameField.getText();
                        String username = usernameField.getText();
                        String password = passwordField.getText();

                        if(!name.equals("") && !username.equals("") && !password.equals("")){
                            try{
                                JSONRequest request = new JSONRequest("signIn", username, name, password, "","", "", "","", "");
                                boolean success = Client.sendPOSTMessage(request.getRequest());
                                if(success){
                                    JSONRequest request1 = new JSONRequest("login",username,"",password,"", "", "", "", "", "");
                                    boolean success1 = Client.sendPOSTMessage(request1.getRequest());
                                    if(success1) {
                                        dispose();
                                        HomeBox h = new HomeBox();
                                        startBox.setHomeBox(h);
                                        h.setVisible(true);
                                    }
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
        if(key==KeyEvent.VK_ENTER) {
            if (!getNameField().equals("") && !getUsernameField().equals("") && !getPasswordField().equals("")) {
                try {
                    JSONRequest request = new JSONRequest("signIn", getUsernameField().getText(), getNameField().getText(), getPasswordField().getText(), "", "", "", "", "", "");
                    boolean success = Client.sendPOSTMessage(request.getRequest());
                    if (success) {
                        dispose();
                        LoginBox h = new LoginBox(this.startBox);
                        h.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
