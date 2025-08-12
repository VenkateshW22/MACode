package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.*;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //one to one relationship example
        OnetoOne(em, emf);

        //one to many relationship example
        OnetoMany(em, emf);

        //Many to many relationship example
        ManytoMany(em, emf);

        //Many to one relationship example
        ManytoOne(em, emf);

        em.close();
        emf.close();
    }

    private static void ManytoOne(EntityManager em, EntityManagerFactory emf) {
        Employee employee = new Employee("Ravi Teja");
        Department department = new Department("IT");
        employee.setDepartment(department);
        department.getEmployees().add(employee);
        em.persist(employee);
        em.persist(department);
        System.out.println(department.getName() + " " + department.getEmployees().size());
        em.getTransaction().commit();
        System.out.println("Emplopyees in department " + department.getName() + " are: "+ department.getEmployees().size());
        em.close();
        emf.close();
    }

    private static void ManytoMany(EntityManager em, EntityManagerFactory emf) {
        Student student1 = new Student("Venkatesh K");
        Student student2 = new Student("Ravi Teja");
        Course course1 = new Course("Java");
        Course course2 = new Course("Python");
        student1.getCourses().add(course1);
        student1.getCourses().add(course2);
        student2.getCourses().add(course1);
        student2.getCourses().add(course2);
        course1.getStudents().add(student1);
        course1.getStudents().add(student2);
        course2.getStudents().add(student1);
        course2.getStudents().add(student2);
        em.persist(student1);
        em.persist(student2);
        em.persist(course1);
        em.persist(course2);
        System.out.println(student1.getName() + " " + student1.getCourses().size());
        System.out.println(course1.getName() + " " + course1.getStudents().size());
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void OnetoMany(EntityManager em, EntityManagerFactory emf) {
        Customer customer = new Customer("Venkatesh K");
        Order order1 = new Order("Od-001");
        Order order2 = new Order("Od-002");
        customer.getOrders().add(order1);
        customer.getOrders().add(order2);
        order1.setCustomer(customer);
        order2.setCustomer(customer);
        em.persist(customer);
        System.out.println(customer.getName() + " " + customer.getOrders().size());
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

    private static void OnetoOne(EntityManager em, EntityManagerFactory emf) {
        UserProfile profile = new UserProfile("I am a Software Engineer");
        User user = new User("Venkatesh K");
        user.setProfile(profile);
        profile.setUser(user);
        em.persist(user);
        System.out.println(user.getName() + " " + user.getProfile().getBio());
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}