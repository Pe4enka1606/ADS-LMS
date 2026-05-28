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
    Рекурсивно вычислить расстояние редактирования двух данных непустых строк

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    0

    Sample Input 2:
    short
    ports
    Sample Output 2:
    3

    Sample Input 3:
    distance
    editing
    Sample Output 3:
    5

*/

public class A_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        int result = 0;

        // Добавляем двумерный массив для мемоизации (динамического программирования)
        // Размер [длина_первой_строки + 1][длина_второй_строки + 1]
        int[][] dp = new int[one.length() + 1][two.length() + 1];

        // Инициализируем массив специальным значением -1, чтобы отметить невычисленные ячейки
        for (int i = 0; i <= one.length(); i++) {
            for (int j = 0; j <= two.length(); j++) {
                dp[i][j] = -1;
            }
        }

        // Вызываем рекурсивную функцию с мемоизацией
        result = recursiveEditDistance(one, two, one.length(), two.length(), dp);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    // Добавляем рекурсивную функцию с мемоизацией для вычисления расстояния Левенштейна
    private int recursiveEditDistance(String one, String two, int i, int j, int[][] dp) {
        // Если значение уже вычислено, возвращаем его из кэша
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        // Базовый случай: если первая строка пустая, нужно вставить все символы второй строки
        if (i == 0) {
            dp[i][j] = j;
            return j;
        }

        // Базовый случай: если вторая строка пустая, нужно удалить все символы первой строки
        if (j == 0) {
            dp[i][j] = i;
            return i;
        }

        // Если последние символы совпадают, переходим к следующим символам без увеличения стоимости
        if (one.charAt(i - 1) == two.charAt(j - 1)) {
            dp[i][j] = recursiveEditDistance(one, two, i - 1, j - 1, dp);
        } else {
            // Иначе вычисляем минимальную стоимость из трех возможных операций:
            // 1. Удаление символа из первой строки (i-1, j)
            // 2. Вставка символа во вторую строку (i, j-1)
            // 3. Замена символа (i-1, j-1)
            int delete = recursiveEditDistance(one, two, i - 1, j, dp) + 1;
            int insert = recursiveEditDistance(one, two, i, j - 1, dp) + 1;
            int replace = recursiveEditDistance(one, two, i - 1, j - 1, dp) + 1;

            // Выбираем минимальную стоимость
            dp[i][j] = Math.min(Math.min(delete, insert), replace);
        }

        return dp[i][j];
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