package com.github.zlamb1.layout;

import com.github.zlamb1.ISeatDescriptor;

import java.awt.*;
import java.util.Iterator;
import java.util.function.Consumer;

public class LayoutService implements ILayoutService {
    protected SeatLayout seatLayout;

    public static class SeatLayoutDescriptorIterator implements Iterator<SeatLayoutDescriptor> {
        protected int idx;
        protected SeatLayout seatLayout;

        protected Order order;

        public enum Order {
            RowOrder,
            ColumnOrder
        }

        public SeatLayoutDescriptorIterator(SeatLayout seatLayout) {
            this(seatLayout, Order.ColumnOrder);
        }

        public SeatLayoutDescriptorIterator(SeatLayout seatLayout, Order order) {
            this.idx = 0;
            this.seatLayout = seatLayout;
            this.order = order;
        }

        @Override
        public boolean hasNext() {
            return idx < (seatLayout.getRows() * seatLayout.getCols());
        }

        @Override
        public SeatLayoutDescriptor next() {
            // FIXME: implement ordering
            int row = idx / seatLayout.getCols();
            int col = idx % seatLayout.getCols();
            ISeatDescriptor seatDescriptor = seatLayout.getSeat(row, col);
            idx++;
            return new SeatLayoutDescriptor(row, col, seatDescriptor);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void forEachRemaining(Consumer<? super SeatLayoutDescriptor> action) {
            throw new UnsupportedOperationException();
        }
    }

    public LayoutService(SeatLayout seatLayout) {
        this.seatLayout = seatLayout;
    }

    public void setSeatLayout(SeatLayout seatLayout) {
        this.seatLayout = seatLayout;
    }

    @Override
    public int getRowGap(int row, SeatLayout.RowGapDirection direction) {
        return seatLayout.getRowGap(row, direction);
    }

    @Override
    public int getColGap(int col, SeatLayout.ColGapDirection direction) {
        return seatLayout.getColGap(col, direction);
    }

    @Override
    public Insets getGaps(int row, int col) {
        return new Insets(
            seatLayout.getRowGap(row, SeatLayout.RowGapDirection.NORTH),
            seatLayout.getColGap(col, SeatLayout.ColGapDirection.WEST),
            seatLayout.getRowGap(row, SeatLayout.RowGapDirection.SOUTH),
            seatLayout.getColGap(col, SeatLayout.ColGapDirection.EAST)
        );
    }

    @Override
    public Iterator<SeatLayoutDescriptor> iterator() {
        return new SeatLayoutDescriptorIterator(seatLayout);
    }

    public Iterator<SeatLayoutDescriptor> iterator(SeatLayoutDescriptorIterator.Order order) {
        return new SeatLayoutDescriptorIterator(seatLayout, order);
    }
}
