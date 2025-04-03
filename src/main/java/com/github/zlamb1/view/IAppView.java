package com.github.zlamb1.view;

import com.github.zlamb1.LoginService;
import com.github.zlamb1.SeatService;

public interface IAppView extends ISeatView, ILoginView {
    enum MenuOption {
        SEAT_BOOKING
    }

    MenuOption renderAppView(LoginService loginService, SeatService seatService);
}
