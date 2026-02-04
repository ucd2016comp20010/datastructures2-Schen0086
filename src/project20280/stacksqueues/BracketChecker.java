package project20280.stacksqueues;

import project20280.interfaces.Stack;

import java.util.Scanner;

class BracketChecker {
    private final String input;

    public BracketChecker(String in) {
        input = in;
    }

    // Checks the input string for balanced delimiters using a stack
    // Prints specific error messages as described in the question
    public void check() {

        // Use your stack implementation (ArrayStack or LinkedStack)
        Stack<Character> s = new ArrayStack<>();

        // Use a Scanner to read the string
        Scanner sc = new Scanner(input);

        // Read the entire line (so spaces are included too)
        String line = sc.hasNextLine() ? sc.nextLine() : "";

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            // If we see an opening delimiter, push it
            if (isLeftDelimiter(ch)) {
                s.push(ch);
            }

            // If we see a closing delimiter, we must match it with stack top
            else if (isRightDelimiter(ch)) {

                // If stack is empty, we have a missing left delimiter
                if (s.isEmpty()) {
                    System.out.println("Missing left delimiter before '" + ch + "' at index " + i);
                    sc.close();
                    return;
                }

                // Pop the last opening delimiter
                char left = s.pop();

                // If it doesn't match the closing delimiter, report mismatch
                if (!isMatchingPair(left, ch)) {
                    System.out.println("Matching error: '" + left + "' does not match '" + ch + "' at index " + i);
                    sc.close();
                    return;
                }
            }

            // Ignore all other characters
        }

        // After processing everything, if stack not empty => missing right delimiter(s)
        if (!s.isEmpty()) {
            System.out.println("Missing right delimiter(s): not all opening delimiters were closed");
            sc.close();
            return;
        }

        // If we got here, everything matched correctly
        System.out.println("Balanced");
        sc.close();
    }

    // Returns true if c is one of: { [ (
    private boolean isLeftDelimiter(char c) {
        return c == '{' || c == '[' || c == '(';
    }

    // Returns true if c is one of: } ] )
    private boolean isRightDelimiter(char c) {
        return c == '}' || c == ']' || c == ')';
    }

    // Checks whether the opening and closing delimiters form a correct pair
    private boolean isMatchingPair(char left, char right) {
        return (left == '(' && right == ')')
                || (left == '[' && right == ']')
                || (left == '{' && right == '}');
    }

    public static void main(String[] args) {
        String[] inputs = {
                "{[()]}",
                "{[(])}",
                "{{[[(())]]}}",
                "][]][][[]][]][][[[",
                "(((abc))((d)))))",

                "[]]()()",       // not correct
                "c[d]",          // correct
                "a{b[c]d}e",     // correct
                "a{b(c]d}e",     // not correct
                "a[b{c}d]e}",    // not correct
                "a{b(c) "        // not correct
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
            System.out.println();
        }
    }
}
