package ru.javajunior.models;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "Courses")
public class Course {

    private static final String[] titles = new String[] { "Математика", "Алгебра", "Геометрия", "Вероятность и статистика",
            "Информатика", "Литература", "География", "Биология", "Физика", "Химия",
            "ОБЖ", "История", "Обществознание", "Экономика", "Право", "Русский язык"};
    private static final Random rnd = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "duration")
    private int duration;


    public Course(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Course() {
    }

    public static Course create(){
        return new Course(titles[rnd.nextInt(titles.length)], rnd.nextInt(5, 12));
    }
    public void randomUpdate(){
        String updatedTitle;
        int updatedDuration;
        do {
            updatedTitle = titles[rnd.nextInt(titles.length)];
        }while (updatedTitle.equals(getTitle()));
        setTitle(updatedTitle);
        do {
            updatedDuration = rnd.nextInt(5, 12);
        }while (updatedDuration == getDuration());
        setDuration(rnd.nextInt(5, 12));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
