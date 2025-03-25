package com.github.zlamb1;

public class Seat implements ISeatDescriptor {
    protected String name;
    protected int row, col;
    protected boolean window, aisle;
    protected Customer reservedBy;

    public Seat(String name, int row, int col, boolean window, boolean aisle) {
        this.name = name;
        this.row = row;
        this.col = col;
        this.window = window;
        this.aisle = aisle;
        reservedBy = null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return col;
    }

    @Override
    public boolean isWindowSeat() {
        return window;
    }

    @Override
    public boolean isAisleSeat() {
        return aisle;
    }

    @Override
    public boolean isReserved() {
        return reservedBy != null;
    }

    @Override
    public Customer getReserver() {
        return reservedBy;
    }

    public void reserve(Customer c) {
        reservedBy = c;
    }
}
