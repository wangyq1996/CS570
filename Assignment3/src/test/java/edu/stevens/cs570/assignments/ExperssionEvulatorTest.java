package edu.stevens.cs570.assignments;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {

    @org.junit.jupiter.api.Test

    void evaluate() {

        assertEquals(20, new ExpressionEvaluator("11+3**2").evaluate());
        assertEquals(0.140226856440992, new ExpressionEvaluator("(12.34+34.56*0.66)**0.8/123").evaluate());
        assertEquals(0.5, new ExpressionEvaluator("0.0+0.4").evaluate());
        assertEquals(-3.7512257614709226, new ExpressionEvaluator("(1.234**(+12.3)/(-3.54))").evaluate());
        assertEquals(2.4178516392292583E24, new ExpressionEvaluator("2**3**4").evaluate());
        assertThrows(IllegalArgumentException.class, () -> {
            // cannot divide by 0
            Double res = new ExpressionEvaluator("100/(5-5)").evaluate();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // cannot divide by 0
            Double res = new ExpressionEvaluator("123*32/0").evaluate();
        });
        assertThrows(IllegalArgumentException.class, () -> {
            // Illegal number
            Double res = new ExpressionEvaluator("(1.2.3+4)*4/2").evaluate();
        });

    }

    @org.junit.jupiter.api.Test

    void ExpressionEvaluator() {
        assertThrows(IllegalArgumentException.class, () -> {
            // unbalanced parentheses
            ExpressionEvaluator test = new ExpressionEvaluator("100 * (   ((2.5   ** (3.4 + 3.2)) ** 2 )");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // unbalanced parentheses 1
            ExpressionEvaluator test = new ExpressionEvaluator("((123*44)-2");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // illegal character appear m
            ExpressionEvaluator test = new ExpressionEvaluator("123*4r4-2");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            // Digit is between inside ()
            ExpressionEvaluator test = new ExpressionEvaluator("100 * (   (2.5   (**) (3.4 + 3.2)) ** 2 )");
        });

    }

}
