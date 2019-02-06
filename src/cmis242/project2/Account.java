/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmis242.project2;

/**
 * File: Account.java
 * Date: 4/4/2018
 * Author: Dillan Cobb
 * Purpose: Contains a constructor for the 'Bank Account' of the ATM.
 * As well as contains methods for interacting with the account.
 */

public class Account {
    // Constructor variables
    private double accountBalance;
    private int chargeCount;
    private double chargeAmt = 1.50;
    
    // Default constructor
    public Account() {
        this.accountBalance = 0.0;
        this.chargeCount = 0;
    }
    
    // Constructor that allows an amount to be placed within its account
    public Account(double amount) {
        this.accountBalance = amount;
        this.chargeCount = 0;
    }
    
    // Withdraw method, pulls money from an account if it has enough money
    // in the account to pull.
    void withdraw(double withdrawAmt) {
        // Exception check if there is enough money in the account
        try {
            // totalWithdraw adds the withdrawAmt and the chargeAmt to ensure
            // there is enough money in the account in the event of a service
            // charge to the account.
            double totalWithdraw = withdrawAmt + chargeAmt;
            if (accountBalance >= totalWithdraw) {
                accountBalance -= withdrawAmt;
                if (chargeCount > 4) {
                    accountBalance -= chargeAmt;
                }
                chargeCount++;
            }
            else {
                // The amount in the account wasnt more than the amount asked
                // for + the service charge, so an exception is thrown.
                throw new InsufficentFundsException("Target account does not "
                        + "have sufficent funds for withdraw.");
            }
            
        } catch (InsufficentFundsException e) {
            e.printStackTrace();
        }
        
    }
    
    // setter method desposit method adds the depositAmt to the bank account
    void deposit(double depositAmt) {
        accountBalance += depositAmt;
    }
    
    // transferMoney method, had a target account that it pulls the amount from
    // the account and sends it to the target account
    void transferMoney(Account targetAccount, double amount) {
        if (accountBalance >= amount) {
            accountBalance -= amount;
            targetAccount.deposit(amount);
        }
    }
    
    // getBalance is a simple getter method that returns the balance of the 
    // account.
    double getBalance() {
        return accountBalance;
    }
}
