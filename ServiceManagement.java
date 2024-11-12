package com.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class ServiceManagement {

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    // Add a new book
    public void addBook(String title, String author) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new Books(title, author, true));
            transaction.commit();
            System.out.println("Book added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Add a new member
    public void addMember(String name, String email) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new Member(name, email));
            transaction.commit();
            System.out.println("Member added successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // View all books
    public void viewBooks() {
        try (Session session = sessionFactory.openSession()) {
            List<Books> books = session.createQuery("from Books", Books.class).list();
            for (Books book : books) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Available: " + book.isAvailable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Borrow a book
    public void borrowBook(int bookId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Books book = session.get(Books.class, bookId);
            if (book != null && book.isAvailable()) {
                book.setAvailable(false);
                session.update(book);
                transaction.commit();
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Book is not available.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Return a book
    public void returnBook(int bookId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Books book = session.get(Books.class, bookId);
            if (book != null && !book.isAvailable()) {
                book.setAvailable(true);
                session.update(book);
                transaction.commit();
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Book was not borrowed.");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
