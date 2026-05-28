package by.it.group451051.shiman.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_Stairs {

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int stairs[] = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        // Создаем массив для динамического программирования
        // dp[i] - максимальная сумма, которую можно получить, дойдя до i-й ступеньки (0-индексация)
        int[] dp = new int[n];

        // Базовые случаи
        if (n == 0) {
            return 0;  // Если ступенек нет, сумма 0
        } else if (n == 1) {
            return Math.max(0, stairs[0]);  // Если ступенька одна, либо встаем на нее, либо не встаем (но нужно дойти до n-й)
        }

        // Для первой ступеньки: можем начать только с нее
        dp[0] = stairs[0];

        // Для второй ступеньки: можем прийти либо сразу на вторую, либо через первую
        dp[1] = Math.max(stairs[0] + stairs[1], stairs[1]);

        // Для остальных ступенек: можем прийти либо с предыдущей (i-1), либо через одну (i-2)
        for (int i = 2; i < n; i++) {
            // Максимум из двух вариантов:
            // 1. Пришли с предыдущей ступеньки (i-1) - тогда добавляем текущую
            // 2. Перешагнули через одну ступеньку (i-2) - тогда добавляем текущую
            dp[i] = Math.max(dp[i-1], dp[i-2]) + stairs[i];
        }

        // Результат - максимальная сумма на последней ступеньке
        int result = dp[n-1];
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        System.out.println(res);
    }
}