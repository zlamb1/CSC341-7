package com.github.zlamb1.layout;

import com.github.zlamb1.ISeatDescriptor;

public class SeatLayoutDescriptor {
    protected int row, col;
    protected ISeatDescriptor seatDescriptor;

    public SeatLayoutDescriptor(int row, int col, ISeatDescriptor seatDescriptor) {
        this.row = row;
        this.col = col;
        this.seatDescriptor = seatDescriptor;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return col;
    }

    public boolean hasDescriptor() {
        return seatDescriptor != null;
    }

    public ISeatDescriptor getSeatDescriptor() {
        return seatDescriptor;
    }
}
