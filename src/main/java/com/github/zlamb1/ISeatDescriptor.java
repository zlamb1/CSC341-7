package com.github.zlamb1;

public interface ISeatDescriptor {
    String getName();
    int getRow();
    int getColumn();
    boolean isWindowSeat();
    boolean isAisleSeat();
    boolean isReserved();
    Customer getReserver();
}
