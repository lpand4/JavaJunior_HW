package ru.javajunior;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    /*
    Создайте абстрактный класс "Animal" с полями "name" и "age".
    Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
    Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
    Выведите на экран информацию о каждом объекте.
    Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
     */
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {


        //Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
        Animal[] animals = new Animal[]{new Cat("Пружинка", 2, true, "мяв", false),
                new Dog("Атлант", 5, "вуф-вуф", "большая"),
                new Dog("Боня", 3, "гав-гав", "маленькая"),
                new Cat("Нефертити", 1, true, "мяу", true),
                new Cat("Ворчун", 6, false, "грозный мяу", false),
                new Dog("Алмаз", 4, "гав", "средняя")};


        //Выведите на экран информацию о каждом объекте.
        for (Animal animal : animals) {
            getInfo(animal);
        }

        //Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
        for (Animal animal : animals) {
            getMakeSound(animal);
        }
    }

    private static <T> void getInfo(T object) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class<?> objectClass = object.getClass();

        Field[] fields = objectClass.getDeclaredFields();
        Field[] fieldsSuperclass = objectClass.getSuperclass().getDeclaredFields();
        sb.append("Объект класса ").append(objectClass.getSimpleName()).append(":\n");
        for (Field field : fieldsSuperclass) {
            field.setAccessible(true);
            sb.append(field.getName()).append(": ").append(field.get(object)).append("\n");
        }
        for (Field field : fields) {
            field.setAccessible(true);
            sb.append(field.getName()).append(": ").append(field.get(object)).append("\n");
        }
        System.out.println(sb);
    }

    private static <T> void getMakeSound(T object) throws InvocationTargetException, IllegalAccessException {
        Class<?> objectClass = object.getClass();

        Method[] methods = objectClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("makeSound")) {
                method.invoke(object);
            }
        }
    }
}