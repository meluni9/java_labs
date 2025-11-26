package lab3.controller;

import lab3.model.*;
import lab3.service.SkiPassRegistry;
import lab3.service.Turnstile;
import lab3.view.ConsoleView;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ResortController {
    private SkiPassRegistry registry;
    private Turnstile turnstile;
    private ConsoleView view;
    private Scanner scanner;

    public ResortController() {
        this.registry = new SkiPassRegistry();
        this.turnstile = new Turnstile(registry);
        this.view = new ConsoleView();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": createCountPass(SkiPassType.WEEKDAY); break;
                case "2": createDayPass(SkiPassType.WEEKDAY); break;
                case "3": createHalfDayPass(SkiPassType.WEEKDAY); break;
                case "4": createCountPass(SkiPassType.WEEKEND); break;
                case "5": createDayPass(SkiPassType.WEEKEND); break;
                case "6": createHalfDayPass(SkiPassType.WEEKEND); break;
                case "7": createSeasonPass(); break;
                case "8": blockPass(); break;
                case "9": simulateEntry(); break;
                case "10": view.print(turnstile.getStatistics()); break;
                case "0": running = false; break;
                default: view.print("Invalid option");
            }
        }
    }

    private void createCountPass(SkiPassType type) {
        view.print("Enter rides (10, 20, 50, 100):");
        int rides = Integer.parseInt(scanner.nextLine());
        SkiPass pass = new CountSkiPass(type, rides);
        registry.register(pass);
        view.print("Created: " + pass);
    }

    private void createDayPass(SkiPassType type) {
        view.print("Enter days (1, 2, 5):");
        int days = Integer.parseInt(scanner.nextLine());
        SkiPass pass = new DaySkiPass(type, days);
        registry.register(pass);
        view.print("Created: " + pass);
    }

    private void createHalfDayPass(SkiPassType type) {
        view.print("1. Morning (9-13)\n2. Afternoon (13-17)");
        String choice = scanner.nextLine();
        boolean isMorning = choice.equals("1");
        SkiPass pass = new HalfDaySkiPass(type, isMorning);
        registry.register(pass);
        view.print("Created: " + pass);
    }

    private void createSeasonPass() {
        SkiPass pass = new SeasonSkiPass();
        registry.register(pass);
        view.print("Created: " + pass);
    }

    private void blockPass() {
        view.print("Enter ID to block:");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            registry.block(id);
            view.print("Pass " + id + " blocked (if it existed).");
        } catch (NumberFormatException e) {
            view.print("Invalid ID format.");
        }
    }

    private void simulateEntry() {
        view.print("Enter ID:");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            LocalDateTime now = LocalDateTime.now();

            boolean allowed = turnstile.check(id, now);

            if (allowed) {
                view.print(">>> ACCESS GRANTED");
                SkiPass pass = registry.getById(id);
                view.print("Current Status: " + pass);
            } else {
                view.print(">>> ACCESS DENIED");
                SkiPass pass = registry.getById(id);
                if (pass == null) {
                    view.print("Reason: Card not found (Read Error)");
                } else {
                    view.print("Reason: Blocked, Expired, Wrong Time/Day or No Rides left.");
                    view.print("Card Info: " + pass);
                }
            }
        } catch (NumberFormatException e) {
            view.print("Invalid ID.");
        }
    }
}
