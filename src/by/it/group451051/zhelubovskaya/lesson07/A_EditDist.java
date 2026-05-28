package by.it.group451051.zhelubovskaya.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String, Integer> memo = new HashMap<>();

    int getDistanceEdinting(String one, String two) {
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        memo.clear(); // очищаем кэш перед началом расчета
        return changeLength(one, two, one.length(), two.length());
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

    }
    private int changeLength(String one, String two, int i, int j) {
        // Проверяем, не вычисляли ли мы уже этот результат
        String key = i + "," + j;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        
        // Базовые случаи (без изменений)
        if (i == 0) return j;
        if (j == 0) return i;

        int result;
        
        // если символы совпадают то ничего не меняем
        // если разные то расстояние редактирования увеличивается
        if (one.charAt(i - 1) == two.charAt(j - 1)) {
            result = changeLength(one, two, i - 1, j - 1);
        } else {
            result = 1 + min(
                changeLength(one, two, i - 1, j), // удаление
                changeLength(one, two, i, j - 1), // вставка
                changeLength(one, two, i - 1, j - 1) // замена
            );
        }
        
        // Сохраняем результат в кэш
        memo.put(key, result);
        return result;
    }
    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }    

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/zhelubovskaya/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}
