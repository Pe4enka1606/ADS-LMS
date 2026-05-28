package by.it.group451051.pekarskij.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_Stairs {

    int getMaxSum(InputStream stream ) {
    Scanner scanner = new Scanner(stream);
    int n = scanner.nextInt();
    int[] stairs = new int[n];
    for (int i = 0; i < n; i++) {
        stairs[i] = scanner.nextInt();
    }

    // dp[i] — максимальная сумма, если стоим на ступеньке i (0..n-1)
    if (n == 0) return 0;
    if (n == 1) return stairs[0];

    int[] dp = new int[n];

    // стартовые значения
    dp[0] = stairs[0];
    dp[1] = Math.max(stairs[0] + stairs[1], stairs[1]);

    // переход: можно прийти с i-1 или i-2
    for (int i = 2; i < n; i++) {
        dp[i] = Math.max(dp[i - 1], dp[i - 2]) + stairs[i];
    }

    // ответ — на последней ступеньке
    return dp[n - 1];
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res=instance.getMaxSum(stream);
        System.out.println(res);
    }

}
