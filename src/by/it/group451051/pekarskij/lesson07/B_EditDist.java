package by.it.group451051.pekarskij.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_EditDist {

    int getDistanceEdinting(String one, String two) {
    int n = one.length();
    int m = two.length();

    // dp[i][j] — расстояние между one[0..i) и two[0..j)
    int[][] dp = new int[n + 1][m + 1];

    // базовые случаи: расстояние до пустой строки
    for (int i = 0; i <= n; i++) dp[i][0] = i;
    for (int j = 0; j <= m; j++) dp[0][j] = j;

    // заполняем таблицу
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {

            // если символы совпадают — стоимость 0
            int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;

            // выбираем минимальную операцию
            dp[i][j] = Math.min(
                    dp[i - 1][j] + 1,                 // удаление
                    Math.min(
                            dp[i][j - 1] + 1,         // вставка
                            dp[i - 1][j - 1] + cost   // замена
                    )
            );
        }
    }

    return dp[n][m];
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}