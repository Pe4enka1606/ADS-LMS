package by.it.group451052.nasonova.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        int n = one.length();
        int m = two.length();
        int[][] dp = new int[n + 1][m + 1];
        char[][] parent = new char[n + 1][m + 1];

        dp[0][0] = 0;
        parent[0][0] = 'S';

        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
            parent[i][0] = 'D';
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
            parent[0][j] = 'I';
        }

        for (int i = 1; i <= n; i++) {
            char c1 = one.charAt(i - 1);
            for (int j = 1; j <= m; j++) {
                char c2 = two.charAt(j - 1);

                int cost = (c1 == c2) ? 0 : 1;

                int del = dp[i - 1][j] + 1;
                int ins = dp[i][j - 1] + 1;
                int rep = dp[i - 1][j - 1] + cost;

                int best = rep;
                char op = (cost == 0) ? 'M' : 'R';

                if (del < best) {
                    best = del;
                    op = 'D';
                }
                if (ins < best) {
                    best = ins;
                    op = 'I';
                }

                dp[i][j] = best;
                parent[i][j] = op;
            }
        }

        StringBuilder sb = new StringBuilder();
        int i = n, j = m;

        while (i > 0 || j > 0) {
            char op = parent[i][j];

            if (op == 'M') {
                sb.append("#,");
                i--;
                j--;
            } else if (op == 'R') {
                sb.append("~").append(two.charAt(j - 1)).append(",");
                i--;
                j--;
            } else if (op == 'D') {
                sb.append("-").append(one.charAt(i - 1)).append(",");
                i--;
            } else {
                sb.append("+").append(two.charAt(j - 1)).append(",");
                j--;
            }
        }
        String[] tokens = sb.toString().split(",");
        StringBuilder result = new StringBuilder();
        for (int t = tokens.length - 1; t >= 0; t--) {
            if (!tokens[t].isEmpty()) {
                result.append(tokens[t]).append(",");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}