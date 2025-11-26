package lab3.view;

public class ConsoleView {
    public void print(String msg) {
        System.out.println(msg);
    }

    public void printMenu() {
        System.out.println("\n--- SKI RESORT CONTROL ---");
        System.out.println("1. Issue WEEKDAY Count Pass (10/20/50/100)");
        System.out.println("2. Issue WEEKDAY Time Pass (1/2/5 days)");
        System.out.println("3. Issue WEEKDAY Half-Day (Morning/Afternoon)");
        System.out.println("4. Issue WEEKEND Count Pass (10/20/50/100)");
        System.out.println("5. Issue WEEKEND Time Pass (1/2 days)");
        System.out.println("6. Issue WEEKEND Half-Day (Morning/Afternoon)");
        System.out.println("7. Issue SEASON Pass");
        System.out.println("8. Block Pass by ID");
        System.out.println("9. Simulate Turnstile Entry");
        System.out.println("10. Show Statistics");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }
}
