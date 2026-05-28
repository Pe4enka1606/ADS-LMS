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


        String result = "";
        int first_length=one.length();
        int second_length=two.length();
        int[][] mas_cost=new int[first_length+1][second_length+1];
        String[][] mas_edit=new String[first_length+1][second_length+1];
        for (int i=0;i<=first_length;i++) {
            mas_cost[i][0]=i;
            mas_edit[i][0]="-";
        }
        for (int i=0;i<=second_length;i++) {
            mas_cost[0][i]=i;
            mas_edit[0][i]="+";
        }
        mas_edit[0][0]="";
        for (int i=1;i<=first_length;i++)
            for (int i2=1;i2<=second_length;i2++) {
                if (one.charAt(i - 1) == two.charAt(i2 - 1)) {
                    mas_cost[i][i2] = mas_cost[i - 1][i2 - 1];
                    mas_edit[i][i2] = "#";
                } else {
                    int new_sym = mas_cost[i][i2 - 1] + 1;
                    int del_sym = mas_cost[i - 1][i2] + 1;
                    int zam_sym = mas_cost[i - 1][i2 - 1] + 1;
                    mas_cost[i][i2] = Math.min(new_sym, Math.min(del_sym, zam_sym));
                    if (mas_cost[i][i2] == new_sym) mas_edit[i][i2] = "+";
                    else if (mas_cost[i][i2] == del_sym) mas_edit[i][i2] = "-";
                    else mas_edit[i][i2] = "~";
                }
            }
        while (first_length>0||second_length>0) {
            switch (mas_edit[first_length][second_length]) {
                case "#":
                    result="#,"+result;
                    first_length--;
                    second_length--;
                    break;
                case "~":
                    result="~"+two.charAt(second_length-1)+","+result;
                    first_length--;
                    second_length--;
                    break;
                case "+":
                    result="+"+two.charAt(second_length-1)+","+result;
                    second_length--;
                    break;
                case "-":
                    result="-"+one.charAt(first_length-1)+","+result;
                    first_length--;
                    break;
            }
        }
        System.out.println(result);


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