package by.it.group451051.pekarskij.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_EditDist {

    int getDistanceEdinting(String one, String two) {
    int n = one.length();
    int m = two.length();

    // таблица для мемоизации
    int[][] dp = new int[n + 1][m + 1];

    // заполняем -1 как признак "не вычислено"
    for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
            dp[i][j] = -1;
        }
    }

    // рекурсивный вызов
    return calc(one, two, n, m, dp);
}

// рекурсивное вычисление расстояния Левенштейна
private int calc(String a, String b, int i, int j, int[][] dp) {

    // если уже вычислено — возвращаем
    if (dp[i][j] != -1) {
        return dp[i][j];
    }

    // если одна строка пустая — расстояние равно длине другой
    if (i == 0) return dp[i][j] = j;
    if (j == 0) return dp[i][j] = i;

    // если последние символы совпадают — идём дальше
    if (a.charAt(i - 1) == b.charAt(j - 1)) {
        return dp[i][j] = calc(a, b, i - 1, j - 1, dp);
    }

    // три операции:
    // 1) удаление
    int del = calc(a, b, i - 1, j, dp);
    // 2) вставка
    int ins = calc(a, b, i, j - 1, dp);
    // 3) замена
    int rep = calc(a, b, i - 1, j - 1, dp);

    // выбираем минимальную + 1 операция
    return dp[i][j] = 1 + Math.min(del, Math.min(ins, rep));
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}
