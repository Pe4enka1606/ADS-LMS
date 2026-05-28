package by.it.group451051.pekarskij.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_EditDist {

    String getDistanceEdinting(String a, String b) {
    int n = a.length();
    int m = b.length();

    int[][] dp = new int[n + 1][m + 1];

    // базовые случаи
    for (int i = 0; i <= n; i++) dp[i][0] = i;
    for (int j = 0; j <= m; j++) dp[0][j] = j;

    // заполнение таблицы
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            int same = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

            int del = dp[i - 1][j] + 1;
            int ins = dp[i][j - 1] + 1;
            int rep = dp[i - 1][j - 1] + same;

            dp[i][j] = Math.min(del, Math.min(ins, rep));
        }
    }

    // восстановление предписания
    StringBuilder out = new StringBuilder();
    int i = n, j = m;

    while (i > 0 || j > 0) {

        // совпадение или замена
        if (i > 0 && j > 0) {
            int same = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;

            if (dp[i][j] == dp[i - 1][j - 1] + same) {
                if (same == 0) {
                    out.append("#,");
                } else {
                    out.append("~").append(b.charAt(j - 1)).append(",");
                }
                i--; j--;
                continue;
            }
        }

        // вставка
        if (j > 0 && dp[i][j] == dp[i][j - 1] + 1) {
            out.append("+").append(b.charAt(j - 1)).append(",");
            j--;
            continue;
        }

        // удаление
        if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
            out.append("-").append(a.charAt(i - 1)).append(",");
            i--;
        }
    }

    return out.toString();
}


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}