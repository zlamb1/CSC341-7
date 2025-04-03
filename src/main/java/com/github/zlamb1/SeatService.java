package com.github.zlamb1;

import com.github.zlamb1.layout.ILayoutService;
import com.github.zlamb1.layout.LayoutService;
import com.github.zlamb1.layout.SeatLayout;
import com.github.zlamb1.layout.SeatLayoutDescriptor;

import java.util.*;

public class SeatService {
    protected SeatLayout seatLayout;
    protected ILayoutService layoutService;
    protected LoginService loginService;

    protected Map<String, List<ISeatDescriptor>> customerSeats = new HashMap<>();

    public enum BookResult {
        SUCCESS,
        INVALID_CUSTOMER,
        INVALID_SEAT,
        SEAT_UNAVAILABLE,
        SEAT_AVAILABLE,
        INSUFFICIENT_PRIVILEGE
    }

    public SeatService(SeatLayout seatLayout, LoginService loginService) {
        this.seatLayout = seatLayout;
        this.layoutService = new LayoutService(seatLayout);
        this.loginService = loginService;

        // generate mapping from customers to seats
        for (SeatLayoutDescriptor seatLayoutDescriptor : layoutService) {
            ISeatDescriptor seatDescriptor;
            if (seatLayoutDescriptor.hasDescriptor() && (seatDescriptor = seatLayoutDescriptor.getSeatDescriptor()).isReserved()) {
                Customer customer = seatDescriptor.getReserver();
                List<ISeatDescriptor> seatDescriptors = customerSeats.computeIfAbsent(customer.getName(), k -> new ArrayList<>());
                seatDescriptors.add(seatDescriptor);
            }
        }
    }

    public BookResult bookSeat(Customer customer, ISeatDescriptor seatDescriptor) {
        if (customer == null)
            return BookResult.INVALID_CUSTOMER;
        if (!inBounds(seatDescriptor))
            return BookResult.INVALID_SEAT;
        Seat seat = seatLayout.getSeat(seatDescriptor.getRow(), seatDescriptor.getColumn());
        if (seat == null)
            return BookResult.INVALID_SEAT;
        if (seat.isReserved())
            return BookResult.SEAT_UNAVAILABLE;
        seat.reserve(customer);
        return BookResult.SUCCESS;
    }

    public BookResult bookSeat(ISeatDescriptor seatDescriptor) {
        return bookSeat(loginService.getLoggedIn(), seatDescriptor);
    }

    public BookResult cancelSeat(ISeatDescriptor seatDescriptor) {
        if (!inBounds(seatDescriptor))
            return BookResult.INVALID_SEAT;
        Seat seat = seatLayout.getSeat(seatDescriptor.getRow(), seatDescriptor.getColumn());
        if (seat == null)
            return BookResult.INVALID_SEAT;
        if (!seat.isReserved())
            return BookResult.SEAT_AVAILABLE;
        if (seat.getReserver() != loginService.getLoggedIn() && !isPrivilegedUser())
            return BookResult.INSUFFICIENT_PRIVILEGE;
        seat.reserve(null);
        return BookResult.SUCCESS;
    }

    public Collection<ISeatDescriptor> getCustomerSeats(Customer customer) {
        return customerSeats.get(customer.getName());
    }

    public ILayoutService getLayoutService() {
        return layoutService;
    }

    protected boolean isPrivilegedUser() {
        return loginService.isLoggedIn() && loginService.getLoggedIn().getPrivilege() > 0;
    }

    protected boolean inBounds(ISeatDescriptor seat) {
        return inBounds(seat.getRow(), seat.getColumn());
    }

    protected boolean inBounds(int row, int col) {
        return row >= 0 && row < seatLayout.getRows() && col >= 0 && col < seatLayout.getCols();
    }
}
