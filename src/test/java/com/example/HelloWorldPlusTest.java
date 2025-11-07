package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HelloWorldPlus Tests")
class HelloWorldPlusTest {

    private final HelloWorldPlus app = new HelloWorldPlus();

    @Test
    @DisplayName("Test getGreeting method")
    void testGetGreeting() {
        String result = app.getGreeting();
        assertEquals("Welcome to HelloWorldPlus!", result);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test calculateSum method")
    void testCalculateSum() {
        int result = app.calculateSum(5, 10);
        assertEquals(15, result);
    }

    @Test
    @DisplayName("Test calculateSum with negative numbers")
    void testCalculateSumNegative() {
        int result = app.calculateSum(-5, 10);
        assertEquals(5, result);
    }

    @Test
    @DisplayName("Test reverseString method")
    void testReverseString() {
        String result = app.reverseString("Java21");
        assertEquals("12avaJ", result);
    }

    @Test
    @DisplayName("Test reverseString with empty string")
    void testReverseStringEmpty() {
        String result = app.reverseString("");
        assertEquals("", result);
    }

    @Test
    @DisplayName("Test reverseString with null safety")
    void testReverseStringNullSafety() {
        String result = app.reverseString(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Verify new feature logging does not affect functionality")
    void testLoggingIntegration() {
        String result = app.getGreeting();
        assertNotNull(result);
        assertTrue(result.contains("Welcome"));
    }
}
