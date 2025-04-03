package com.github.zlamb1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Seat implements ISeatDescriptor {
    protected String name;
    protected int type, row, col;
    protected boolean window, aisle;
    protected Customer reservedBy;

    protected PropertyChangeSupport propertyChangeSupport;

    public Seat(String name, int row, int col, boolean window, boolean aisle) {
        this.name = name;
        this.type = 0;
        this.row = row;
        this.col = col;
        this.window = window;
        this.aisle = aisle;
        this.reservedBy = null;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getType() {
        return type;
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
        Customer oldReservedBy = reservedBy;
        reservedBy = c;
        propertyChangeSupport.firePropertyChange("reservedBy", oldReservedBy, c);
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }
}
