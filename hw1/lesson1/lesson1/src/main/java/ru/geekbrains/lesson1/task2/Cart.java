package ru.geekbrains.lesson1.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Корзина
 *
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    public Cart(Class<T> clazz, UMarket market) {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs() {
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> {
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                    index.getAndIncrement(), food.getName(),
                    food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет",
                    food.getCarbohydrates() ? "Да" : "Нет");
        });
    }


    //Переработать метод балансировки корзины товаров cardBalancing() с использованием Stream API
    //

    /**
     * Балансировка корзины
     */
    public void cardBalancing() {
        AtomicBoolean proteins = new AtomicBoolean(false);
        AtomicBoolean fats = new AtomicBoolean(false);
        AtomicBoolean carbohydrates = new AtomicBoolean(false);

        foodstuffs.stream().forEach(food -> {
            if (!proteins.get() && food.getProteins())
                proteins.set(true);
            else if (!fats.get() && food.getFats())
                fats.set(true);
            else if (!carbohydrates.get() && food.getCarbohydrates())
                carbohydrates.set(true);
        });

        if (proteins.get() && fats.get() && carbohydrates.get()) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        market.getThings(Food.class).stream().forEach(thing -> {
            if (!proteins.get() && thing.getProteins()) {
                proteins.set(true);
                foodstuffs.add((T) thing);
            } else if (!fats.get() && thing.getFats()) {
                fats.set(true);
                foodstuffs.add((T) thing);
            } else if (!carbohydrates.get() && thing.getCarbohydrates()) {
                carbohydrates.set(true);
                foodstuffs.add((T) thing);
            }
        });

        if (proteins.get() && fats.get() && carbohydrates.get())
            System.out.println("Корзина сбалансирована по БЖУ.");
        else
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");
    }


}
