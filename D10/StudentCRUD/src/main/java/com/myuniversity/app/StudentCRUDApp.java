package com.myuniversity.app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.myuniversity.model.Student;
import com.myuniversity.util.HibernateUtil;

public class StudentCRUDApp {

    public static void main(String[] args) {
        // --- C: Create (Save) ---
        Long newStudentId = createStudent("Alice Smith", "alice.smith@example.com");
        if (newStudentId != null) {
            // --- R: Read (Get) ---
            readStudent(newStudentId);

            // --- U: Update ---
            updateStudent(newStudentId, "alice.updated@example.com");

            // --- R: Read again to verify update ---
            readStudent(newStudentId);

            // --- D: Delete ---
            deleteStudent(newStudentId);

            // Try to read after delete (should not be found)
            readStudent(newStudentId);
        }

        // It's good practice to shut down the SessionFactory when the application exits
        HibernateUtil.shutdown();
    }

    /**
     * Creates a new Student record in the database.
     * @param name The name of the student.
     * @param email The email of the student.
     * @return The ID of the newly created student, or null if creation failed.
     */
    private static Long createStudent(String name, String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long studentId = null;

        try {
            transaction = session.beginTransaction();
            Student student = new Student(name, email); // Transient state
            session.save(student); // Moves to Persistent state, INSERT SQL generated
            transaction.commit();
            studentId = student.getId();
            System.out.println(" Created Student: " + student.getName() + " (ID: " + studentId + ")");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error creating student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return studentId;
    }

    /**
     * Reads a Student record from the database by ID.
     * @param id The ID of the student to read.
     */
    private static void readStudent(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Using get() method: fetches immediately, returns null if not found
            Student student = session.get(Student.class, id);
            if (student != null) {
                System.out.println(" Read Student (ID: " + id + "): Name=" + student.getName() + ", Email=" + student.getEmail());
            } else {
                System.out.println(" Student with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Error reading student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * Updates a Student's email in the database.
     * @param id The ID of the student to update.
     * @param newEmail The new email address.
     */
    private static void updateStudent(Long id, String newEmail) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id); // Get persistent object
            if (student != null) {
                student.setEmail(newEmail); // Modify persistent object (dirty checking)
                // No explicit session.update(student) needed here if object is persistent
                System.out.println(" Updating Student (ID: " + id + ") to new email: " + newEmail);
            } else {
                System.out.println(" Student with ID " + id + " not found for update.");
            }
            transaction.commit(); // Hibernate flushes changes, UPDATE SQL generated
            System.out.println("Student (ID: " + id + ") email updated.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    /**
     * Deletes a Student record from the database by ID.
     * @param id The ID of the student to delete.
     */
    private static void deleteStudent(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id); // Get persistent object
            if (student != null) {
                session.delete(student); // DELETE SQL generated
                System.out.println("Deleted Student with ID: " + id);
            } else {
                System.out.println("Student with ID " + id + " not found for deletion.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error deleting student: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }
}
