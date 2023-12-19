package ru.javajunior;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.javajunior.models.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class Main {
    /*
        Создайте базу данных (например, SchoolDB).
        В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
        Настройте Hibernate для работы с вашей базой данных.
        Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
        Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
        Убедитесь, что каждая операция выполняется в отдельной транзакции.
     */
    static Random rnd = new Random();

    public static void main(String[] args) {
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {

            //Вставка в таблицу
            for (int i = 0; i < rnd.nextInt(15, 31); i++) {
                Course course = Course.create();
                // addCourse(sessionFactory,course);
            }
            //Чтение всех курсов
            List<Course> courseList = readAllCourses(sessionFactory);
            courseList.forEach(System.out::println);

            //Получение курса по id
            Course courseWithId = readCourseById(sessionFactory, 13);
            System.out.println(courseWithId);

            //Обновление данных
            updateCourse(sessionFactory, 15);

            //Удаление по ID
            deleteCourseById(sessionFactory, 17);

            //Удаление всех курсов
            //deleteAllCourses(sessionFactory);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addCourse(SessionFactory sessionFactory, Course course) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
            System.out.println("Добавление курса прошло успешно!");
        }
    }

    private static List<Course> readAllCourses(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Course> courseList = session.createQuery("FROM Course").getResultList();
            System.out.println("Чтение курсов прошло успешно!");
            return courseList;
        }
    }

    private static Course readCourseById(SessionFactory sessionFactory, int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null)
                System.out.println("Чтение курса прошло успешно!");
            else
                System.out.println("Курса с таким ID нет!");
            return course;
        }
    }

    private static void updateCourse(SessionFactory sessionFactory, int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                System.out.println("Ныняшние данные этого курса: " + course);
                course.randomUpdate();
                System.out.println("Новые данные этого курса: " + course);
                session.update(course);
                session.getTransaction().commit();
            } else
                System.out.println("Курса с таким ID нет!");
        }
    }

    private static void deleteCourseById(SessionFactory sessionFactory, int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Course courseToDelete = session.get(Course.class, id);
            if (courseToDelete != null) {
                session.delete(courseToDelete);
                session.getTransaction().commit();
                System.out.println("Курс успешно удален!");
            } else
                System.out.println("Курса с таким ID нет!");

        }
    }

    private static void deleteAllCourses(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Course> courseList = session.createQuery("FROM Course", Course.class).getResultList();
            courseList.forEach(session::delete);
            session.getTransaction().commit();
        }
    }


}