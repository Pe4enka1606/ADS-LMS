package by.it.group451051.zhelubovskaya.lesson06;

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
        int[] m = new int[n];
        //читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }
        //тут реализуйте логику задачи методами динамического программирования (!!!)
        int result = 0;
        int[] dp = new int[n];        // массив для длин подпоследовательностей
        int[] prev = new int[n];      // массив для индексов предыдущих элементов
        int lastElementIndex = -1;    // индекс последнего элемента в самой длинной подпоследовательности

        for (int currentIdx = 0; currentIdx < n; currentIdx++) {
            dp[currentIdx] = 1;               // минимальная подпоследовательность это сам элемент
            prev[currentIdx] = -1;            // минимальное значение потому что нет предыдущего элемента
        }

        //перебор всех элементов для нахождения максимальной длины подпоследовательности
        for (int currentIdx = 0; currentIdx < n; currentIdx++) {
            for (int prevIdx = 0; prevIdx < currentIdx; prevIdx++) {
                // если предыдущий не меньше текущего, и цепочка через предыдущий будет длиннее
                // то берем этот более длинный вариант
                if (m[prevIdx] >= m[currentIdx] && dp[prevIdx] + 1 > dp[currentIdx]) {
                    dp[currentIdx] = dp[prevIdx] + 1;
                    prev[currentIdx] = prevIdx; // обозначаем предыдущий элемент
                }
            }

            // обновляем максимальную длину
            if (dp[currentIdx] > result) {
                result = dp[currentIdx];
                lastElementIndex = currentIdx; // сохранение индекса последнего элемента
            }
        }

        // вывод длины (по условию задачи)
        System.out.println(result);

        // восстановление и вывод индексов подпоследовательности
        if (result > 0) {
            int[] indexSequence = new int[result]; // массив для хранения индексов
            int current = lastElementIndex;         // перебор начнется с конца

            for (int idx = result - 1; idx >= 0; idx--) {
                indexSequence[idx] = current + 1;   // +1 чтобы получить нужный индекс (начинается с 1)
                current = prev[current];            // после идем к предыдущему
            }

            // вывод индексов (по условию задачи)
            for (int idx = 0; idx < result; idx++) {
                System.out.print(indexSequence[idx] + " ");
            }
        }
        scanner.close();        
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/zhelubovskaya/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}