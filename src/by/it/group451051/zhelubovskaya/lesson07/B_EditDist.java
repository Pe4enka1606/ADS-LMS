package by.it.group451051.zhelubovskaya.lesson07;

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
        int[] prevStr = new int[two.length() + 1];   // предыдущая строка
        int[] currentStr = new int[two.length() + 1]; // текущая строка

        // на случай если строка one пустая
        for (int j = 0; j <= two.length(); j++) {
            currentStr[j] = j; // чтобы получить строку two нужно j операций
        }

        for (int i = 1; i <= one.length(); i++) {
            // копируем текущую строку в предыдущую
            System.arraycopy(currentStr, 0, prevStr, 0, prevStr.length);
            
            // на случай если строка two пустая
            currentStr[0] = i; // чтобы получить пустую строку из one нужно i удалений

            // вычисление расстояния для one и two
            for (int j = 1; j <= two.length(); j++) {
                // стоимость замены символа, 0 если равны и 1 если разные
                int cost = (one.charAt(i - 1) != two.charAt(j - 1)) ? 1 : 0;

                // отбор меньшего по стоимости способа
                currentStr[j] = min(
                    prevStr[j] + 1,         // удалить символ из строки one
                    currentStr[j - 1] + 1,  // или добавить
                    prevStr[j - 1] + cost   // или заменить или оставить как есть
                );
            }
        }

        int result = currentStr[currentStr.length - 1]; // итоговый результат
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }
    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/zhelubovskaya/lesson07/dataABC.txt");
        B_EditDist instance = new B_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}