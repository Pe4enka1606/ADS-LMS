package by.it.group451051.kozlova.lesson02;
/*
Даны
1) объем рюкзака 4
2) число возможных предметов 60
3) сам набор предметов
    100 50
    120 30
    100 50
Все это указано в файле (by/it/a_khmelev/lesson02/greedyKnapsack.txt)

Необходимо собрать наиболее дорогой вариант рюкзака для этого объема
Предметы можно резать на кусочки (т.е. алгоритм будет жадным)
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class C_GreedyKnapsack {
    private static class Item implements Comparable<Item> {
        int cost;
        int weight;
        double costPerWeight;

        Item(int cost, int weight) {
            this.cost = cost;
            this.weight = weight;
            this.costPerWeight = (double)cost / weight;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "cost=" + cost +
                    ", weight=" + weight +
                    ", costPerWeight=" + String.format("%.2f", costPerWeight) +
                    '}';
        }

        @Override
        public int compareTo(Item o) {
            return Double.compare(o.costPerWeight, this.costPerWeight);
        }
    }

    double calc(File source) throws FileNotFoundException {
        Scanner input = new Scanner(source);
        int n = input.nextInt();      //сколько предметов в файле
        int W = input.nextInt();      //какой вес у рюкзака
        Item[] items = new Item[n];   //получим список предметов
        for (int i = 0; i < n; i++) { //создавая каждый конструктором
            int cost = input.nextInt();
            int weight = input.nextInt();
            items[i] = new Item(cost, weight);
        }
        //покажем предметы
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.printf("Всего предметов: %d. Рюкзак вмещает %d кг.\n", n, W);

        Arrays.sort(items);

        double totalCost = 0.0;
        int remainingCapacity = W;

        for (Item item : items) {
            if (remainingCapacity == 0) {
                break;
            }
            if (item.weight <= remainingCapacity) {
                totalCost += item.cost;
                remainingCapacity -= item.weight;
            } else {
                double fraction = (double)remainingCapacity / item.weight;
                totalCost += item.cost * fraction;
                remainingCapacity = 0;
            }
        }

        System.out.printf("Удалось собрать рюкзак на сумму %f\n", totalCost);
        return totalCost;
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.currentTimeMillis();
        String root=System.getProperty("user.dir") + "/src/";
        // убедитесь, что файл находится по правильному пути
        File f = new File(root + "by/it/a_khmelev/lesson02/greedyKnapsack.txt");
        double costFinal = new C_GreedyKnapsack().calc(f);
        long finishTime = System.currentTimeMillis();
        System.out.printf("Общая стоимость %f (время %d мс)\n", costFinal, finishTime - startTime);
    }
}