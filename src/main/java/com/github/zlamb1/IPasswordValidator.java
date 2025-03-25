package com.github.zlamb1;

public interface IPasswordValidator {
    int getMinimumLength();
    int getMaximumLength();

    boolean isValid(String password);
    String getHint(String password);
}
