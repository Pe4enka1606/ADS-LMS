package by.it.group451051.pekarskij.lesson02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            // сортируем по убыванию стоимости за единицу веса
            double v1 = (double) cost / weight;
            double v2 = (double) o.cost / o.weight;
            return Double.compare(v2, v1);
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();
        int W = input.nextInt();
        Item[] items = new Item[n];
        for (int i = 0; i < n; i++) {
            items[i] = new Item(input.nextInt(), input.nextInt());
        }

        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        double result = 0;

        // сортируем предметы по убыванию ценности
        java.util.Arrays.sort(items);

        // жадно набираем рюкзак, разрешая дробление предметов
        int remaining = W;
        for (Item item : items) {
            if (remaining == 0) break;

            if (item.weight <= remaining) {
                // берем весь предмет
                result += item.cost;
                remaining -= item.weight;
            } else {
                // берем часть предмета
                double fraction = (double) remaining / item.weight;
                result += item.cost * fraction;
                remaining = 0;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", result);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root = System.getProperty("user.dir") + "/src/";
        File f = new File(root + "by/it/group451051/pekarskij/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d)", costFinal, finishTime - startTime);
    }
}
