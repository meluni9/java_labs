package lab5;

import java.io.*;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final FileService fileService = new FileService();

    public static void main(String[] args) {
        System.out.println("I/O Streams, Serialization, Networking");

        while (true) {
            printMenu();
            int choice = getIntInput();

            try {
                switch (choice) {
                    case 1:
                        task1MaxWords();
                        break;
                    case 2:
                        task3Encryption();
                        break;
                    case 3:
                        task4TagAnalysis();
                        break;
                    case 4:
                        taskSerializationDemo();
                        break;
                    case 0:
                        System.out.println("Вихід...");
                        return;
                    default:
                        System.out.println("Невірний вибір. Спробуйте ще раз.");
                }
            } catch (Exception e) {
                System.err.println("Помилка під час виконання: " + e.getMessage());
                // e.printStackTrace();
            }
            System.out.println("\n--------------------------------------------------");
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Знайти рядок з макс. кількістю слів у файлі (Завдання 1)");
        System.out.println("2. Шифрування/Дешифрування файлу (Завдання 3)");
        System.out.println("3. Аналіз HTML тегів з URL (Завдання 4)");
        System.out.println("4. Серіалізація результатів аналізу (Вимоги 2, 3)");
        System.out.println("0. Вихід");
        System.out.print("Ваш вибір: ");
    }

    private static void task1MaxWords() throws IOException {
        System.out.print("Введіть шлях до вхідного файлу: ");
        String path = scanner.next();
        String result = fileService.findLineWithMaxWords(path);
        System.out.println("Результат:\n" + result);
    }

    private static void task3Encryption() throws IOException {
        System.out.println("1. Зашифрувати файл");
        System.out.println("2. Розшифрувати файл");
        System.out.print("Вибір: ");
        int subChoice = getIntInput();

        System.out.print("Введіть шлях до вхідного файлу: ");
        String inPath = scanner.next();
        System.out.print("Введіть шлях для збереження результату: ");
        String outPath = scanner.next();
        System.out.print("Введіть числовий ключ (ціле число): ");
        int key = getIntInput();

        if (subChoice == 1) {
            fileService.encryptFile(inPath, outPath, key);
            System.out.println("Файл успішно зашифровано.");
        } else if (subChoice == 2) {
            fileService.decryptFile(inPath, outPath, key);
            System.out.println("Файл успішно розшифровано.");
        } else {
            System.out.println("Невірний вибір підменю.");
        }
    }

    private static void task4TagAnalysis() throws IOException {
        System.out.print("Введіть URL адресу (наприклад, https://google.com): ");
        String url = scanner.next();

        System.out.println("Аналіз сторінки...");
        Map<String, Integer> tags = fileService.analyzeTags(url);

        if (tags.isEmpty()) {
            System.out.println("Тегів не знайдено або помилка читання.");
            return;
        }

        System.out.println("\n--- а) Сортування лексикографічно (A-Z) ---");
        Map<String, Integer> sortedByKey = new TreeMap<>(tags);
        sortedByKey.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("\n--- б) Сортування за частотою (Зростання) ---");
        tags.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
        
        lastAnalysisResult = new TagAnalysisResult(url, tags);
        System.out.println("\nРезультат аналізу збережено в пам'яті. Ви можете зберегти його у файл через 4.");
    }
    
    private static TagAnalysisResult lastAnalysisResult = null;

    private static void taskSerializationDemo() throws IOException, ClassNotFoundException {
        System.out.println("1. Зберегти останній результат аналізу тегів у файл");
        System.out.println("2. Завантажити результат аналізу з файлу");
        System.out.print("Вибір: ");
        int subChoice = getIntInput();

        System.out.print("Введіть ім'я файлу (наприклад, data.ser): ");
        String filePath = scanner.next();

        if (subChoice == 1) {
            if (lastAnalysisResult == null) {
                System.out.println("Спочатку виконайте Завдання 4 (аналіз тегів)!");
                return;
            }
            fileService.saveObjectToFile(filePath, lastAnalysisResult);
            System.out.println("Об'єкт успішно серіалізовано.");
        } else if (subChoice == 2) {
            Object obj = fileService.loadObjectFromFile(filePath);
            if (obj instanceof TagAnalysisResult) {
                TagAnalysisResult loaded = (TagAnalysisResult) obj;
                System.out.println("Об'єкт завантажено:");
                System.out.println(loaded);
                System.out.println("Перші 5 тегів:");
                loaded.getTagsMap().entrySet().stream().limit(5).forEach(System.out::println);
            } else {
                System.out.println("Невідомий тип об'єкта у файлі.");
            }
        }
    }

    private static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Будь ласка, введіть число: ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
