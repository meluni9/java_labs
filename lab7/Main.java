package lab7;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Програма пошуку досконалих чисел.");
        System.out.print("Введіть верхню межу діапазону (позитивне число): ");

        if (scanner.hasNextInt()) {
            int limit = scanner.nextInt();

            if (limit > 0) {
                System.out.println("Досконалі числа в діапазоні від 1 до " + limit + ":");

                long count = IntStream.rangeClosed(1, limit)
                        .filter(Main::isPerfectNumber)
                        .peek(n -> System.out.println("Знайдено число: " + n))
                        .count();

                if (count == 0) {
                    System.out.println("У заданому діапазоні досконалих чисел не знайдено.");
                }

            } else {
                System.out.println("Помилка: Будь ласка, введіть число більше 0.");
            }
        } else {
            System.out.println("Помилка: Введено не ціле число.");
        }

        scanner.close();
    }

    public static boolean isPerfectNumber(int n) {
        if (n <= 1) return false;
        

        int sumOfDivisors = IntStream.rangeClosed(1, n / 2)
                .filter(divisor -> n % divisor == 0)
                .sum();
        
        return sumOfDivisors == n;
    }
}
