package com.astir_trotter.atcustom.utils;

import android.util.Log;

import com.astir_trotter.atcustom.global.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author - Yonis Larsson
 * @contact - yonis.larsson.biz@gmail.com
 * @date - 2/27/17
 */

public class EquationCalculator {
    private static final String TAG = EquationCalculator.class.getSimpleName();

    private static final int EQUATION_LENGTH_MAX = 4096;

    private static final double CALC_VALUE_MAX = Math.pow(2, 32) - 1;
    private static final double CALC_VALUE_MIN = -Math.pow(2, 31);
    private static final int CALC_SCALE_LENGTH = 6;

    public static final double CALC_VALUE_ERROR = CALC_VALUE_MAX + 1;

    // Operation Types
    private static final int CALC_OPERATION_NONE = 0;

    private static final int CALC_OPERATION_ADD = 1;
    private static final int CALC_OPERATION_SUB = 2;
    private static final int CALC_OPERATION_MUL = 3;
    private static final int CALC_OPERATION_DIV = 4;
    private static final int CALC_OPERATION_SQRT = 5;
    private static final int CALC_OPERATION_POWER = 6;

    private static final int CALC_OPERATION_SHIFT_LEFT = 7;
    private static final int CALC_OPERATION_SHIFT_RIGHT = 8;
    private static final int CALC_OPERATION_ROT_LEFT = 9;
    private static final int CALC_OPERATION_ROT_RIGHT = 10;
    private static final int CALC_OPERATION_INT = 11;
    private static final int CALC_OPERATION_SWAP = 12;

    private static final int CALC_OPERATION_AND = 13;
    private static final int CALC_OPERATION_OR = 14;
    private static final int CALC_OPERATION_XOR = 15;
    private static final int CALC_OPERATION_NOT = 16;
    private static final int CALC_OPERATION_SIGN = 17;

    // Bases (Operands)
    private static final String OPERAND_DEC = "\\d+\\.?\\d*|\\.\\d+";
    private static final String OPERAND_HEX = "[\\da-f]+h|0[xh][\\da-f]+";
    private static final String OPERAND_OCT = "[0-7]+o|0o[0-7]+";
    private static final String OPERAND_BIN = "[01]+b|0b[01]+";
    private static final String OPERAND = "(" + OPERAND_DEC + ")\\$?|" +
                                            "\\$?(" + OPERAND_DEC + ")|" +
                                            "(" + OPERAND_HEX + ")|" +
                                            "(" + OPERAND_OCT + ")|" +
                                            "(" + OPERAND_BIN + ")";
    private static final String OPERATOR = "\\+|-|×|\\*\\*|÷|/|\\^|\\*|<<|shl|>>|shr|rol|ror|&|\\||xor";
    private static final String UNARY = "√|sqrt|~|not|swap|int";

    private static double restrictValueRange(double value) {
        long scale_shift = (long) Math.pow(10, CALC_SCALE_LENGTH);
        value = (long) (value * scale_shift);
        value /= scale_shift;

        return Math.max(CALC_VALUE_MIN, Math.min(value, CALC_VALUE_MAX));
    }

    private static int getOperatorFromText(String operator) {
        int ret = CALC_OPERATION_NONE;

        switch (operator) {
            case "√":case "sqrt":
                ret = CALC_OPERATION_SQRT;
                break;
            case "~":case "not":
                ret = CALC_OPERATION_NOT;
                break;
            case "swap":
                ret = CALC_OPERATION_SWAP;
                break;
            case "int":
                ret = CALC_OPERATION_INT;
                break;
            case "+":
                ret = CALC_OPERATION_ADD;
                break;
            case "-":
                ret = CALC_OPERATION_SUB;
                break;
            case "×":case "*":
                ret = CALC_OPERATION_MUL;
                break;
            case "÷":case "/":
                ret = CALC_OPERATION_DIV;
                break;
            case "^":case "**":
                ret = CALC_OPERATION_POWER;
                break;
            case "<<":case "shl":
                ret = CALC_OPERATION_SHIFT_LEFT;
                break;
            case ">>":case "shr":
                ret = CALC_OPERATION_SHIFT_RIGHT;
                break;
            case "rol":
                ret = CALC_OPERATION_ROT_LEFT;
                break;
            case "ror":
                ret = CALC_OPERATION_ROT_RIGHT;
                break;
            case "&":
                ret = CALC_OPERATION_AND;
                break;
            case "|":
                ret = CALC_OPERATION_OR;
                break;
            case "xor":
                ret = CALC_OPERATION_XOR;
                break;
        }

        return ret;
    }

