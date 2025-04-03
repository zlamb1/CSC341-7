package com.github.zlamb1;

import com.github.zlamb1.io.IAppSerializer;
import com.github.zlamb1.layout.LayoutService;
import com.github.zlamb1.view.IAppView;
import com.github.zlamb1.layout.LayoutFactory;
import com.github.zlamb1.layout.SeatLayout;

public class SeatApp {
    protected IAppView appView;
    protected IAppSerializer appSerializer;
    protected LoginService loginService;

    protected SeatService seatService;

    public SeatApp(IAppView appView, IAppSerializer appSerializer) {
        this.appView = appView;
        this.appSerializer = appSerializer;
        this.loginService = new LoginService(appSerializer);

        SeatLayout seatLayout = LayoutFactory.createStandardLayout();
        this.seatService = new SeatService(seatLayout, loginService);

        appView.addLoginHandler(this::onLogin);
        appView.addRegisterHandler(this::onRegister);
        appView.addLogoutHandler(this::onLogout);
    }

    public void startApp() {
        appView.renderLoginView();
    }

    public void onLogin(String name, String password) {
        String nameHint = null, passwordHint = loginService.getPasswordValidator().getHint(password);

        if (name == null || name.isEmpty()) {
            nameHint = "Name is required.";
        } else if (passwordHint == null) {
            LoginService.LoginResult loginResult;
            loginResult = loginService.login(name, password);
            switch (loginResult) {
                case SUCCESS -> appView.renderAppView(loginService, seatService);
                case WRONG_NAME -> nameHint = "Unknown name.";
                case WRONG_PASSWORD -> passwordHint = "Incorrect password.";
                default -> passwordHint = "Unknown error occurred.";
            }
        }

        appView.renderNameHint(nameHint);
        appView.renderPasswordHint(passwordHint);
    }

    public void onRegister(String name, String password, String confirmPassword) {
        String nameHint = null, passwordHint = loginService.getPasswordValidator().getHint(password);

        if (name == null || name.isEmpty()) {
            nameHint = "Name is required.";
        }

        if (passwordHint == null && !password.equals(confirmPassword))
            passwordHint = "Passwords do not match.";

        if (nameHint == null && passwordHint == null) {
            LoginService.RegisterResult registerResult;
            registerResult = loginService.register(name, password);
            switch (registerResult) {
                case SUCCESS -> appView.renderLoginView();
                case DUPLICATE_NAME -> nameHint = "Name is taken.";
                case SHORT_PASSWORD -> passwordHint = "Password is too short.";
                case PASSWORD_COMPLEXITY -> passwordHint = "Password is not complex enough.";
                default -> passwordHint = "Unknown error occurred.";
            }
        }

        appView.renderNameHint(nameHint);
        appView.renderPasswordHint(passwordHint);
    }

    public void onLogout() {
        loginService.logout();
        appView.renderLoginView();
    }

}
