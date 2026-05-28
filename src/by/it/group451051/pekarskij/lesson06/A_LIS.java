package by.it.group451051.pekarskij.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_LIS {

    int getSeqSize(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем длину последовательности
    int n = scanner.nextInt();
    int[] m = new int[n];

    // читаем массив
    for (int i = 0; i < n; i++) {
        m[i] = scanner.nextInt();
    }

    // dp[i] — длина LIS, заканчивающейся в позиции i
    int[] dp = new int[n];

    int result = 1;

    // базовый случай: каждая позиция — LIS длины 1
    for (int i = 0; i < n; i++) {
        dp[i] = 1;

        // ищем предыдущие элементы, меньшие m[i]
        for (int j = 0; j < i; j++) {
            if (m[j] < m[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }

        // обновляем глобальный максимум
        if (dp[i] > result) {
            result = dp[i];
        }
    }

    return result;
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson06/dataA.txt");
        A_LIS instance = new A_LIS();
        int result = instance.getSeqSize(stream);
        System.out.print(result);
    }
}