    private static String getTextFromOperator(int operator) {
        String ret;

        switch (operator) {
            case CALC_OPERATION_SQRT:
                ret = "√";
                break;
            case CALC_OPERATION_NOT:
                ret = "~";
                break;
            case CALC_OPERATION_SWAP:
                ret = "swap";
                break;
            case CALC_OPERATION_INT:
                ret = "int";
                break;
            case CALC_OPERATION_ADD:
                ret = "+";
                break;
            case CALC_OPERATION_SUB:
                ret = "-";
                break;
            case CALC_OPERATION_MUL:
                ret = "*";
                break;
            case CALC_OPERATION_DIV:
                ret = "/";
                break;
            case CALC_OPERATION_POWER:
                ret = "^";
                break;
            case CALC_OPERATION_SHIFT_LEFT:
                ret = "<<";
                break;
            case CALC_OPERATION_SHIFT_RIGHT:
                ret = ">>";
                break;
            case CALC_OPERATION_ROT_LEFT:
                ret = "rol";
                break;
            case CALC_OPERATION_ROT_RIGHT:
                ret = "ror";
                break;
            case CALC_OPERATION_AND:
                ret = "&";
                break;
            case CALC_OPERATION_OR:
                ret = "|";
                break;
            case CALC_OPERATION_XOR:
                ret = "xor";
                break;
            default:
                throw new IllegalArgumentException();
        }

        return ret;
    }

    private static int getOperatorPriority(int opType) {
        int ret;
        switch (opType) {

            // unary operators
            case CALC_OPERATION_NOT:
            case CALC_OPERATION_SWAP:
            case CALC_OPERATION_SQRT:
            case CALC_OPERATION_INT:
            case CALC_OPERATION_SIGN:
                ret = 0;
                break;

            case CALC_OPERATION_POWER:
                ret = 1;
                break;

            case CALC_OPERATION_MUL:
            case CALC_OPERATION_DIV:
                ret = 2;
                break;

            case CALC_OPERATION_ADD:
            case CALC_OPERATION_SUB:
                ret = 3;
                break;

            case CALC_OPERATION_SHIFT_LEFT:
            case CALC_OPERATION_SHIFT_RIGHT:
            case CALC_OPERATION_ROT_LEFT:
            case CALC_OPERATION_ROT_RIGHT:
                ret = 4;
                break;

            case CALC_OPERATION_AND:
                ret = 5;
                break;

            case CALC_OPERATION_XOR:
                ret = 6;
                break;

            case CALC_OPERATION_OR:
                ret = 7;
                break;

            default:
                throw new IllegalArgumentException();
        }

        return ret;
    }

