package UI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LoginPrompt extends JFrame{
    private JTextField usernameBox = null;
    private JTextField passwordBox = null;
    private JButton loginButton = null;
    private JButton registerButton = null;

    public LoginPrompt(LoginComplete complete) throws SQLException, IOException {
        this.setLayout(new BorderLayout());
        JLabel label = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/pokedex.png")));
        this.add(label, BorderLayout.NORTH);
        this.add(this.getPanel(complete), BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);

    }

    private JPanel getPanel(final LoginComplete complete) throws SQLException {
        JPanel panel = new JPanel();
        this.usernameBox = new JTextField();
        this.passwordBox = new JPasswordField();
        GridLayout layout = new GridLayout(3, 2);
        layout.setHgap(25);
        layout.setVgap(25);
        panel.setLayout(layout);
        panel.add(new JLabel("Username:"));
        panel.add(this.usernameBox);
        panel.add(new JLabel("Password:"));
        panel.add(this.passwordBox);
        this.loginButton = new JButton("Login");
        this.registerButton = new JButton("Register");
        this.loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try{complete.login(LoginPrompt.this.usernameBox.getText(), LoginPrompt.this.passwordBox.getText());
                } catch (Exception eee){
                    eee.printStackTrace();
                };

            }
        });
        this.registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {complete.register(LoginPrompt.this.usernameBox.getText(), LoginPrompt.this.passwordBox.getText());
                } catch (Exception eee){
                    eee.printStackTrace();
                }
            }
        });
        panel.add(this.loginButton);
        panel.add(this.registerButton);
        return panel;
    }

}
