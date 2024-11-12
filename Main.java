package com.demo;

import java.util.Scanner;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        ServiceManagement service = new ServiceManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Library Management System ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. View Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    service.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter member name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter member email: ");
                    String email = scanner.nextLine();
                    service.addMember(name, email);
                    break;
                case 3:
                    service.viewBooks();
                    break;
                case 4:
                    System.out.print("Enter book ID to borrow: ");
                    int bookId = scanner.nextInt();
                    service.borrowBook(bookId);
                    break;
                case 5:
                    System.out.print("Enter book ID to return: ");
                    bookId = scanner.nextInt();
                    service.returnBook(bookId);
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sessionFactory.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