    /**
     * @param expression to be evaluated
     * @param isFirstCall indicates if this is first call, i.e. from outside.
     * @return Equation object if possible. Null if not.
     */
    private static Equation getValidEquation(String expression, boolean isFirstCall) {
        Equation ret = new Equation();

        if (isFirstCall) {
            if (expression == null ||
                    expression.length() > EQUATION_LENGTH_MAX)
                return null;

            // remove all letters followed by '='
            int equalSymbolIndex;
            if ((equalSymbolIndex = expression.indexOf('=')) != -1)
                expression = expression.substring(0, equalSymbolIndex);

            // convert to lower-case
            expression = expression.replaceAll("[\\r\\n]", " ")
                    .replaceAll("\\,", "")
                    .toLowerCase()
                    .trim();

            // check if short-format
            Pattern shortFormat = Pattern.compile("^\\s*([+-]?(" + OPERAND + "))((\\s+[+-]?(" + OPERAND + "))*)\\s*$", Constants.PATTERN_FLAG);
            Matcher matcher = shortFormat.matcher(expression);
            if (matcher.matches()) {
                do {
                    String operand = matcher.group(1);
                    double operandValue = getOperandValue(operand);
                    ret.operands.add(operandValue);

                    String restExpression = matcher.group().substring(matcher.end(1)).trim();
                    if (!restExpression.isEmpty())
                        ret.operators.add(CALC_OPERATION_ADD);
                    else
                        break;

                    matcher.reset(restExpression);
                } while (matcher.matches());

                return ret;
            }
        }

        // full-format
        if (expression.startsWith("(") &&
                expression.endsWith(")"))
            expression = expression.substring(1, expression.length() - 1);

        if (expression.startsWith("+"))
            expression = expression.substring(1);
        else if (expression.startsWith("-"))
            expression = "0" + expression;

        boolean turnOperand = true; // switch between operand vs operator
        int startIndex = 0;
        while (startIndex < expression.length()) {
            int endIndex;
            if (turnOperand) {
                endIndex = findUnariesEndIndex(expression, startIndex);
                if (endIndex == -1)
                    throw new RuntimeException();
                String unariesString = expression.substring(startIndex, endIndex).trim();
                List<Integer> precedingUnaryOperators = getUnaries(unariesString);
                startIndex = endIndex;

                endIndex = findOperandEndIndex(expression, startIndex);
                if (endIndex == -1)
                    return null;

                String operandString = expression.substring(startIndex, endIndex).trim();
                Equation operand;
                if (operandString.startsWith("(")) {
                    if ((operand = getValidEquation(operandString, false)) == null)
                        return null;
                } else
                    operand = new Equation(getOperandValue(operandString));

                operand.unaries.addAll(precedingUnaryOperators);
                ret.operands.add(operand);

            } else {
                endIndex = findOperatorEndIndex(expression, startIndex);
                if (endIndex == -1)
                    return null;
                String operatorString = expression.substring(startIndex, endIndex);

                ret.operators.add(getOperatorFromText(operatorString));
            }

            turnOperand = !turnOperand;
            startIndex = endIndex;
        }

        // empty expression or no last operand
        if (expression.isEmpty() || turnOperand)
            return null;

        return ret;
    }

    /**
     * Detect series of unaries,
     */
    private static int findUnariesEndIndex(String expression, int startIndex) {
        Pattern pattern = Pattern.compile("^\\s*((" + UNARY + ")\\s*)*", Constants.PATTERN_FLAG);
        Matcher matcher = pattern.matcher(expression)
                .region(startIndex, expression.length())
                .useAnchoringBounds(true);
        if (matcher.find())
            return matcher.end();

        return -1;
    }

    /**
     * Detect enclosed brackets or simple value.
     * Operand cannot have sign(+/-) as a prefix.
     *
     * @return -1, if not found. (appends preceding/following spaces)
     */
    private static int findOperandEndIndex(String expression, int startIndex) {
        // if starts with (, then find its ).
        if (expression.charAt(startIndex) == '(') {
            int depth = 1;
            for (startIndex = startIndex + 1; startIndex < expression.length(); startIndex++) {
                switch (expression.charAt(startIndex)) {
                    case '(':
                        depth++;
                        break;
                    case ')':
                        if (--depth == 0)
                            return startIndex + 1;
                        break;
                }
            }
            return -1;
        }

        // simple value
        Pattern pattern = Pattern.compile("^\\s*(" + OPERAND + ")\\s*", Constants.PATTERN_FLAG);
        Matcher matcher = pattern.matcher(expression)
                .region(startIndex, expression.length())
                .useAnchoringBounds(true);
        if (matcher.find())
            return matcher.end();

        return -1;
    }

    /**
     * no spaces before / after.
     */
    private static int findOperatorEndIndex(String expression, int startIndex) {
        Pattern pattern = Pattern.compile("^(" + OPERATOR + ")", Constants.PATTERN_FLAG);
        Matcher matcher = pattern.matcher(expression)
                .region(startIndex, expression.length())
                .useAnchoringBounds(true);
        if (matcher.find())
            return matcher.end();

        return -1;
    }

