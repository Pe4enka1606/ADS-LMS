package by.it.group451051.mardas.lesson07;

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



        int result = 0;
        int first_length=one.length();
        int second_length=two.length();
        int[][] mas_cost = new int[first_length+1][second_length+1];
        for (int i=0;i<=first_length;i++) mas_cost[i][0]=i;
        for (int i=0;i<=second_length;i++) mas_cost[0][i]=i;
        for (int i=1;i<=first_length;i++)
            for (int i2=1;i2<=second_length;i2++) {
                if (one.charAt(i-1)==two.charAt(i2-1)) mas_cost[i][i2]=mas_cost[i-1][i2-1];
                else {
                    int new_sym=mas_cost[i][i2-1]+1;
                    int del_sym=mas_cost[i-1][i2]+1;
                    int zam_sym=mas_cost[i-1][i2-1]+1;
                    mas_cost[i][i2]=Math.min(new_sym,Math.min(del_sym,zam_sym));
                }
            }
        result=mas_cost[first_length][second_length];
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