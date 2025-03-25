package com.github.zlamb1.io;

import com.github.zlamb1.Customer;
import com.github.zlamb1.Seat;

import java.util.Collection;

public interface IAppSerializer {
    void serializeCustomers(Collection<Customer> customer);
    void serializeSeats(Collection<Seat> seat);

    Collection<Customer> deserializeCustomers();
    Collection<Seat> deserializeSeats();
}
