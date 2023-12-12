package ru.javajunior;

import java.io.Serializable;

public class Student implements Serializable {
    //region Поля
    private String name;
    private int age;
    private transient double GPA;
    //endregion


    //region Конструкторы
    public Student() {
    }

    public Student(String name, int age, double GPA) {
        this.name = name;
        this.age = age;
        this.GPA = GPA;
    }
    //endregion

    //region Методы

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGPA() {
        return GPA;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + GPA +
                '}';
    }
    //endregion
}
