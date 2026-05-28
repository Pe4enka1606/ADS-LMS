package by.it.group451051.shiman.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int w = scanner.nextInt();  // Вместимость рюкзака
        int n = scanner.nextInt();  // Количество слитков
        int gold[] = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Создаем двумерный массив для динамического программирования
        // dp[i][j] - максимальный вес, который можно набрать, используя первые i слитков для рюкзака вместимостью j
        int[][] dp = new int[n + 1][w + 1];

        // Заполняем таблицу dp
        for (int i = 1; i <= n; i++) {  // i - текущий слиток (1-based)
            for (int j = 1; j <= w; j++) {  // j - текущая вместимость рюкзака
                // Если вес текущего слитка больше текущей вместимости,
                // то мы не можем взять этот слиток
                if (gold[i - 1] > j) {
                    // Не берем текущий слиток
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // Выбираем максимум из двух вариантов:
                    // 1. Не берем текущий слиток (dp[i-1][j])
                    // 2. Берем текущий слиток (dp[i-1][j - gold[i-1]] + gold[i-1])
                    // Обратите внимание: dp[i-1][...] - потому что без повторов, нельзя использовать тот же слиток дважды
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - gold[i - 1]] + gold[i - 1]);
                }
            }
        }

        // Результат - максимальный вес для полной емкости рюкзака с использованием всех n слитков
        int result = dp[n][w];
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataB.txt");
        B_Knapsack instance = new B_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}