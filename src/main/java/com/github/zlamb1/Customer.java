package com.github.zlamb1;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Customer {
    protected String name;
    protected int privilege;
    protected Hash hash;

    public Customer(String name, int privilege, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this(name, privilege, HashService.createHash(password));
    }

    public Customer(String name, int privilege, Hash hash) {
        this.name = name;
        this.privilege = privilege;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public int getPrivilege() {
        return privilege;
    }

    public Hash getHash() {
        return hash;
    }

    public boolean comparePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return HashService.compareHash(hash, password);
    }

    public void changePassword(String newPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        hash = HashService.createHash(newPassword);
    }
}
