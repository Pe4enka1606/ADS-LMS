package by.it.group451051.pekarskij.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
    Scanner scanner = new Scanner(stream);

    int W = scanner.nextInt();
    int n = scanner.nextInt();

    int[] gold = new int[n];
    for (int i = 0; i < n; i++) {
        gold[i] = scanner.nextInt();
    }

    // dp[x] — максимальный вес, который можно набрать ровно при вместимости x
    int[] dp = new int[W + 1];

    for (int weight : gold) {
        if (weight == 0) continue; // нулевой вес можно игнорировать
        for (int x = weight; x <= W; x++) {
            dp[x] = Math.max(dp[x], dp[x - weight] + weight);
        }
    }

    return dp[W];
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }
}
