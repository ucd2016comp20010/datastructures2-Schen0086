package project20280.exercises;

import project20280.interfaces.Stack;
import project20280.stacksqueues.ArrayStack;

// Q4
public class Wk3BaseConverter {

    // Converts a decimal number to binary
    public static String convertToBinary(long decNum) {

        // Call the general base converter with base 2
        return convertToBase(decNum, 2);
    }


    // Converts a decimal number to any base between 2 and 36
    public static String convertToBase(long decNum, int base) {

        // Check for valid base
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("Base must be between 2 and 36");
        }

        // Special case: 0
        if (decNum == 0) {
            return "0";
        }

        // Handle negative numbers
        boolean negative = decNum < 0;
        long n = negative ? -decNum : decNum;

        // Stack to store digits
        Stack<Character> stack = new ArrayStack<>();

        // Repeated division algorithm
        while (n > 0) {

            // Get remainder
            int remainder = (int) (n % base);

            // Convert remainder to character
            stack.push(digitToChar(remainder));

            // Divide number by base
            n = n / base;
        }

        // Build final string by popping stack
        StringBuilder result = new StringBuilder();

        if (negative) {
            result.append("-");
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }


    // Converts a digit value to its character representation
    // 0-9 -> '0' to '9'
    // 10-35 -> 'A' to 'Z'
    private static char digitToChar(int digit) {

        if (digit >= 0 && digit <= 9) {
            return (char) ('0' + digit);
        }

        return (char) ('A' + (digit - 10));
    }
}
