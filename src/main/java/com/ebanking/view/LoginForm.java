package com.ebanking.view;

import com.ebanking.dao.UserDAO;
import com.ebanking.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private UserDAO userDAO;

    public LoginForm() {
        userDAO = new UserDAO();
        initComponents();
    }

    private void initComponents() {
        setTitle("eBanking Login");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        add(txtPassword, gbc);

        // Button Login
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        btnLogin = new JButton("Login");
        add(btnLogin, gbc);

        btnLogin.addActionListener(this::handleLogin);
    }

    private void handleLogin(ActionEvent e) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username/Password nggak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDAO.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login Sukses! Selamat datang, " + user.getUsername());
            // TODO: Buka Dashboard
            this.dispose();
            // new MainDashboard(user).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login Gagal! Cek lagi username/password lu.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
