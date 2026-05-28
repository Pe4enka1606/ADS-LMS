package by.it.group451051.yazhou.lesson07;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.lang.*;

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

        Integer[][] mem = new Integer[one.length()+1][two.length()+1];

        return calc(one, two, one.length(),two.length(),mem);

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

    }

    int calc (String s1, String s2, int i, int j, Integer[][] mem) {

        if (mem[i][j] != null) {
            return mem[i][j];
        }

        if (i == 0){
            mem[i][j] = j;
            return j;
        }

        if (j == 0){
            mem[i][j] = i;
            return i;
        }

        if (s1.charAt(i-1) == s2.charAt(j-1)){
            mem[i][j] = calc(s1, s2, i-1, j-1, mem);
        } else {
            int insert = calc(s1, s2, i, j -1 , mem) +1;
            int delete = calc(s1, s2, i - 1, j, mem) +1;
            int replace = calc(s1, s2, i - 1, j - 1, mem) +1;
            mem[i][j] = Math.min(Math.min(insert, delete), replace);
        }
        return mem[i][j];
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/yazhou/lesson07/dataABC.txt");
        A_EditDist instance = new A_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }
}
