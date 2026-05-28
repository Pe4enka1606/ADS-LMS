package by.it.group451051.belousova.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_EditDist {

    int getDistanceEdinting(String one, String two) {
        int n = one.length();
        int m = two.length();

        // Создаем таблицу для мемоизации
        int[][] memo = new int[n + 1][m + 1];

        // Инициализируем таблицу специальным значением (-1) для обозначения непросчитанных ячеек
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                memo[i][j] = -1;
            }
        }

        // Вызываем рекурсивную функцию
        return editDistance(one, two, n, m, memo);
    }

    // Рекурсивная функция с мемоизацией
    private int editDistance(String s1, String s2, int i, int j, int[][] memo) {
        // Если результат уже вычислен, возвращаем его
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // Базовые случаи
        if (i == 0) {
            // Если первая строка пустая, нужно вставить все символы второй строки
            memo[i][j] = j;
            return j;
        }
        if (j == 0) {
            // Если вторая строка пустая, нужно удалить все символы первой строки
            memo[i][j] = i;
            return i;
        }

        // Если последние символы совпадают, рекурсивно вычисляем для оставшихся подстрок
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            memo[i][j] = editDistance(s1, s2, i - 1, j - 1, memo);
            return memo[i][j];
        }

        // Если последние символы не совпадают, рассматриваем три операции:
        // 1. Удаление символа из первой строки
        // 2. Вставка символа во вторую строку
        // 3. Замена символа
        int deleteCost = editDistance(s1, s2, i - 1, j, memo) + 1;
        int insertCost = editDistance(s1, s2, i, j - 1, memo) + 1;
        int replaceCost = editDistance(s1, s2, i - 1, j - 1, memo) + 1;

        // Выбираем минимальную стоимость
        memo[i][j] = Math.min(Math.min(deleteCost, insertCost), replaceCost);
        return memo[i][j];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}