package by.it.group451051.markhel.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int[] gold = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Решение для 0/1 рюкзака (без повторов)
        int[] dp = new int[W + 1];

        for (int i = 0; i < n; i++) {
            int weight = gold[i];
            // Обратный порядок для использования каждого слитка только один раз
            for (int w = W; w >= weight; w--) {
                dp[w] = Math.max(dp[w], dp[w - weight] + weight);
            }
        }

        return dp[W];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
