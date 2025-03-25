package com.github.zlamb1.view;

import com.github.zlamb1.ISeatDescriptor;
import com.github.zlamb1.LoginService;

import java.util.Collection;

public interface ISeatView {
    Collection<ISeatDescriptor> renderSeatView(LoginService loginService, Collection<ISeatDescriptor> seats);
}
