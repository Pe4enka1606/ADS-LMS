package by.it.group451051.belousova.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
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

        // Заполнение матрицы
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    int deleteCost = dp[i - 1][j] + 1;
                    int insertCost = dp[i][j - 1] + 1;
                    int replaceCost = dp[i - 1][j - 1] + 1;
                    dp[i][j] = Math.min(Math.min(deleteCost, insertCost), replaceCost);
                }
            }
        }

        // Восстановление редакционного предписания
        ArrayList<String> operations = new ArrayList<>();
        int i = n, j = m;

        while (i > 0 || j > 0) {
            // Проверяем возможность копирования (символы совпадают и стоимость не изменилась)
            if (i > 0 && j > 0 && one.charAt(i - 1) == two.charAt(j - 1) && dp[i][j] == dp[i - 1][j - 1]) {
                operations.add("#");
                i--;
                j--;
            }
            // Проверяем возможность замены
            else if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + 1) {
                operations.add("~" + two.charAt(j - 1));
                i--;
                j--;
            }
            // Проверяем возможность удаления
            else if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
                operations.add("-" + one.charAt(i - 1));
                i--;
            }
            // Проверяем возможность вставки
            else if (j > 0 && dp[i][j] == dp[i][j - 1] + 1) {
                operations.add("+" + two.charAt(j - 1));
                j--;
            }
            // Если ничего не подошло, но мы еще не в начале
            else if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1]) {
                // Это копирование, но символы не равны - не должно происходить в корректной матрице
                operations.add("#");
                i--;
                j--;
            }
            else {
                // Запасной вариант: двигаемся по диагонали
                if (i > 0 && j > 0) {
                    operations.add("~" + two.charAt(j - 1));
                    i--;
                    j--;
                } else if (i > 0) {
                    operations.add("-" + one.charAt(i - 1));
                    i--;
                } else if (j > 0) {
                    operations.add("+" + two.charAt(j - 1));
                    j--;
                }
            }
        }

        // Разворачиваем список операций (так как мы восстанавливали с конца)
        Collections.reverse(operations);

        // Формируем строку результата
        StringBuilder result = new StringBuilder();
        for (String op : operations) {
            result.append(op).append(",");
        }

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