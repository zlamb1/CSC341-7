package com.github.zlamb1.view;

import com.github.zlamb1.ISeatDescriptor;
import com.github.zlamb1.LoginService;
import com.github.zlamb1.SeatService;

import java.util.Collection;

public interface ISeatView {
    Collection<ISeatDescriptor> renderSeatView(LoginService loginService, SeatService seatService);
    void renderLayoutView();
}
