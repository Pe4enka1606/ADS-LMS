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

        int m = one.length(); // Длина первой строки
        int n = two.length(); // Длина второй строки

        // Создаем двумерный массив для хранения расстояний между префиксами строк
        // dp[i][j] будет содержать расстояние Левенштейна между префиксами one[0..i-1] и two[0..j-1]
        int[][] dp = new int[m + 1][n + 1];

        // Инициализируем первую строку: чтобы преобразовать пустую строку в префикс two[0..j-1],
        // нужно выполнить j вставок
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Инициализируем первый столбец: чтобы преобразовать префикс one[0..i-1] в пустую строку,
        // нужно выполнить i удалений
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        // Заполняем остальные ячейки матрицы dp
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // Если символы в текущих позициях совпадают, стоимость замены равна 0
                // Берем значение из диагональной ячейки (без изменений)
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // Иначе вычисляем минимальную стоимость из трех возможных операций:
                    // 1. Удаление символа из первой строки: dp[i-1][j] + 1
                    // 2. Вставка символа во вторую строку: dp[i][j-1] + 1
                    // 3. Замена символа: dp[i-1][j-1] + 1
                    int delete = dp[i - 1][j] + 1;    // Удаление
                    int insert = dp[i][j - 1] + 1;    // Вставка
                    int replace = dp[i - 1][j - 1] + 1; // Замена

                    // Выбираем минимальную стоимость из трех операций
                    dp[i][j] = Math.min(Math.min(delete, insert), replace);
                }
            }
        }

        // Результат находится в правом нижнем углу матрицы - расстояние между полными строками
        int result = dp[m][n];

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }



    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}