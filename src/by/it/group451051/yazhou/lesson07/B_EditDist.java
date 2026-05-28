package by.it.group451051.yazhou.lesson07;

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
    Итерационно вычислить расстояние редактирования двух данных непустых строк

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

public class B_EditDist {


    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        int m = one.length();
        int n = two.length();

        // Создаем таблицу для хранения результатов подзадач
        int[][] dp = new int[m + 1][n + 1];

        // Базовые случаи:
        // Если вторая строка пустая, нужно удалить все символы из первой
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Если первая строка пустая, нужно вставить все символы второй строки
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Заполняем таблицу снизу вверх
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Если символы равны, стоимость = 0, иначе = 1
                int cost = (one.charAt(i - 1) == two.charAt(j - 1)) ? 0 : 1;

                // Находим минимальную стоимость из трех возможных операций:
                // 1. Удаление (из первой строки)
                // 2. Вставка (во вторую строку)
                // 3. Замена (если символы разные)
                dp[i][j] = Math.min(
                        Math.min(
                                dp[i - 1][j] + 1,      // удаление
                                dp[i][j - 1] + 1       // вставка
                        ),
                        dp[i - 1][j - 1] + cost    // замена
                );
            }
        }

        int result = dp[m][n];
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }



    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/yazhou/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}