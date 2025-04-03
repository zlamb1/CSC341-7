package com.github.zlamb1;

import java.beans.PropertyChangeListener;

public interface ISeatDescriptor {
    String getName();
    int getType();
    int getRow();
    int getColumn();
    boolean isWindowSeat();
    boolean isAisleSeat();
    boolean isReserved();
    Customer getReserver();

    void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);
    void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);
}
