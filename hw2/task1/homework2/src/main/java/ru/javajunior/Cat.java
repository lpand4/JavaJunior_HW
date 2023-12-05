package ru.javajunior;

public class Cat extends Animal{
    private boolean isLoving;
    private String voiceSound;
    private boolean isActive;

    public Cat(String name, int age, boolean isLoving, String voiceSound, boolean isActive) {
        super(name, age);
        this.isLoving = isLoving;
        this.voiceSound = voiceSound;
        this.isActive = isActive;
    }

    public void makeSound(){
        System.out.printf("%s: %s\n", name, voiceSound);
    }

    public void findOutLoving(){
        if (isLoving){
            System.out.println("%s является любвеобильной кошкой\n");
        }else {
            System.out.println("%s является не любвеобильной кошкой\n");
        }
    }

    public void findOutActive(){
        if (isActive){
            System.out.println("%s является активной кошкой\n");
        }else {
            System.out.println("%s является не активной кошкой\n");
        }
    }
}
