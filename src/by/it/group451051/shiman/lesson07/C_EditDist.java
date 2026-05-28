package by.it.group451051.shiman.lesson07;

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

        int m = one.length(); // Длина первой строки
        int n = two.length(); // Длина второй строки

        // Создаем матрицу для динамического программирования
        // dp[i][j] будет содержать расстояние Левенштейна между префиксами one[0..i-1] и two[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        // Инициализация первой строки и первого столбца
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j; // Чтобы получить пустую строку из префикса two длиной j, нужно j удалений
        }
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i; // Чтобы получить префикс one длиной i из пустой строки, нужно i вставок
        }

        // Заполняем матрицу dp
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Проверяем, совпадают ли текущие символы
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    // Если символы совпадают, берем значение из диагонали (без изменений)
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Иначе выбираем минимальное значение из трех возможных операций
                    int delete = dp[i - 1][j] + 1;    // Удаление символа из первой строки
                    int insert = dp[i][j - 1] + 1;    // Вставка символа во вторую строку
                    int replace = dp[i - 1][j - 1] + 1; // Замена символа

                    dp[i][j] = Math.min(Math.min(delete, insert), replace);
                }
            }
        }

        // Теперь восстанавливаем редакционное предписание, двигаясь от конца к началу
        StringBuilder operations = new StringBuilder();
        int i = m, j = n;

        // Пока не дойдем до начала одной из строк
        while (i > 0 || j > 0) {
            // Проверяем, можно ли выполнить копирование (символы совпадают)
            if (i > 0 && j > 0 && one.charAt(i - 1) == two.charAt(j - 1)) {
                // Копирование (символы совпадают)
                operations.append("#,");
                i--;
                j--;
            }
            // Проверяем, можно ли выполнить замену
            else if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + 1) {
                // Замена символа
                operations.append("~").append(two.charAt(j - 1)).append(",");
                i--;
                j--;
            }
            // Проверяем, можно ли выполнить удаление
            else if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
                // Удаление символа из первой строки
                operations.append("-").append(one.charAt(i - 1)).append(",");
                i--;
            }
            // Проверяем, можно ли выполнить вставку
            else if (j > 0 && dp[i][j] == dp[i][j - 1] + 1) {
                // Вставка символа во вторую строку
                operations.append("+").append(two.charAt(j - 1)).append(",");
                j--;
            }
        }

        // Разворачиваем строку операций, так как мы шли от конца к началу
        String reversed = operations.reverse().toString();

        // Убираем последнюю запятую и возвращаем результат
        String result = "";
        // Сначала разбиваем строку по запятым, затем переворачиваем порядок операций
        String[] ops = reversed.split(",");
        for (int k = ops.length - 1; k >= 0; k--) {
            if (!ops[k].isEmpty()) {
                result += ops[k] + ",";
            }
        }

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
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