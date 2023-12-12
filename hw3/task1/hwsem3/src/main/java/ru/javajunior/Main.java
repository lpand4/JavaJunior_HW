package ru.javajunior;

import java.io.*;

public class Main {
    /*
    Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
    Обеспечьте поддержку сериализации для этого класса.
    Создайте объект класса Student и инициализируйте его данными.
    Сериализуйте этот объект в файл. Десериализуйте объект обратно в программу из файла.
    Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановлено.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Создайте объект класса Student и инициализируйте его данными.
        Student student = new Student("Владислав", 23, 5);
        String title = "students_data.bin";


        // Сериализуйте этот объект в файл.
        doSeria(student, title);


        // Десериализуйте объект обратно в программу из файла.
        Student deserialStudent = (Student) doDeseria(title);

        //Выведите все поля объекта
        System.out.println(deserialStudent);

        //Как видим GPA_deserial_student = 0, т.к. оно не было сериализовано за счет модификатора transient в нашем классе
        System.out.printf("GPA_student = %.1f; GPA_deserial_student = %.1f", student.getGPA(), deserialStudent.getGPA());



    }

    static void doSeria(Object o, String title) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(title))) {
            objectOutputStream.writeObject(o);
            System.out.println("Сериализация успешно произведена!");
        }
    }
    static Object doDeseria(String title) throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(title))) {
            Object o = objectInputStream.readObject();
            System.out.println("Десериализация успешно произведена!");
            return o;
        }
    }
}