    /**
     * Convert operand string to double value.
     */
    private static double getOperandValue(String operand) {
        double value = 0;
        boolean isValid = false;

        Pattern pattern;
        Matcher matcher;

        // dec
        pattern = Pattern.compile("([+-]?)((" + OPERAND_DEC + ")\\$?|" + "\\$?(" + OPERAND_DEC + "))", Constants.PATTERN_FLAG);
        if ((matcher = pattern.matcher(operand)).matches()) {
            String decStr = matcher.group(3) != null ? matcher.group(3) : matcher.group(4);
            value = Convertor.getFloat(decStr);
            isValid = true;
        }

        // hex
        pattern = Pattern.compile("([+-]?)(" + OPERAND_HEX + ")", Constants.PATTERN_FLAG);
        if (!isValid && (matcher = pattern.matcher(operand)).matches()) {
            String hexStr = matcher.group(2);
            if (hexStr.startsWith("0"))
                hexStr = hexStr.substring(2);
            else if (hexStr.endsWith("h"))
                hexStr = hexStr.substring(0, hexStr.length() - 1);
            value = Convertor.getLong(hexStr, 16);
            isValid = true;
        }

        // oct
        pattern = Pattern.compile("([+-]?)(" + OPERAND_OCT + ")", Constants.PATTERN_FLAG);
        if (!isValid && (matcher = pattern.matcher(operand)).matches()) {
            String octStr = matcher.group(2);
            if (octStr.startsWith("0"))
                octStr = octStr.substring(2);
            else
                octStr = octStr.substring(0, octStr.length() - 1);
            value = Convertor.getLong(octStr, 8);
            isValid = true;
        }

        // bin
        pattern = Pattern.compile("([+-]?)(" + OPERAND_BIN + ")", Constants.PATTERN_FLAG);
        if (!isValid && (matcher = pattern.matcher(operand)).matches()) {
            String binStr = matcher.group(2);
            if (binStr.startsWith("0"))
                binStr = binStr.substring(2);
            else
                binStr = binStr.substring(0, binStr.length() - 1);
            value = Convertor.getLong(binStr, 2);
            isValid = true;
        }

        if (!isValid)
            return CALC_VALUE_ERROR;

        if (matcher.group(1).equalsIgnoreCase("-"))
            value = -value;

        return restrictValueRange(value);
    }

    private static List<Integer> getUnaries(String unariesString) {
        List<Integer> ret = new ArrayList<>();

        Pattern pattern = Pattern.compile(UNARY, Constants.PATTERN_FLAG);
        Matcher matcher = pattern.matcher(unariesString);
        int startIndex = 0;
        while (matcher.find(startIndex)) {
            ret.add(getOperatorFromText(matcher.group()));
            startIndex = matcher.end();
        }

        if (startIndex != unariesString.length())
            throw new RuntimeException();

        return ret;
    }

    /**
     * calculate based on operator with operand1, operand2.
     * @param operand1 (ignored for unary operators)
     * @return result value (double type)
     */
    private static double calcOneTerm(double operand1, double operand2, int operator) {
        if (operand1 == CALC_VALUE_ERROR ||
                operand2 == CALC_VALUE_ERROR)
            return CALC_VALUE_ERROR;

        boolean isError = false;
        double ret = 0;

        switch (operator) {
            // single actions
            case CALC_OPERATION_SQRT:
                if (operand2 < 0)
                    isError = true;
                else
                    ret = Math.sqrt(operand2);
                break;
            case CALC_OPERATION_INT:
                ret = (long) operand2;
                break;
            case CALC_OPERATION_SWAP:
                long b0 = ((long) operand2) & 0xFF;
                long b1 = ((long) operand2 >> 8) & 0xFF;
                long b2 = ((long) operand2 >> 16) & 0xFF;
                long b3 = ((long) operand2 >> 24) & 0xFF;
                ret = (b0 << 24) + (b1 << 16) + (b2 << 8) + b3;
                break;
            case CALC_OPERATION_NOT:
                int size = (int) (Math.log10(operand2) / Math.log10(2.0d)) + 1;
                size = (size > 16) ? 32 : ((size > 8) ? 16 : 8);
                final long not = (1 << (size + 1)) - 1;
                ret = not ^ (long) operand2;
                break;
            case CALC_OPERATION_ADD:
                ret = operand1 + operand2;
                break;
            case CALC_OPERATION_SUB:
                ret = operand1 - operand2;
                break;
            case CALC_OPERATION_MUL:
                ret = operand1 * operand2;
                break;
            case CALC_OPERATION_DIV:
                if (operand2 == 0) {
                    isError = true;
                    Log.d(TAG, "Divide by zero.");
                    ret = 0;
                } else
                    ret = operand1 / operand2;
                break;
            case CALC_OPERATION_POWER:
                ret = Math.pow(operand1, operand2);
                break;
            case CALC_OPERATION_SHIFT_LEFT:
                ret = ((long) operand1 << (long) operand2) & (long) CALC_VALUE_MAX;
                break;
            case CALC_OPERATION_SHIFT_RIGHT:
                ret = (long) operand1 >> (long) operand2;
                break;
            case CALC_OPERATION_ROT_LEFT:
                ret = operand1;
                for (long i = 0; i < operand2; i++) {
                    long shifted_bit = ((long) ret & (1 << 31)) >> 31;
                    ret = (((long) ret << 1) + shifted_bit) & (long) CALC_VALUE_MAX;
                }
                break;
            case CALC_OPERATION_ROT_RIGHT:
                ret = operand1;
                for (long i = 0; i < operand2; i++) {
                    long shifted_bit = (long) ret & 1;
                    ret = ((long) ret >> 1) + (shifted_bit << 31);
                }
                break;
            case CALC_OPERATION_AND:
                ret = (long) operand1 & (long) operand2;
                break;
            case CALC_OPERATION_OR:
                ret = (long) operand1 | (long) operand2;
                break;
            case CALC_OPERATION_XOR:
                ret = (long) operand1 ^ (long) operand2;
                break;

            default:
                isError = true;
        }

        if (isError)
            ret = CALC_VALUE_ERROR;
        else
            ret = restrictValueRange(ret);

        return ret;
    }

