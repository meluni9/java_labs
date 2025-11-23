package lab1;

import java.util.Scanner;

public class PerfectNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Програма пошуку досконалих чисел.");
        System.out.print("Введіть верхню межу діапазону (позитивне число): ");

        if (scanner.hasNextInt()) {
            int limit = scanner.nextInt();

            if (limit > 0) {
                System.out.println("Досконалі числа в діапазоні від 1 до " + limit + ":");
                
                int foundCount = 0;

                for (int i = 1; i <= limit; i++) {
                    int sumOfDivisors = 0;

                    for (int j = 1; j <= i / 2; j++) {
                        if (i % j == 0) {
                            sumOfDivisors += j;
                        }
                    }

                    if (sumOfDivisors == i && i != 0) {
                        System.out.println("Знайдено число: " + i);
                        foundCount++;
                    }
                }

                if (foundCount == 0) {
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
}
