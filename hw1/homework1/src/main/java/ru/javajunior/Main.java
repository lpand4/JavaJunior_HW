package ru.javajunior;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //Напишите программу, которая использует Stream API для обработки списка чисел.
        // Программа должна вывести на экран среднее значение всех четных чисел в списке.

        System.out.println(Arrays.asList(1,2,3,4,32,13,-6,4).stream()
                .filter(x -> x%2==0)
                .mapToInt(x -> x)
                .average().orElseThrow());
    }
}