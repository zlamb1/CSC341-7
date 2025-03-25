package com.github.zlamb1;

import com.github.zlamb1.io.IAppSerializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    protected IAppSerializer appSerializer;

    protected Map<String, Customer> customers;
    protected Customer loggedIn;
    protected IPasswordValidator passwordValidator;

    public enum LoginResult {
        SUCCESS,
        WRONG_NAME,
        WRONG_PASSWORD,
        HASH_ERROR
    }

    public enum RegisterResult {
        SUCCESS,
        DUPLICATE_NAME,
        SHORT_PASSWORD,
        PASSWORD_COMPLEXITY,
        HASH_ERROR
    }

    LoginService(IAppSerializer appSerializer) {
        this.appSerializer = appSerializer;
        this.customers = new HashMap<>();
        loggedIn = null;
        passwordValidator = new PasswordValidator();
        for (Customer c : appSerializer.deserializeCustomers()) {
            this.customers.put(c.getName(), c);
        }
    }

    public Customer getLoggedIn() {
        return loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn != null;
    }

    public LoginResult login(String name, String password) {
        if (!customers.containsKey(name)) {
            return LoginResult.WRONG_NAME;
        }

        Customer c = customers.get(name);
        try {
            if (!c.comparePassword(password))
                return LoginResult.WRONG_PASSWORD;
        } catch (Exception exc) {
            return LoginResult.HASH_ERROR;
        }

        loggedIn = c;
        return LoginResult.SUCCESS;
    }

    public void logout() {
        loggedIn = null;
    }

    public RegisterResult register(String name, String password) {
        try {
            if (customers.containsKey(name))
                return RegisterResult.DUPLICATE_NAME;
            if (password.length() < 4)
                return RegisterResult.SHORT_PASSWORD;
            // FIXME: password complexity validation
            customers.put(name, new Customer(name, 0, password));
            appSerializer.serializeCustomers(customers.values());
            return RegisterResult.SUCCESS;
        } catch (Exception exc) {
            return RegisterResult.HASH_ERROR;
        }
    }

    public IPasswordValidator getPasswordValidator() {
        return passwordValidator;
    }
}
