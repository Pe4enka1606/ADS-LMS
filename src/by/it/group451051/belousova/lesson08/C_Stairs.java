package by.it.group451051.belousova.lesson08;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_Stairs {

    int getMaxSum(InputStream stream) {
        Scanner scanner = new Scanner(stream);
        int n = scanner.nextInt();
        int[] stairs = new int[n];
        for (int i = 0; i < n; i++) {
            stairs[i] = scanner.nextInt();
        }

        // Если ступенек нет, возвращаем 0
        if (n == 0) {
            scanner.close();
            return 0;
        }

        // Если всего одна ступенька, возвращаем её значение
        if (n == 1) {
            scanner.close();
            return Math.max(0, stairs[0]); // Ступенька может быть отрицательной
        }

        // Массив для динамического программирования
        // dp[i] - максимальная сумма, которую можно получить, поднявшись на i-ю ступеньку
        int[] dp = new int[n];

        // Базовые случаи
        dp[0] = stairs[0]; // Первая ступенька

        if (n >= 2) {
            // Для второй ступеньки можно прийти либо с земли (0), либо с первой ступеньки
            dp[1] = Math.max(stairs[1], dp[0] + stairs[1]);
        }

        // Заполняем остальные ступеньки
        for (int i = 2; i < n; i++) {
            // Можно прийти либо с предыдущей ступеньки (i-1), либо с предпредыдущей (i-2)
            dp[i] = Math.max(dp[i-1], dp[i-2]) + stairs[i];
        }

        scanner.close();
        return dp[n-1]; // Последняя ступенька
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson08/dataC.txt");
        C_Stairs instance = new C_Stairs();
        int res = instance.getMaxSum(stream);
        System.out.println(res);
    }
}
