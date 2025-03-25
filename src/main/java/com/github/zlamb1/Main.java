package com.github.zlamb1;

import com.github.zlamb1.io.IAppSerializer;
import com.github.zlamb1.io.JSONAppSerializer;
import com.github.zlamb1.view.AppView;
import com.github.zlamb1.view.IAppView;

public class Main {
    public static void main(String[] args) {
        IAppView appView = new AppView();
        IAppSerializer appSerializer = new JSONAppSerializer();

        SeatApp app = new SeatApp(appView, appSerializer);
        app.startApp();
    }
}