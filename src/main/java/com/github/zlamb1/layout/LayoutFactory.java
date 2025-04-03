package com.github.zlamb1.layout;

import com.github.zlamb1.Seat;

public class LayoutFactory {
    public static SeatLayout createStandardLayout() {
        SeatLayout seatLayout = new SeatLayout(6, 24);

        for (int r = 0; r < seatLayout.getRows(); r++) {
            for (int c = 0; c < seatLayout.getCols(); c++) {
                Seat seat = new Seat("" + (c + 14) + (char) ('A' + r), r, c, r == 0 || r == (seatLayout.getRows() - 1), r == 2 || r == 3);
                seatLayout.setSeat(r, c, seat);
            }
        }

        seatLayout.addColGap(3, SeatLayout.ColGapDirection.WEST, 25);
        seatLayout.addRowGap(3, SeatLayout.RowGapDirection.NORTH, 25);
        return seatLayout;
    }
}
