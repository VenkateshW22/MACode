package com.myuniversity.app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.myuniversity.model.Course;
import com.myuniversity.util.HibernateUtil;

public class CourseCrudApp {

    public static void main(String[] args) {
        // 1. Create a new course
        Long newCourseId = createCourse("Data Structures", "Prof. Alan Turing");
        if (newCourseId != null) {
            // 2. Read the created course
            readCourse(newCourseId);

            // 3. Update the course's instructor
            updateCourse(newCourseId, "Prof. Ada Lovelace");

            // 4. Read again to verify update
            readCourse(newCourseId);

            // 5. Delete the course
//            deleteCourse(newCourseId);

            // Try to read after delete (should not be found)
            readCourse(newCourseId);
        }

        HibernateUtil.shutdown();
    }

    private static Long createCourse(String title, String instructor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Long courseId = null;
        try {
            transaction = session.beginTransaction();
            Course course = new Course(title, instructor);
            session.save(course);
            transaction.commit();
            courseId = course.getId();
            System.out.println("Created Course: '" + course.getTitle() + "' by " + course.getInstructor() + " (ID: " + courseId + ")");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error creating course: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return courseId;
    }

    private static void readCourse(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Course course = session.get(Course.class, id);
            if (course != null) {
                System.out.println("Read Course (ID: " + id + "): Title='" + course.getTitle() + "', Instructor='" + course.getInstructor() + "'");
            } else {
                System.out.println("Course with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Error reading course: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    private static void updateCourse(Long id, String newInstructor) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                course.setInstructor(newInstructor);
                System.out.println("Updating Course (ID: " + id + ") to new instructor: " + newInstructor);
            } else {
                System.out.println("Course with ID " + id + " not found for update.");
            }
            transaction.commit();
            System.out.println("Course (ID: " + id + ") instructor updated.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error updating course: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    private static void deleteCourse(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                session.delete(course);
                System.out.println("Deleted Course with ID: " + id);
            } else {
                System.out.println("Course with ID " + id + " not found for deletion.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error deleting course: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }
}