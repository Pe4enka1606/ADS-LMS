package by.it.group451051.belousova.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt(); // вместимость рюкзака
        int n = scanner.nextInt(); // количество слитков

        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Массив для динамического программирования
        // dp[i][w] = максимальный вес, который можно набрать
        // используя первые i слитков для рюкзака вместимостью w
        int[][] dp = new int[n + 1][W + 1];

        // Заполняем таблицу dp
        for (int i = 1; i <= n; i++) {
            int currentGold = gold[i - 1];
            for (int w = 0; w <= W; w++) {
                // Если текущий слиток не помещается в рюкзак
                if (currentGold > w) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    // Выбираем максимум между:
                    // 1. Не берем текущий слиток
                    // 2. Берем текущий слиток
                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            dp[i - 1][w - currentGold] + currentGold
                    );
                }
            }
        }

        scanner.close();
        return dp[n][W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}