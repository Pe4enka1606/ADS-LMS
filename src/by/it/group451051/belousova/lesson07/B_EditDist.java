package by.it.group451051.belousova.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_EditDist {

    int getDistanceEdinting(String one, String two) {
        // Итеративный метод динамического программирования
        int n = one.length();
        int m = two.length();

        // Создаем матрицу для динамического программирования
        int[][] dp = new int[n + 1][m + 1];

        // Инициализация базовых случаев
        for (int i = 0; i <= n; i++) {
            dp[i][0] = i; // Для преобразования префикса длины i в пустую строку
        }

        for (int j = 0; j <= m; j++) {
            dp[0][j] = j; // Для преобразования пустой строки в префикс длины j
        }

        // Итеративное заполнение таблицы
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // Если символы совпадают, стоимость не увеличивается
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Иначе выбираем минимальную стоимость из трех операций
                    int deleteCost = dp[i - 1][j] + 1;     // Удаление
                    int insertCost = dp[i][j - 1] + 1;     // Вставка
                    int replaceCost = dp[i - 1][j - 1] + 1; // Замена

                    dp[i][j] = Math.min(Math.min(deleteCost, insertCost), replaceCost);
                }
            }
        }

        return dp[n][m];
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}