    public static double calcExpression(String expression) {
        Equation equation = getValidEquation(expression, true);
        if (equation == null)
            return CALC_VALUE_ERROR;

        return equation.getValue();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private static class Equation {
        /**
         * simple double value or Equation reference
         */
        List<Object> operands;

        /**
         * operators between operands (So operators' size is 1 less than operands'.
         * There's no unary operators. Such operands will be calculated in parsing stage.
         */
        List<Integer> operators;

        /**
         * Unary operators
         */
        List<Integer> unaries;

        private Equation() {
            operands = new ArrayList<>();
            operators = new ArrayList<>();
            unaries = new ArrayList<>();
        }

        private Equation(double value) {
            this();
            operands.add(value);
        }

        private double getValue() {
            if (operands.isEmpty() ||
                    operands.size() != operators.size() + 1)
                throw new IllegalArgumentException();

            // sub-equations
            for (int i = 0; i < operands.size(); i++) {
                Object obj;
                if ((obj = operands.get(i)) instanceof Equation) {
                    operands.remove(i);
                    operands.add(i, ((Equation) obj).getValue());
                }
            }

            // operators
            while (!operators.isEmpty()) {
                int opPriority = -1;
                int opIndex = 0;
                for (int i = 0; i < operators.size(); i++) {
                    int operator = operators.get(i);
                    int priority = getOperatorPriority(operator);
                    if (opPriority == -1 ||
                            priority < opPriority) {
                        opIndex = i;
                        opPriority = priority;
                    }
                }

                double operand2 = (double) operands.remove(opIndex + 1);
                double operand1 = (double) operands.remove(opIndex);
                operands.add(opIndex, calcOneTerm(operand1, operand2, operators.remove(opIndex)));
            }

            double value = (double) operands.get(0);
            // unaries
            for (int i = unaries.size() - 1; i >= 0; i--) {
                int unary = unaries.get(i);
                value = calcOneTerm(0, value, unary);
            }

            return value;
        }

        @Override
        public String toString() {
            StringBuilder retBuilder = new StringBuilder();
            if (!unaries.isEmpty()) {
                for (Integer unary : unaries)
                    retBuilder.append(getTextFromOperator(unary))
                            .append(' ');
            }

            if (!operators.isEmpty())
                retBuilder.append('(');

            for (int i = 0; i < operands.size(); i++) {
                Object operand = operands.get(i);
                if (!(operand instanceof Equation) &&
                        ((double) operand) == CALC_VALUE_ERROR)
                    retBuilder.append("Err");
                else
                    retBuilder.append(operand.toString());

                if (i < operands.size() - 1)
                    retBuilder.append(getTextFromOperator(operators.get(i)));
            }

            if (!operators.isEmpty())
                retBuilder.append(')');

            return retBuilder.toString();
        }
    }
}
