package com.github.zlamb1.view;

import com.github.zlamb1.*;
import com.github.zlamb1.io.ImageLoader;
import com.github.zlamb1.layout.ILayoutService;
import com.github.zlamb1.layout.SeatLayoutDescriptor;
import com.github.zlamb1.svg.SVGButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    protected SeatFilter seatFilter;

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
    public MenuOption renderAppView(LoginService loginService, SeatService seatService) {
        SwingUtilities.invokeLater(() -> {
            contentPane = new JPanel();
            contentPane.setLayout(new GridBagLayout());
            gbc = new GridBagConstraints();

            createTopBar(loginService);
            createSideBar();

            JButton submitBtn = new JButton("Book Seats");
            submitBtn.setVisible(false);

            JPanel gridPanel = new JPanel();

            gridPanel.setLayout(new GridBagLayout());
            GridBagConstraints gridGBC = new GridBagConstraints();

            List<SeatButton> seatButtons = new ArrayList<>();
            List<ISeatDescriptor> seatDescriptors = new ArrayList<>();

            SeatTicket seatTicket = new SeatTicket();

            seatFilter.addFilterListener(filter -> {
                seatButtons.forEach(seatButton -> seatButton.setGhosted(
                    (filter.filterByWindowSeats() && !seatButton.getSeatDescriptor().isWindowSeat()) ||
                    (filter.filterByAisleSeats() && !seatButton.getSeatDescriptor().isAisleSeat())
                ));
                gridPanel.repaint();
            });

            ILayoutService layoutService = seatService.getLayoutService();
            for (SeatLayoutDescriptor seatLayoutDescriptor : layoutService) {
                int row = seatLayoutDescriptor.getRow();
                int col = seatLayoutDescriptor.getColumn();

                gridGBC.gridx = col;
                gridGBC.gridy = row;
                gridGBC.insets = layoutService.getGaps(row, col);

                if (seatLayoutDescriptor.hasDescriptor()) {
                    ISeatDescriptor seatDescriptor = seatLayoutDescriptor.getSeatDescriptor();
                    SeatButton btn = new SeatButton(seatLayoutDescriptor.getSeatDescriptor());
                    seatButtons.add(btn);

                    btn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseExited(MouseEvent e) {
                            if (seatDescriptor.equals(seatTicket.getSeatDescriptor()))
                                seatTicket.setSeatDescriptor(seatDescriptors.isEmpty() ? null : seatDescriptors.getLast());
                        }
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            boolean empty = seatDescriptors.isEmpty();
                            if (btn.isSelected())
                                seatDescriptors.remove(seatDescriptor);
                            else seatDescriptors.add(seatDescriptor);
                            btn.setSelected(!btn.isSelected());
                            if (seatDescriptors.isEmpty() != empty) {
                                submitBtn.setVisible(!seatDescriptors.isEmpty());
                                contentPane.revalidate();
                                contentPane.repaint();
                            }
                        }
                    });

                    btn.addMouseMotionListener(new MouseAdapter() {
                        private void setTicket() {
                            seatTicket.setSeatDescriptor(seatDescriptor);
                        }
                        @Override
                        public void mouseMoved(MouseEvent e) {
                            setTicket();
                        }
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            setTicket();
                        }
                    });

                    gridPanel.add(btn, gridGBC);
                } else {
                    JPanel filler = new JPanel();
                    filler.setOpaque(false);
                    // FIXME: do not use magic constants for size
                    filler.setPreferredSize(new Dimension(25, 25));
                    gridPanel.add(filler, gridGBC);
                }
            }

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 0.0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.NORTH;

            contentPane.add(gridPanel, gbc);

            gbc.gridy++;
            gbc.insets = new Insets(25, 0, 0, 0);
            gbc.ipadx = 150;

            contentPane.add(seatTicket, gbc);

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

    protected void createTopBar(LoginService loginService) {
        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));

        Customer customer = loginService.getLoggedIn();
        if (customer != null) {
            JLabel customerLabel = new JLabel("Hello, " + customer.getName() + "!");
            topBar.add(customerLabel);
        }

        topBar.add(Box.createHorizontalGlue());

        SVGButton logoutBtn = new SVGButton("logout.svg", null) {
            @Override
            public JToolTip createToolTip() {
                JToolTip toolTip = new JToolTip();
                toolTip.setBorder(new EmptyBorder(5, 5, 5, 5));
                return toolTip;
            }
        };

        logoutBtn.setSVGColor(logoutBtn.getForeground());

        logoutBtn.setPreferredSize(new Dimension(25, 25));
        logoutBtn.setMaximumSize(new Dimension(25, 25));

        logoutBtn.setToolTipText("Logout");
        logoutBtn.addActionListener(e -> {
            for (LogoutHandler logoutHandler : logoutHandlers) {
                logoutHandler.onLogout();
            }
        });

        topBar.add(logoutBtn);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = InsetHelper.createSouthInsets(5);

        contentPane.add(topBar, gbc);

        gbc.gridwidth = 1;
        gbc.insets = InsetHelper.createEmptyInsets();
    }

    protected void createSideBar() {
        JPanel sideBar = new JPanel();
        sideBar.setBorder(BorderFactory.createLineBorder(sideBar.getForeground()));

        sideBar.setLayout(new GridBagLayout());

        GridBagConstraints sideBarGBC = new GridBagConstraints();

        sideBarGBC.weighty = 1.0;
        sideBarGBC.fill = GridBagConstraints.BOTH;
        sideBarGBC.anchor = GridBagConstraints.NORTH;

        seatFilter = new SeatFilter();
        sideBar.add(seatFilter, sideBarGBC);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.insets = InsetHelper.createHorizontalInsets(5);
        gbc.anchor = GridBagConstraints.EAST;

        contentPane.add(sideBar, gbc);

        gbc.insets = InsetHelper.createEmptyInsets();
        gbc.gridheight = 1;
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
    public Collection<ISeatDescriptor> renderSeatView(LoginService loginService, SeatService seatService) {
        return List.of();
    }

    @Override
    public void renderLayoutView() {

    }
}
