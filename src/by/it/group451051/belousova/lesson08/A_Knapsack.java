package by.it.group451051.belousova.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // вместимость рюкзака
        int n = scanner.nextInt(); // количество вариантов слитков

        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Массив для динамического программирования
        // dp[w] = максимальный вес, который можно набрать для рюкзака вместимостью w
        int[] dp = new int[W + 1];

        // Заполняем массив dp
        for (int w = 1; w <= W; w++) {
            for (int i = 0; i < n; i++) {
                // Если слиток помещается в рюкзак
                if (gold[i] <= w) {
                    // Сравниваем текущее значение с вариантом, когда берем этот слиток
                    dp[w] = Math.max(dp[w], dp[w - gold[i]] + gold[i]);
                }
            }
        }

        scanner.close();
        return dp[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}