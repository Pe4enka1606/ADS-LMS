package by.it.group451051.shiman.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_Knapsack {

    int getMaxWeight(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int w = scanner.nextInt();  // Вместимость рюкзака
        int n = scanner.nextInt();  // Количество видов слитков
        int gold[] = new int[n];
        for (int i = 0; i < n; i++) {
            gold[i] = scanner.nextInt();
        }

        // Создаем массив для динамического программирования
        // dp[i] будет хранить максимальный вес, который можно набрать для емкости i
        int[] dp = new int[w + 1];

        // Заполняем dp для каждой емкости от 1 до W
        for (int i = 1; i <= w; i++) {
            // Перебираем все виды слитков
            for (int j = 0; j < n; j++) {
                // Если вес слитка меньше или равен текущей емкости
                if (gold[j] <= i) {
                    // Пробуем добавить этот слиток к оптимальному заполнению оставшейся емкости
                    dp[i] = Math.max(dp[i], dp[i - gold[j]] + gold[j]);
                }
            }
        }

        // Результат - максимальный вес для полной емкости рюкзака
        int result = dp[w];
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataA.txt");
        A_Knapsack instance = new A_Knapsack();
        int res = instance.getMaxWeight(stream);
        System.out.println(res);
    }
}