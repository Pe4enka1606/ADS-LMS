package by.it.group451051.pekarskij.lesson08;

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

    // dp[x] — максимальный вес, который можно набрать при вместимости x
    int[] dp = new int[W + 1];

    // каждый слиток можно взять только один раз, идём справа налево
    for (int weight : gold) {
        if (weight == 0) continue;
        for (int x = W; x >= weight; x--) {
            dp[x] = Math.max(dp[x], dp[x - weight] + weight);
        }
    }

    return dp[W];
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res=instance.getMaxWeight(stream);
        System.out.println(res);
    }

}
