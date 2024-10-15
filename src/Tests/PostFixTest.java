package Tests;

import ADTs.PostFix;
import Exceptions.InvalidSyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostFixTest {

    private PostFix postFix;

    @BeforeEach
    public void setUp() {
        postFix = new PostFix();
    }

    @Test
    public void testCalculateSimpleAddition() throws InvalidSyntaxException {
        String expression = "2 3 +";
        assertEquals(5, postFix.calculate(expression));
    }

    @Test
    public void testCalculateSimpleSubtraction() throws InvalidSyntaxException {
        String expression = "10 5 -";
        assertEquals(5, postFix.calculate(expression));
    }

    @Test
    public void testCalculateSimpleMultiplication() throws InvalidSyntaxException {
        String expression = "4 6 *";
        assertEquals(24, postFix.calculate(expression));
    }

    @Test
    public void testCalculateSimpleDivision() throws InvalidSyntaxException {
        String expression = "8 2 /";
        assertEquals(4, postFix.calculate(expression));
    }

    @Test
    public void testCalculateComplexExpression() throws InvalidSyntaxException {
        String expression = "5 1 2 + 4 * + 3 -";
        assertEquals(14, postFix.calculate(expression)); // (1 + 2) * 4 + 5 - 3 = 14
    }

    @Test
    public void testInvalidOperator() {
        String expression = "3 4 %"; // % is not a valid operator
        assertThrows(InvalidSyntaxException.class,  () -> postFix.calculate(expression));
    }

    @Test
    public void testInvalidSyntaxNonNumeric() {
        String expression = "3 a +"; // 'a' is invalid
        assertThrows(InvalidSyntaxException.class,  () -> postFix.calculate(expression));
    }

    @Test
    public void testInvalidSyntaxTooManyOperators() {
        String expression = "3 + +";
        assertThrows(InvalidSyntaxException.class,  () -> postFix.calculate(expression));
    }

    @Test
    public void testDivisionByZero() {
        String expression = "4 0 /";
        assertThrows(ArithmeticException.class, () -> postFix.calculate(expression));
    }

    @Test
    public void testEmptyExpression() throws InvalidSyntaxException {
        String expression = "";
        assertThrows(InvalidSyntaxException.class,  () -> postFix.calculate(expression));
    }

    @Test
    public void testInvalidExpressionMissingOperands() {
        String expression = "+ 3 4"; // Operator first, missing operand
        assertThrows(InvalidSyntaxException.class,  () -> postFix.calculate(expression));
    }
}
