package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * HelloWorldPlus - A simple Java 21 application demonstrating Maven build with JUnit 5
 */
public class HelloWorldPlus {

    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        log("Application started");
        System.out.println("Hello World Plus!");
        HelloWorldPlus app = new HelloWorldPlus();
        System.out.println("Dummy Method 1: " + app.getGreeting());
        System.out.println("Dummy Method 2: " + app.calculateSum(5, 10));
        System.out.println("Dummy Method 3: " + app.reverseString("Java21"));
        log("Application ended");
    }

    private static void log(String message) {
        System.out.println("[" + LocalDateTime.now().format(TIME_FORMATTER) + "] " + message);
    }

    /**
     * Dummy method 1: Returns a greeting message
     */
    public String getGreeting() {
        return "Welcome to HelloWorldPlus - Production Ready!";
    }

    /**
     * Dummy method 2: Calculates the sum of two integers
     */
    public int calculateSum(int a, int b) {
        if (a < -1000000 || a > 1000000 || b < -1000000 || b > 1000000) {
            throw new IllegalArgumentException("Numbers must be within valid range");
        }
        return a + b;
    }

    /**
     * Dummy method 3: Reverses a string with null safety
     */
    public String reverseString(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return new StringBuilder(input).reverse().toString();
    }
}
