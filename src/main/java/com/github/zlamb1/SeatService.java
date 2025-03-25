package com.github.zlamb1;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SeatService {
    protected Map<String, Seat> seats;
    protected LoginService loginService;

    public enum BookResult {
        SUCCESS,
        INVALID_CUSTOMER,
        INVALID_SEAT,
        SEAT_UNAVAILABLE,
        SEAT_AVAILABLE,
        INSUFFICIENT_PRIVILEGE
    }

    public SeatService(Collection<Seat> seats, LoginService loginService) {
        this.seats = new HashMap<>();
        this.loginService = loginService;

        for (Seat seat : seats) {
            this.seats.put(seat.getName(), seat);
        }
    }

    public Collection<ISeatDescriptor> getSeats() {
        return Collections.unmodifiableCollection(seats.values());
    }

    public BookResult bookSeat(Customer customer, ISeatDescriptor seat) {
        if (customer == null)
            return BookResult.INVALID_CUSTOMER;
        if (!seats.containsKey(seat.getName()))
            return BookResult.INVALID_SEAT;
        Seat s = seats.get(seat.getName());
        if (s.isReserved())
            return BookResult.SEAT_UNAVAILABLE;
        s.reserve(customer);
        return BookResult.SUCCESS;
    }

    public BookResult bookSeat(ISeatDescriptor seat) {
        return bookSeat(loginService.getLoggedIn(), seat);
    }

    public BookResult cancelSeat(ISeatDescriptor seat) {
        if (!seats.containsKey(seat.getName()))
            return BookResult.INVALID_SEAT;
        Seat s = seats.get(seat.getName());
        if (!s.isReserved())
            return BookResult.SEAT_AVAILABLE;
        if (s.getReserver() != loginService.getLoggedIn() && !isPrivilegedUser())
            return BookResult.INSUFFICIENT_PRIVILEGE;
        s.reserve(null);
        return BookResult.SUCCESS;
    }

    protected boolean isPrivilegedUser() {
        return loginService.isLoggedIn() && loginService.getLoggedIn().getPrivilege() > 0;
    }
}
