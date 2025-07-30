package com.course.custom;

import com.course.custom.exception.InsufficientFundsException;
import com.course.custom.model.BankAccount;

public class CustomExceptionDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0);
        System.out.println("Initial balance: " + account.getBalance());

        performTransactions(account);
    }

    public static void performTransactions(BankAccount account) {
        try {
            System.out.println("\nAttempting to withdraw 500.0...");
            account.withdraw(500.0); // Success

            System.out.println("\nAttempting to withdraw 700.0...");
            account.withdraw(700.0); // Failure

        } catch (InsufficientFundsException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("You are short by: " + e.getDeficit());
        }
    }
}
