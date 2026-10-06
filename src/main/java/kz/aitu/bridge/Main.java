package kz.aitu.bridge;

import kz.aitu.bridge.client.ConsoleApplication;
import kz.aitu.bridge.client.WebApplication;

import java.util.Scanner;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select mode:");
        System.out.println("1. Console Mode");
        System.out.println("2. Web Mode (localhost)");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim();

        if ("2".equals(choice)) {
            System.out.println("Starting in Web Mode...");
            try {
                new WebApplication().run();
            } catch (Exception e) {
                System.err.println("Failed to start Web Mode: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Starting in Console Mode...");
            new ConsoleApplication().run();
        }
    }
}
