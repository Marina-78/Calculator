
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CalculatorLogic {
    private static final List<String> numbers = Arrays.asList(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X");

    public static void startCalculator() {
        System.out.println("Внимание! В выражении должны быть числа одного из двух типов " +
                "Арабские 1-10 или Римские I-X.");
        try {
            while (true) {
                System.out.print("\nВведите арифметическое выражение через пробел + Enter: ");
                Scanner scanner = new Scanner(System.in);

                String result;
                String[] expression;

                expression = scanner.nextLine().split(" ");

                if (expression.length != 3) {
                    throw new CalculatorException("Некорректное арифметическое выражение!");

                } else if (!chekExpression(expression[0], expression[2])) {

                    throw new CalculatorException("Операнды выходят за рамки указанных ограничений!");

                } if (chekNumberType(expression[0], expression[2])) {
                    int first = Integer.parseInt(expression[0]);
                    int second = Integer.parseInt(expression[2]);

                    result = String.valueOf(calculatingResult(first, second, expression[1]));

                } else {
                    result = calculatingRomanResult(expression);
                }
                System.out.print("\nОтвет: " + result + "\n");

            }

        } catch (CalculatorException e) {
            System.out.println(e.getErrorMsg());
        }
    }
    private static boolean chekExpression (String s1, String s2) {
        return numbers.contains(s1) && numbers.contains(s2);
    }
    private static boolean chekNumberType(String s1, String s2) {
        String regex = "[1-9]$|^10";
        return s1.matches(regex) && s2.matches(regex);
    }
    private static int calculatingResult(int a, int b, String operator) throws CalculatorException {
        int result;

        switch (operator) {
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            case "-":
                result = a - b;
                break;
            case "+":
                result = a + b;
                break;
            default: throw new CalculatorException("Некорректный знак оператора в арифметическом выражении!");
        } return result;
    }
    private static String calculatingRomanResult(String[] expression) throws CalculatorException {
        CalculatorRomNumbers first = null;
        CalculatorRomNumbers second = null;
        String result;
        for (CalculatorRomNumbers num : CalculatorRomNumbers.values()) {
            if (num.toString().equals(expression[0])) {
                first = num;
            } if (num.toString().equals(expression[2])) {
                second = num;
            }
        } if (first == null || second == null) {
            throw new CalculatorException("Некорректный один из двух операндов!");
        }
        int calcResIndex = calculatingResult(first.ordinal(), second.ordinal(), expression[1]);
        if (calcResIndex > 0) {
            result = String.valueOf(CalculatorRomNumbers.values()[calcResIndex]);
        } else {
            throw new CalculatorException("Ответ в римских числах не может быть отрицательным или нулевым!");
        }
        return result;
    }
}
