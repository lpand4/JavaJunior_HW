package ru.javajunior;

import com.ctc.wstx.shaded.msv.org_jp_gr_xml.dom.XMLMaker;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.logging.XMLFormatter;

public class Main {
    /*
    Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
    Обеспечьте поддержку сериализации для этого класса.
    Создайте объект класса Student и инициализируйте его данными.
    Сериализуйте этот объект в файл. Десериализуйте объект обратно в программу из файла.
    Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановлено.

    * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).
     */

    static ObjectMapper objectMapper = new ObjectMapper();
    static XmlMapper xmlMapper = new XmlMapper();
    static String TITLE_BIN = "students_data.bin";
    static String TITLE_JSON = "students_data.json";
    static String TITLE_XML = "students_data.xml";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //region Задание_1 .BIN
        // Создайте объект класса Student и инициализируйте его данными.
        Student student = new Student("Владислав", 23, 5);

        // Сериализуйте этот объект в файл.
        doSeria(student, TITLE_BIN);

        // Десериализуйте объект обратно в программу из файла.
        Student deserialStudent = (Student) doDeseria(TITLE_BIN);

        //Выведите все поля объекта
        System.out.println(deserialStudent);

        //Как видим GPA_deserial_student = 0, т.к. оно не было сериализовано за счет модификатора transient в нашем классе
        System.out.printf("GPA_student = %.1f; GPA_deserial_student = %.1f \n", student.getGPA(), deserialStudent.getGPA());
        //endregion
        System.out.println();
        //region Задание_2 .JSON
        objectMapper.writeValue(new File(TITLE_JSON), student);
        System.out.println("Сериализация .JSON успешно произведена!");

        Student deserialJSONStudent = objectMapper.readValue(new File(TITLE_JSON), Student.class);
        System.out.println("Десериализация .JSON успешно произведена!");

        System.out.println(deserialJSONStudent);
        //endregion
        System.out.println();
        //region Задание_2 .XML
        xmlMapper.writeValue(new File(TITLE_XML), student);
        System.out.println("Сериализация .XM успешно произведена!");


        Student deserialXMLStudent = xmlMapper.readValue(new File(TITLE_XML), Student.class);
        System.out.println("Десериализация .XML успешно произведена!");

        System.out.println(deserialXMLStudent);
        //endregion
    }

    //region task1_methods
    static void doSeria(Object o, String title) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(title))) {
            objectOutputStream.writeObject(o);
            System.out.println("Сериализация .BIN успешно произведена!");
        }
    }

    static Object doDeseria(String title) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(title))) {
            Object o = objectInputStream.readObject();
            System.out.println("Десериализация .BIN успешно произведена!");
            return o;
        }
    }
    //endregion
}