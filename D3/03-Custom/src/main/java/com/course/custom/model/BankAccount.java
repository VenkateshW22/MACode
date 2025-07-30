package com.course.custom.model;

import com.course.custom.exception.InsufficientFundsException;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Withdrawal amount exceeds balance.", amount - balance);
        }
        balance -= amount;
        System.out.println("Withdrawal successful. New balance: " + balance);
    }

    public double getBalance() {
        return balance;
    }
}
