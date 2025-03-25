package com.github.zlamb1.view;

import com.github.zlamb1.Customer;
import com.github.zlamb1.ISeatDescriptor;
import com.github.zlamb1.LoginService;
import com.github.zlamb1.io.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppView implements IAppView {
    protected JFrame frame;
    protected JPanel contentPane;
    protected GridBagConstraints gbc;
    protected JLabel planeIcon;

    protected Field nameField;
    protected PasswordField passwordField, passwordFieldTwo;

    protected int logoWidth = 225, logoHeight = 150;

    protected List<LoginHandler> loginHandlers = new ArrayList<>();
    protected List<RegisterHandler> registerHandlers = new ArrayList<>();
    protected List<LogoutHandler> logoutHandlers = new ArrayList<>();

    public AppView() {
        Image planeImage = ImageLoader.loadImage("plane.png", logoWidth, logoHeight);
        if (planeImage != null) {
            planeIcon = new JLabel(new ImageIcon(planeImage));
            planeIcon.setPreferredSize(new Dimension(logoWidth, logoHeight));
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            nameField = new Field("Name");
            passwordField = new PasswordField("Password");
            passwordFieldTwo = new PasswordField("Confirm Password");

            frame = new JFrame("Seat Assignment");
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    @Override
    public MenuOption renderAppView(LoginService loginService) {
        SwingUtilities.invokeLater(() -> {
            contentPane = new JPanel();
            contentPane.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();

            int logoutButtonSize = 35;
            int logoutIconSize = logoutButtonSize - 10;

            Image logoutImage = ImageLoader.loadImage("logout.png", logoutIconSize, logoutIconSize);
            assert logoutImage != null;

            JPanel topBar = new JPanel();
            topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));

            Customer customer = loginService.getLoggedIn();
            if (customer != null) {
                JLabel customerLabel = new JLabel("Hello, " + customer.getName() + "!");
                topBar.add(customerLabel);
            }

            topBar.add(Box.createHorizontalGlue());

            JButton logoutButton = new JButton(new ImageIcon(logoutImage)) {
                @Override
                public JToolTip createToolTip() {
                    JToolTip toolTip = new JToolTip();
                    toolTip.setBorder(new EmptyBorder(5, 5, 5, 5));
                    return toolTip;
                }
            };

            logoutButton.setPreferredSize(new Dimension(logoutButtonSize, logoutButtonSize));
            logoutButton.setToolTipText("Logout");
            logoutButton.addActionListener(e -> {
                for (LogoutHandler logoutHandler : logoutHandlers) {
                    logoutHandler.onLogout();
                }
            });

            topBar.add(logoutButton);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.NORTHWEST;

            contentPane.add(topBar, gbc);

            JPanel gridPanel = new JPanel();

            gridPanel.setLayout(new GridLayout(6, 24, 2, 2));

            for (int r = 0; r < 6; r++) {
                for (int c = 0; c < 24; c++) {
                    SeatButton btn = new SeatButton();
                    gridPanel.add(btn);
                }
            }

            gbc.gridy++;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.NORTH;

            contentPane.add(gridPanel, gbc);

            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

            frame.setContentPane(contentPane);
            frame.revalidate();
        });

        return null;
    }

    @Override
    public void renderLoginView() {
        SwingUtilities.invokeLater(() -> {
            createLoginRegisterMenu();

            JButton loginButton = new JButton("Login");
            loginButton.setPreferredSize(new Dimension(100, 35));

            nameField.addActionListener(this::onLogin);
            passwordField.addActionListener(this::onLogin);
            loginButton.addActionListener(this::onLogin);

            JButton registerButton = new JButton("Create Account");
            registerButton.setPreferredSize(new Dimension(100, 35));
            registerButton.addActionListener((e) -> renderRegisterView());

            gbc.gridy++;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(24, 0, 5, 0);
            contentPane.add(loginButton, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 5, 0);
            contentPane.add(registerButton, gbc);

            frame.setContentPane(contentPane);
            frame.revalidate();
        });
    }

    @Override
    public void renderRegisterView() {
        SwingUtilities.invokeLater(() -> {
            createLoginRegisterMenu();

            JButton registerButton = new JButton("Register");
            registerButton.setPreferredSize(new Dimension(100, 35));

            nameField.addActionListener(this::onRegister);
            passwordField.addActionListener(this::onRegister);
            passwordFieldTwo.addActionListener(this::onRegister);
            registerButton.addActionListener(this::onRegister);

            JButton loginButton = new JButton("Return to Login");
            loginButton.setPreferredSize(new Dimension(100, 35));
            loginButton.addActionListener((e) -> renderLoginView());

            gbc.gridy++;
            contentPane.add(passwordFieldTwo, gbc);

            gbc.gridy++;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(24, 0, 5, 0);
            contentPane.add(registerButton, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(0, 0, 5, 0);
            contentPane.add(loginButton, gbc);

            frame.setContentPane(contentPane);
            frame.revalidate();
        });
    }

    protected void createLoginRegisterMenu() {
        nameField.clear();
        nameField.removeActionListeners();

        passwordField.clear();
        passwordField.resetEchoChar();
        passwordField.removeActionListeners();

        passwordFieldTwo.clear();
        passwordFieldTwo.resetEchoChar();
        passwordFieldTwo.removeActionListeners();

        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.ipadx = logoWidth;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        if (planeIcon != null) {
            contentPane.add(planeIcon, gbc);
            gbc.gridy++;
        }

        gbc.insets = new Insets(5, 0, 3, 0);
        contentPane.add(nameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(5, 0, 3, 0);
        contentPane.add(passwordField, gbc);
    }

    protected void onLogin(ActionEvent e) {
        String name = nameField.getValue();
        String password = passwordField.getValue();
        for (LoginHandler handler : loginHandlers) {
            handler.onLogin(name, password);
        }
    }

    protected void onRegister(ActionEvent e) {
        String name = nameField.getValue();
        String password = passwordField.getValue();
        String confirmPassword = passwordFieldTwo.getValue();
        for (RegisterHandler handler : registerHandlers) {
            handler.onRegister(name, password, confirmPassword);
        }
    }

    @Override
    public void renderNameHint(String hint) {
        SwingUtilities.invokeLater(() -> {
            nameField.setHint(hint);
        });
    }

    @Override
    public void renderPasswordHint(String hint) {
        SwingUtilities.invokeLater(() -> {
            passwordField.setHint(hint);
        });
    }

    @Override
    public void addLoginHandler(LoginHandler handler) {
        loginHandlers.add(handler);
    }

    @Override
    public boolean removeLoginHandler(LoginHandler handler) {
        return loginHandlers.remove(handler);
    }

    @Override
    public void addRegisterHandler(RegisterHandler handler) {
        registerHandlers.add(handler);
    }

    @Override
    public boolean removeRegisterHandler(RegisterHandler handler) {
        return registerHandlers.remove(handler);
    }

    @Override
    public void addLogoutHandler(LogoutHandler handler) {
        logoutHandlers.add(handler);
    }

    @Override
    public boolean removeLogoutHandler(LogoutHandler handler) {
        return logoutHandlers.remove(handler);
    }

    @Override
    public Collection<ISeatDescriptor> renderSeatView(LoginService loginService, Collection<ISeatDescriptor> seats) {
        return List.of();
    }
}
