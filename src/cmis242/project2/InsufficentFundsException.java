/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmis242.project2;

/**
 * File: InsufficentFundsExcpetion.java
 * Date: 4/4/2018
 * Author: Dillan Cobb
 * Purpose: Creates an exception that can be throw to the console in the event
 * of an error with funds. 
 */

// InsufficentFundsException class that extends Java's Exception class.
public class InsufficentFundsException extends Exception {
    
    // Allows a string 's' to be printed into the console on the event of being
    // thrown.
    InsufficentFundsException(String s) {
        super(s);
    }
    
}
