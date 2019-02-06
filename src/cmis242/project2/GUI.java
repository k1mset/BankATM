/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmis242.project2;

/**
 * File: GUI.java
 * Date: 4/4/2018
 * Author: Dillan Cobb
 * Purpose: Creates the GUI and constructs the GUI for the user. Also contains
 * the action events for the buttons and GUI for the user to be able to access
 * his/her bank account.
 */

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {
    // static ints for the size of the GUI
    static final int WIDTH = 350, HEIGHT = 250;
    
    // Default GUI constructor for the frame of the ATM
    GUI() {
        super("ATM Machine");
        setFrame(WIDTH, HEIGHT);
        add(new GuiPanel());
    }
    
    static public void main(String[] args) {
        // Constructs the GUI frame and displays it
        GUI atmApp = new GUI();
        atmApp.display();
    }
    
    // display method allows the GUI frame to be displayed
    private void display() {
        setVisible(true);
    }
    
    // setFrame method changes the size of the GUI frame to the user, as well
    // as centers the frame to the users screen.
    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

// GuiPanel class used for creating the GUI
class GuiPanel extends JPanel {
    // Constructing all the buttons, radiobuttons, textfields, and optionpanes
    // for use in the GUI
    private JButton withDrawBtn = new JButton("Withdraw");
    private JButton depositBtn = new JButton("Deposit");
    private JButton transferToBtn = new JButton("Transfer to");
    private JButton balanceBtn = new JButton("Balance");
    private JRadioButton checkingRadioBtn = new JRadioButton("Checking", true);
    private JRadioButton savingsRadioBtn = new JRadioButton("Savings");
    private JTextField amountTxt = new JTextField("");
    private JOptionPane msgPane = new JOptionPane();
    
    // Constructs the checking and savings account for the ATM.
    private Account checkingAccount = new Account();
    private Account savingsAccount = new Account();
    
    // Variavles to be used within the atm
    private double transAmt;
    private double withdrawCheck;
    private double balance;
    
    public GuiPanel() {
        // Sets the gui layout and background color
        setLayout(new BorderLayout());
        setBackground(Color.lightGray);
        
        // Creates the top panel used to hold the buttons of Withdraw, Deposit,
        // Transfer and Balance
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 15, 30));
        btnPanel.setLayout(new GridLayout(2, 2, 4, 4));
        btnPanel.add(withDrawBtn);
        btnPanel.add(depositBtn);
        btnPanel.add(transferToBtn);
        btnPanel.add(balanceBtn);
        
        // Creates the center panel used for the radio buttons for each bank
        // account, contains Checking and Savings radio buttons
        JPanel radioBtnPanel = new JPanel();
        radioBtnPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        radioBtnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        radioBtnPanel.add(checkingRadioBtn);
        radioBtnPanel.add(savingsRadioBtn);
        
        // Creates the bottom panel that holds the textfield for entering in your
        // amount with the ATM
        JPanel txtPanel = new JPanel();
        txtPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        txtPanel.setLayout(new GridLayout(1, 1));
        txtPanel.add(amountTxt);
        
        // Adds all the panels together on the frame
        add(btnPanel, BorderLayout.NORTH);
        add(radioBtnPanel, BorderLayout.CENTER);
        add(txtPanel, BorderLayout.SOUTH);
        
        // Action listener that controls what happens when the Withdraw button
        // is clicked.
        withDrawBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    transAmt = getTransAmt();
                    // Modulus is ran to see if the amount from division of the
                    // withdraw amount ends up with a remainder of 0
                    withdrawCheck = transAmt % 20;
                    // if remainder is not 0, throw exception and inform user
                    // their withdraw could not occur
                    if (withdrawCheck != 0.0) {
                        msgPane.showMessageDialog(null, "Withdraw amount must "
                                + "be in increments of $20. Please try again.", 
                                "Withdraw Error", JOptionPane.ERROR_MESSAGE
                                ); 
                        throw new InsufficentFundsException("Withdraw amount "
                                + "was not an increment of 20.");
                    }
                    // if remainder is 0 then continue with withdraw of money
                    else if (withdrawCheck == 0.0) {
                        // Withdraw for Savings Account
                        if (savingsRadioBtn.isSelected()) {
                            balance = savingsAccount.getBalance();
                            if (balance >= transAmt) {
                                msgPane.showMessageDialog(null, "An amount of $"
                                        + transAmt + " was withdrawn from your "
                                        + "Savings Account.", "Withdraw"
                                        + " Information", 
                                        JOptionPane.INFORMATION_MESSAGE);
                                savingsAccount.withdraw(transAmt);
                            }
                            else {
                                // if account balance is not more then asked for,
                                // an error is displayed and an exception is
                                // thrown
                                msgPane.showMessageDialog(null, "Not enough "
                                        + "funds are in the Savings Account. "
                                        + "Please try again.", "Withdraw Error",
                                        JOptionPane.ERROR_MESSAGE);
                                throw new InsufficentFundsException("Not enough"
                                        + " money in the target account.");
                            }
                        }
                        // Withdraw for Checkings Account
                        else if (checkingRadioBtn.isSelected()) {
                            balance = checkingAccount.getBalance();
                            if (balance >= transAmt) {
                                msgPane.showMessageDialog(null, "An amount of $"
                                        + transAmt + " was withdrawn from your "
                                        + "Checkings Account.", "Withdraw"
                                        + " Information", 
                                        JOptionPane.INFORMATION_MESSAGE);
                                checkingAccount.withdraw(transAmt);
                            }
                            else {
                                // if account balance is not more then asked for,
                                // an error is displayed and an exception is
                                // thrown
                                msgPane.showMessageDialog(null, "Not enough "
                                        + "funds are in the Checking Account. "
                                        + "Please try again.", "Withdraw Error",
                                        JOptionPane.ERROR_MESSAGE);
                                throw new InsufficentFundsException("Not enough"
                                        + " money in the target account.");
                            }
                        }
                    }
                }
                catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                catch (InsufficentFundsException eh) {
                    eh.printStackTrace();
                }
            }
        });
        // Action Listener for when the deposit button is pressed
        depositBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               try {
                   transAmt = getTransAmt();
                   // For when depositing to Savings Account
                   if (savingsRadioBtn.isSelected()) {
                       savingsAccount.deposit(transAmt);
                       msgPane.showMessageDialog(null, "$" + transAmt + 
                               " was added to your Savings Account.", 
                               "Deposit Information", 
                               JOptionPane.INFORMATION_MESSAGE);
                   }
                   // For when depositing to Checking Account
                   else if (checkingRadioBtn.isSelected()) {
                       checkingAccount.deposit(transAmt);
                       msgPane.showMessageDialog(null, "$" + transAmt + 
                               " was added to your Checking Account.", 
                               "Deposit Information", 
                               JOptionPane.INFORMATION_MESSAGE);
                   }
               }
               // Catch exception for when the textfield is not a number
               catch (NumberFormatException ex) {
                   ex.printStackTrace();
               }
           } 
        });
        // ActionListener for when the Transfer button is pressed
        transferToBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               try {
                   transAmt = getTransAmt();
                   if (savingsRadioBtn.isSelected()) {
                       balance = checkingAccount.getBalance();
                       if (balance >= transAmt) {
                            checkingAccount.transferMoney(savingsAccount, transAmt);
                            msgPane.showMessageDialog(null, "The amount of $" + 
                                    transAmt + " was transfered from your "
                                    + "Checkings Account to your Savings "
                                    + "Account.", "Transfer Information", 
                                    JOptionPane.INFORMATION_MESSAGE);
                       }
                       // Exception thrown if the account the money is coming from
                       // does not have enough money in it
                       else {
                           msgPane.showMessageDialog(null, "Not enough money in"
                                   + " your Checking Account to continue the "
                                   + "transfer.", "Transfer Error", 
                                   JOptionPane.ERROR_MESSAGE);
                           throw new InsufficentFundsException("Not enough "
                                   + "money in account to transfer.");
                       }
                   }
                   else if (checkingRadioBtn.isSelected()) {
                       balance = savingsAccount.getBalance();
                       if (balance >= transAmt) {
                           savingsAccount.transferMoney(checkingAccount, transAmt);
                           msgPane.showMessageDialog(null, "The amount of $" + 
                                    transAmt + " was transfered from your "
                                    + "Savings Account to your Checking "
                                    + "Account.", "Transfer Information", 
                                    JOptionPane.INFORMATION_MESSAGE);
                       }
                       else {
                           // Exception thrown if the account the money is coming from
                       // does not have enough money in it
                           msgPane.showMessageDialog(null, "Not enough money in"
                                   + " your Savings Account to continue the "
                                   + "transfer.", "Transfer Error", 
                                   JOptionPane.ERROR_MESSAGE);
                           throw new InsufficentFundsException("Not enough "
                                   + "money in account to transfer.");
                       }
                   }
               }
               catch (NumberFormatException ex) {
                   ex.printStackTrace();
               }
               catch (InsufficentFundsException eh) {
                   eh.printStackTrace();
               }
           } 
        });
        // Actionlistener for the balance button
        balanceBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Displays the Savings Account balance
               if (savingsRadioBtn.isSelected()) {
                   balance = savingsAccount.getBalance();
                   msgPane.showMessageDialog(null, "Your Savings Account "
                           + "balance is: $" + balance, "Balance Information", 
                           JOptionPane.INFORMATION_MESSAGE);
               }
               // Displays the Checking Acount Balance
               else if (checkingRadioBtn.isSelected()) {
                   balance = checkingAccount.getBalance();
                   msgPane.showMessageDialog(null, "Your Checkings Account "
                           + "balance is: $" + balance, "Balance Information", 
                           JOptionPane.INFORMATION_MESSAGE);
               }
           } 
        });
        
        // ActionListener for the Checking account to make sure only one radio
        // button is selected at a time.
        checkingRadioBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (savingsRadioBtn.isSelected()) {
                   savingsRadioBtn.setSelected(false);
               }
           } 
        });
        
        // ActionListener for the Savings account to make sure only one radio
        // button is selected at a time.
        savingsRadioBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (checkingRadioBtn.isSelected()) {
                   checkingRadioBtn.setSelected(false);
               }
           } 
        });
    }
    
    // Returns the amount inside of the amountTxt textfield into a double.
    double getTransAmt() {
        return Double.parseDouble(amountTxt.getText());
    }
}
