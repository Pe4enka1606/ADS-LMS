package by.it.group451051.zhelubovskaya.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
        //Вычисление таблицы расстояний преобразования
        int n = one.length(); // длина первой строки
        int m = two.length(); // длина второй

        // матрица
        int[][] matr = new int[n + 1][m + 1];

        // заполнение первой строки матрицы
        for (int j = 0; j <= m; j++) {
            matr[0][j] = j;
        }

        // заполнение первого столбца
        for (int i = 0; i <= n; i++) {
            matr[i][0] = i;
        }

        // заполнение остальной матрицы
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // проверка совпадают ли символы
                if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    matr[i][j] = matr[i - 1][j - 1];
                } else {
                    matr[i][j] = 1 + Math.min(Math.min(
                        matr[i - 1][j], // удаление
                        matr[i][j - 1]), // вставка
                        matr[i - 1][j - 1]); // замена
                }
            }
        }

        int i = n; // сколько символов из one уже обработали
        int j = m; // сколько из two
         // список для команд
        List<String> operations = new ArrayList<>();

        // пока не обработаны все символы строк
        while (i > 0 || j > 0) {
            // если ещё есть символы в обеих строках И
            // текущие символы одинаковые И
            // расстояние не увеличилось при переходе к этим символам
            if (i > 0 && j > 0 &&
                one.charAt(i-1) == two.charAt(j-1) &&
                matr[i][j] == matr[i-1][j-1]) {
                // копируем
                operations.add("#");
                i--;
                j--;
            } else if (i > 0 && j > 0 &&
                      matr[i][j] == matr[i-1][j-1] + 1) {
                // иначе или замена
                operations.add("~" + two.charAt(j-1));
                i--;
                j--;
            } else if (i > 0 && matr[i][j] == matr[i-1][j] + 1) {
                // или удаление
                operations.add("-" + one.charAt(i-1));
                i--;
            } else if (j > 0 && matr[i][j] == matr[i][j-1] + 1) {
                // или вставка
                operations.add("+" + two.charAt(j-1));
                j--;
            }
        }   
            
        StringBuilder resultBuilder = new StringBuilder();
        for (int k = operations.size() - 1; k >= 0; k--) {
            resultBuilder.append(operations.get(k));
            if (k > 0) {
                resultBuilder.append(",");
            }
        }
        
        // Добавляем запятую в конце для соответствия формату вывода
        if (resultBuilder.length() > 0) {
            resultBuilder.append(",");
        }
        
        String result = resultBuilder.toString();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/zhelubovskaya/lesson07/dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}