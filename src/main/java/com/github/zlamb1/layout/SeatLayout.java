package com.github.zlamb1.layout;

import com.github.zlamb1.Seat;

import java.beans.PropertyChangeSupport;

public class SeatLayout {
    protected int rows, cols;
    protected Seat[][] seats;

    public enum RowGapDirection {
        NORTH, SOUTH
    }

    public enum ColGapDirection {
        WEST, EAST
    }

    protected int[][] rowGaps;
    protected int[][] colGaps;

    protected PropertyChangeSupport propertyChangeSupport;

    public SeatLayout() {
        this(6, 24);
    }

    public SeatLayout(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        this.seats = new Seat[rows][cols];
        this.rowGaps = new int[rows][2];
        this.colGaps = new int[cols][2];

        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Seat getSeat(int row, int col) {
        return seats[row][col];
    }

    public void setSeat(int row, int col, Seat seat) {
        seats[row][col] = seat;
    }

    public void removeSeat(int row, int col) {
        seats[row][col] = null;
    }

    public int getRowGap(int row, RowGapDirection direction) {
        return switch (direction) {
            case NORTH -> rowGaps[row][0];
            case SOUTH -> rowGaps[row][1];
        };
    }

    public int[] getRowGaps(int row) {
        return rowGaps[row];
    }

    public int getColGap(int col, ColGapDirection direction) {
        return switch (direction) {
            case WEST -> colGaps[col][0];
            case EAST -> colGaps[col][1];
        };
    }

    public int[] getColGaps(int col) {
        return colGaps[col];
    }

    public void addRowGap(int row, RowGapDirection direction, int gap) {
        switch (direction) {
            case NORTH -> rowGaps[row][0] = gap;
            case SOUTH -> rowGaps[row][1] = gap;
        }
        onChangeRowGaps();
    }

    public void removeRowGap(int row, RowGapDirection direction) {
        switch (direction) {
            case NORTH -> rowGaps[row][0] = 0;
            case SOUTH -> rowGaps[row][1] = 0;
        }
        onChangeRowGaps();
    }

    public void removeRowGap(int row) {
        rowGaps[row][0] = 0;
        rowGaps[row][1] = 0;
        onChangeRowGaps();
    }

    public void addColGap(int col, ColGapDirection direction, int gap) {
        switch (direction) {
            case WEST -> colGaps[col][0] = gap;
            case EAST -> colGaps[col][1] = gap;
        }
        onChangeColGaps();
    }

    public void removeColGap(int col, ColGapDirection direction) {
        switch (direction) {
            case WEST -> colGaps[col][0] = 0;
            case EAST -> colGaps[col][1] = 0;
        }
        onChangeColGaps();
    }

    public void removeColGap(int col) {
        colGaps[col][0] = 0;
        colGaps[col][1] = 0;
        onChangeColGaps();
    }

    protected void onChangeRowGaps() {
        propertyChangeSupport.firePropertyChange("rowGaps", rowGaps, rowGaps);
    }

    protected void onChangeColGaps() {
        propertyChangeSupport.firePropertyChange("colGaps", colGaps, colGaps);
    }
}
