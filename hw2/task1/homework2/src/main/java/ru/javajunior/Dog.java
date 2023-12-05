package ru.javajunior;

public class Dog extends Animal{
    private String voiceSound;
    private String size;

    public Dog(String name, int age, String voiceSound, String size) {
        super(name, age);
        this.voiceSound = voiceSound;
        this.size = size;
    }

    public void findOutSize(){
        System.out.printf("%s является %s размера\n", name, size);
    }

    public void askingGoForAWalk(){
        System.out.printf("%s просится пойти гулять\n", name);
    }

    public void makeSound(){
        System.out.printf("%s: %s\n", name, voiceSound);
    }
}
