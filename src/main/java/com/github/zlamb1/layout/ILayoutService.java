package com.github.zlamb1.layout;

import java.awt.*;

public interface ILayoutService extends Iterable<SeatLayoutDescriptor> {
    int getRowGap(int row, SeatLayout.RowGapDirection direction);
    int getColGap(int col, SeatLayout.ColGapDirection direction);
    Insets getGaps(int row, int col);
}
