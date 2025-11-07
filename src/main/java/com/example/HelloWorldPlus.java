package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * HelloWorldPlus - A simple Java 21 application demonstrating Maven build with JUnit 5
 * It represents a Hello world plus class.
 * <p>
 * This is test class for git merge study.
 * </p>
 *
 * @author Wilbert Valverde Barrantes
 * @version 1.0
 * @since 2025-11-06
 */
public class HelloWorldPlus {
    
    /**
     * A simple string field for demonstration purposes.
     * This field will be initialized to {@code null} by the default constructor.
     */

    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * The main method is the entry point of this application.
     * It prints a greeting message to the console.
     *
     * @param args Command line arguments (not used in this example).
     */
    public static void main(String[] args) {
        log("Application started");
        System.out.println("Hello World Plus!");
        HelloWorldPlus app = new HelloWorldPlus();
        System.out.println("Dummy Method 1: " + app.getGreeting());
        System.out.println("Dummy Method 2: " + app.calculateSum(5, 10));
        System.out.println("Dummy Method 3: " + app.reverseString("Java21"));
        log("Application ended");
    }
    
    /**
     * New log method: gives log feature
     */
    private static void log(String message) {
        System.out.println("[" + LocalDateTime.now().format(TIME_FORMATTER) + "] " + message);
    }

    /**
     * Dummy method 1: Returns a greeting message
     * 
     * @return the greeting message.
     */
    public String getGreeting() {
        return "Welcome to HelloWorldPlus!";
    }

    /**
     * Dummy method 2: Calculates the sum of two integers
     * 
     * @param a First number to be added.
     * @param b Second number to be added.
     * 
     * @return the sum of the two numbers.
     */
    public int calculateSum(int a, int b) {
        if (a < -1000000 || a > 1000000 || b < -1000000 || b > 1000000) {
            throw new IllegalArgumentException("Numbers must be within valid range!");
        }
        return a + b;
    }

    /**
     * Dummy method 3: Reverses a string with null safety
     * 
     * @param input The string input to be reverse.
     * 
     * @return the reverse string.
     */
    public String reverseString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return new StringBuilder(input).reverse().toString();
    }
}
