package ADTs;

import Exceptions.EmptyCollectionException;
import Exceptions.InvalidSyntaxException;

public class PostFix {

    private StackADT<Integer> stack;

    public PostFix() {
        this.stack = new LinkedStack<>();
    }

    public int calculate(String expression) throws InvalidSyntaxException {
        if (expression.isEmpty()) {
            throw new InvalidSyntaxException("Empty expression");
        }

        String[] elements = expression.split(" ");

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].matches("[0-9]+")) {
                int newNumber = Integer.parseInt(elements[i]);
                stack.push(newNumber);
            } else if (elements[i].matches("[-+*/]")) {
                try {
                    stack.push(simpleOperation(elements[i].charAt(0), stack.pop(), stack.pop()));
                } catch (EmptyCollectionException e) {
                    throw new InvalidSyntaxException("Invalid Syntax");
                }
            } else {
                throw new InvalidSyntaxException("Invalid Syntax");
            }
        }

        int result = 0;

        try {
            result = this.stack.pop();
        } catch (EmptyCollectionException e) {
            throw new InvalidSyntaxException("Invalid Syntax");
        }
        return result;
    }

    private int simpleOperation(char operator, int operand2, int operand1) throws InvalidSyntaxException {
        switch (operator) {
            case '*':
                return operand1 * operand2;
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '/':
                return operand1 / operand2;
            default:
                throw new InvalidSyntaxException("Invalid operator");
        }
    }
}
