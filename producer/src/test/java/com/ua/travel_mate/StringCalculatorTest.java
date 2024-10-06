package com.ua.travel_mate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class StringCalculatorTest {
    private final StringCalculator calculator = new StringCalculator();

    @Test
    void simpleExampleTest() {
        int a = 10;
        int b = 20;
        int c = a + b;
        assertEquals(30, c);
        assertNotEquals(31, c);
    }

    @Test
    void whenWePassZeroWeGetZero() {
        StringCalculator sc = new StringCalculator();
        int result = sc.add("0");
        assertEquals(0, result);
    }

    @Test
    void whenWePassOneWeGetOne() {
        StringCalculator sc = new StringCalculator();
        int result = sc.add("1");
        assertEquals(1, result);
    }

    @Test
    void whenWePassFiveWeGetFive() {
        StringCalculator sc = new StringCalculator();
        int result = sc.add("5");
        assertEquals(5, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2", "4", "6"})
    void isOdd_ShouldReturnTrueForOddNumbers(String number) {
        StringCalculator sc = new StringCalculator();
        int result = sc.add(number);
        assertEquals(0, result % 2);
    }

    // Kata
    @Test
    void testAdd_EmptyString_ReturnsZero() {
        assertEquals(0, calculator.add(""));
    }

    @Test
    void testAdd_SingleNumber_ReturnsNumber() {
        assertEquals(1, calculator.add("1"));
    }

    @Test
    void testAdd_TwoNumbers_ReturnsSum() {
        assertEquals(3, calculator.add("1,2"));
    }

    @Test
    void testAdd_NullString_ReturnsZero() {
        assertEquals(0, calculator.add(null));
    }

    @Test
    void testAdd_MultipleNumbers_ReturnsSum() {
        assertEquals(6, calculator.add("1,2,3"));
        assertEquals(10, calculator.add("1,2,3,4"));
        assertEquals(15, calculator.add("1,2,3,4,5"));
    }

    @Test
    void testAdd_NumbersWithNewLines_ReturnsSum() {
        assertEquals(6, calculator.add("1\n2,3"));
        assertEquals(10, calculator.add("1,2\n3,4"));
        assertEquals(15, calculator.add("1\n,2\n,3\n,4,5"));
    }

    @Test
    void testAdd_NegativeNumber_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.add("1,-2,3");
        });
        assertEquals("Negatives not allowed: [-2]", exception.getMessage());
    }

    @Test
    void testAdd_MultipleNegatives_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.add("1,-2,-3");
        });
        assertEquals("Negatives not allowed: [-2, -3]", exception.getMessage());
    }

    @Test
    void testAdd_NumbersGreaterThan1000_Ignored() {
        assertEquals(2, calculator.add("2,1001"));
        assertEquals(2, calculator.add("1001,2"));
        assertEquals(4, calculator.add("1001,2,1000,2"));
    }

    @Test
    void testAdd_Delimiter() {
        assertEquals(3, calculator.add("//;\n1;2"));
    }

    @Test
    void testAdd_DelimiterAnySize() {
        assertEquals(6, calculator.add("//[***]\n1***2***3"));
    }

    @Test
    void testAdd_MultipleDelimiter() {
        assertEquals(6, calculator.add("//[*][%]\n1*2%3"));
    }

    @Test
    void testAdd_MultipleDelimiterAnySize() {
        assertEquals(6, calculator.add("//[*][%&]\n1*2%&3"));
    }
}
