package com.github.zlamb1;

public class PasswordValidator implements IPasswordValidator {
    protected int minimumLength = 4;
    protected int maximumLength = 50;

    @Override
    public int getMinimumLength() {
        return minimumLength;
    }

    @Override
    public int getMaximumLength() {
        return maximumLength;
    }

    @Override
    public boolean isValid(String password) {
        return password != null && password.length() >= minimumLength && password.length() <= maximumLength;
    }

    @Override
    public String getHint(String password) {
        if (password == null || password.isEmpty())
            return "Password is required.";
        else if (password.length() < minimumLength)
            return "Password must be at least " + minimumLength + " characters long.";
        else if (password.length() > maximumLength)
            return "Password must be less than " + (maximumLength + 1) + " characters long.";
        else return null;
    }
}
