package by.it.group451051.belousova.lesson02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;
        double ratio; // удельная стоимость (стоимость на единицу веса)

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
            this.ratio = (double) cost / weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    ", ratio=" + ratio +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            // Сравниваем по убыванию удельной стоимости
            // Для сортировки по убыванию сравниваем второй объект с первым
            return Double.compare(o.ratio, this.ratio);
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      // сколько предметов в файле
        int W = input.nextInt();      // какой вес у рюкзака
        Item[] items = new Item[n];   // получим список предметов
        for (int i = 0; i < n; i++) { // создавая каждый конструктором
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        // Покажем предметы
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        // 1. Сортируем предметы по убыванию удельной стоимости
        Arrays.sort(items);

        // 2. Жадный алгоритм для непрерывного рюкзака
        double result = 0;
        int remainingWeight = W;

        for (int i = 0; i < n && remainingWeight > 0; i++) {
            Item currentItem = items[i];

            if (currentItem.weight <= remainingWeight) {
                // Берем весь предмет
                result += currentItem.cost;
                remainingWeight -= currentItem.weight;
                System.out.printf("Берем весь предмет: стоимость=%d, вес=%d, осталось места: %d\n",
                        currentItem.cost, currentItem.weight, remainingWeight);
            } else {
                // Берем часть предмета
                double fraction = (double) remainingWeight / currentItem.weight;
                result += currentItem.cost * fraction;
                System.out.printf("Берем часть предмета: стоимость=%d, вес=%d, часть=%.2f, добавляем стоимость=%.2f\n",
                        currentItem.cost, currentItem.weight, fraction, currentItem.cost * fraction);
                remainingWeight = 0;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %.2f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}