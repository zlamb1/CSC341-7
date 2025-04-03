package com.github.zlamb1.view;

import com.github.zlamb1.ISeatDescriptor;
import com.github.zlamb1.LoginService;
import com.github.zlamb1.SeatService;

import java.util.Collection;

public interface IAppView extends ISeatView, ILoginView {
    enum MenuOption {
        SEAT_BOOKING
    }

    Collection<ISeatDescriptor> renderAppView(LoginService loginService, SeatService seatService);
}
