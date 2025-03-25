package com.github.zlamb1.view;

import com.github.zlamb1.LoginService;

public interface IAppView extends ISeatView, ILoginView {
    enum MenuOption {
        SEAT_BOOKING
    }

    MenuOption renderAppView(LoginService loginService);
}
