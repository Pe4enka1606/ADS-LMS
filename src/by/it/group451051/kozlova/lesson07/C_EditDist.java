package by.it.group451051.kozlova.lesson07;

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
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int lenOne = one.length();
        int lenTwo = two.length();
        int[][] dp = new int[lenOne + 1][lenTwo + 1];
        String[][] operation = new String[lenOne + 1][lenTwo + 1];

        for (int i = 0; i <= lenOne; i++) {
            dp[i][0] = i;
            operation[i][0] = "-";
        }
        for (int j = 0; j <= lenTwo; j++) {
            dp[0][j] = j;
            operation[0][j] = "+";
        }
        operation[0][0] = "";

        for (int i = 1; i <= lenOne; i++) {
            for (int j = 1; j <= lenTwo; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                    operation[i][j] = "#";
                } else {
                    int insertCost = dp[i][j - 1] + 1;
                    int deleteCost = dp[i - 1][j] + 1;
                    int replaceCost = dp[i - 1][j - 1] + 1;

                    int minCost = Math.min(insertCost, Math.min(deleteCost, replaceCost));
                    dp[i][j] = minCost;

                    if (minCost == insertCost) {
                        operation[i][j] = "+";
                    } else if (minCost == deleteCost) {
                        operation[i][j] = "-";
                    } else {
                        operation[i][j] = "~";
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder();
        int i = lenOne;
        int j = lenTwo;

        while (i > 0 || j > 0) {
            String op = operation[i][j];

            if (op.equals("#")) {
                result.insert(0, "#,");
                i--;
                j--;
            } else if (op.equals("~")) {
                result.insert(0, "~" + two.charAt(j - 1) + ",");
                i--;
                j--;
            } else if (op.equals("+")) {
                result.insert(0, "+" + two.charAt(j - 1) + ",");
                j--;
            } else if (op.equals("-")) {
                result.insert(0, "-" + one.charAt(i - 1) + ",");
                i--;
            }
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(), scanner.nextLine()));
    }
}