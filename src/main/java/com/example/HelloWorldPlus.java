package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HelloWorldPlus is a simple Java 21 application that demonstrates a Maven
 * build and a JUnit 5 test.
 * 
 * <p>This class is used as a minimal example for a git merge study.</p>
 * 
 * @author Wilbert Valverde Barrantes
 * @version 1.0
 * @since 2025-11-06
 */
public class HelloWorldPlus {

  /**
   * Formatter used to render log timestamps.
   */
  private static final DateTimeFormatter TIME_FORMATTER = 
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private static final Logger LOGGER = Logger.getLogger(HelloWorldPlus.class.getName());

  /**
   * Constructs a new HelloWorldPlus object.
   * The object is initialized to a default state.
   */
  public HelloWorldPlus() {
    // Constructor body (can be empty or perform initialization)
  }

  /**
   * The main method is the entry point of this application. It prints a
   * greeting message to the console.
   *
   * @param args Command line arguments (not used in this example).
   */
  public static void main(final String[] args) {
    log("Application started");
    LOGGER.log(Level.INFO, () -> "Hello World Plus!");
    final HelloWorldPlus app = new HelloWorldPlus();
    LOGGER.log(Level.INFO, () -> "Dummy Method 1: " + app.getGreeting());
    LOGGER.log(Level.INFO, () -> "Dummy Method 2: " + app.calculateSum(5, 10));
    LOGGER.log(Level.INFO, () -> "Dummy Method 3: " + app.reverseString("Java21"));
    log("Application ended");
  }

  /**
   * Prints a simple timestamped log message to standard output.
   *
   * @param message the message to log
   */
  private static void log(final String message) {
    LOGGER.log(
        Level.INFO,
        () -> "[" + LocalDateTime.now().format(TIME_FORMATTER) + "] " + message);
  }

  /**
   * Returns a greeting message.
   *
   * @return the greeting message
   */
  public String getGreeting() {
    return "Welcome to HelloWorldPlus!";
  }

  /**
   * Calculates the sum of two integers.
   *
   * @param a first number to be added
   * @param b second number to be added
   * @return the sum of the two numbers
   * @throws IllegalArgumentException if either argument is outside the allowed
   *                                  range of -1_000_000..1_000_000
   */
  public int calculateSum(final int a, final int b) {
    if (a < -1_000_000 || a > 1_000_000 || b < -1_000_000 || b > 1_000_000) {
      throw new IllegalArgumentException("Numbers must be within valid range");
    }
    return a + b;
  }

  /**
   * Reverses a string, preserving {@code null} and empty inputs.
   *
   * @param input the string input to be reversed
   * @return the reversed string, or the original input if it is {@code null}
   *         or empty
   */
  public String reverseString(final String input) {
    if (input == null || input.isEmpty()) {
      return input;
    }
    return new StringBuilder(input).reverse().toString();
  }

}
