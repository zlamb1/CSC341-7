package com.github.zlamb1.view;

public interface ILoginView {
    interface LoginHandler {
        void onLogin(String name, String password);
    }

    interface RegisterHandler {
        void onRegister(String name, String password, String confirmPassword);
    }

    interface LogoutHandler {
        void onLogout();
    }

    void renderLoginView();
    void renderRegisterView();

    void renderNameHint(String hint);
    void renderPasswordHint(String hint);

    void addLoginHandler(LoginHandler handler);
    boolean removeLoginHandler(LoginHandler handler);

    void addRegisterHandler(RegisterHandler handler);
    boolean removeRegisterHandler(RegisterHandler handler);

    void addLogoutHandler(LogoutHandler handler);
    boolean removeLogoutHandler(LogoutHandler handler);
}
