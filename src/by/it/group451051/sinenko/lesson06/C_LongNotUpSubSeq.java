package by.it.group451051.sinenko.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: наибольшая невозростающая подпоследовательность

Дано:
    целое число 1<=n<=1E5 ( ОБРАТИТЕ ВНИМАНИЕ НА РАЗМЕРНОСТЬ! )
    массив A[1…n] натуральных чисел, не превосходящих 2E9.

Необходимо:
    Выведите максимальное 1<=k<=n, для которого гарантированно найдётся
    подпоследовательность индексов i[1]<i[2]<…<i[k] <= длины k,
    для которой каждый элемент A[i[k]] не больше любого предыдущего
    т.е. для всех 1<=j<k, A[i[j]]>=A[i[j+1]].

    В первой строке выведите её длину k,
    во второй - её индексы i[1]<i[2]<…<i[k]
    соблюдая A[i[1]]>=A[i[2]]>= ... >=A[i[n]].

    (индекс начинается с 1)

Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ

    Sample Input:
    5
    5 3 4 4 2

    Sample Output:
    4
    1 3 4 5
*/


public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        //общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];          // массив для хранения исходных чисел
        int[] l = new int[n];       // массив для длин подпоследовательностей
        int[] prev = new int [n];   // массив для индексов предыдущих элементов
        int result = 0;
        int lastIndex = -1;         // индекс последнего элемента в самой длинной подпоследовательности

        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
            l[i] = 1;                   // минимальная подпоследовательность это сам элемент
            prev[i] = -1;               // минимальное значение потму что нет предыдущего элемента
        
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        
        //перебор предыдущих перед i элементов
            for (int j = 0; j < i; j++) {
                // если j не меньше i, и цепочка через j будет длиннее чем текущая лучшая для i
                // то берем этот более длинный вариант
                if (m[j] >= m[i] && l[j] + 1 > l[i]) { 
                    l[i] = l[j] + 1;        
                    prev[i] = j;    //обозначаем предыдущий элемент
                }
            }
            // обновляем максимальную длину
            if (l[i] > result) {
                result = l[i];
                lastIndex = i;          // сохраняение индекса последнего элемента
            }
        }
        // вывод длины 
        System.out.println(result);

        // нахождение и вывод индексов 
        // если есть хотя бы одна цепочка
        if (result > 0) {
            int[] indexes = new int[result]; // массив для хранения индексов
            int current = lastIndex;          // перебор начнется с конца
            
            for (int i = result - 1; i >= 0; i--) {
                indexes[i] = current + 1;  // +1 чтобы получить нужный индекс
                current = prev[current];   // после идем к предыдущему
            }

            // вывод индексов
            for (int i = 0; i < result; i++) {
                System.out.print(indexes[i] + " ");
            }
        }
        scanner.close();
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
    }

}