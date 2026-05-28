package by.it.group451051.markhel.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_EditDist {
    private String one;
    private String two;
    private int[][] memo;

    private int editDist(int i, int j) {
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        if (i == 0) {
            memo[i][j] = j;
            return j;
        }
        if (j == 0) {
            memo[i][j] = i;
            return i;
        }

        int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;

        int insert = editDist(i, j - 1) + 1;
        int delete = editDist(i - 1, j) + 1;
        int replace = editDist(i - 1, j - 1) + cost;

        memo[i][j] = Math.min(Math.min(insert, delete), replace);
        return memo[i][j];
    }

    int getDistanceEdinting(String one, String two) {
        this.one = one;
        this.two = two;
        int m = one.length();
        int n = two.length();
        memo = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                memo[i][j] = -1;
            }
        }

        return editDist(m, n);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}
