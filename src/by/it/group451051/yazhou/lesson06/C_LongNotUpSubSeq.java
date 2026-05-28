package by.it.group451051.yazhou.lesson06;

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
        scanner.close();

        // tails[k] - последний элемент подпоследовательности длины k+1
        int[] tails = new int[n];
        // posInTails[k] - индекс элемента в массиве m, который лежит в tails[k]
        int[] posInTails = new int[n];
        // prev[i] - индекс предыдущего элемента для элемента m[i] в оптимальной подпоследовательности
        int[] prev = new int[n];

        // Инициализация
        for (int i = 0; i < n; i++) {
            prev[i] = -1;
        }

        int len = 0; // текущая длина наибольшей невозрастающей подпоследовательности

        for (int i = 0; i < n; i++) {
            int x = m[i];

            // Бинарный поиск: ищем первый элемент tails, который меньше x
            // Если все элементы >= x, то вставляем x в конец
            int left = 0, right = len;
            while (left < right) {
                int mid = (left + right) / 2;
                if (tails[mid] < x) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            // left - позиция, куда вставляем x
            tails[left] = x;
            posInTails[left] = i;

            // Устанавливаем ссылку на предыдущий элемент
            if (left > 0) {
                prev[i] = posInTails[left - 1];
            }

            // Если вставили в конец, увеличиваем длину
            if (left == len) {
                len++;
            }
        }

        // Восстановление индексов подпоследовательности
        int[] resultIndices = new int[len];
        int currentIndex = posInTails[len - 1];

        for (int i = len - 1; i >= 0; i--) {
            resultIndices[i] = currentIndex;
            currentIndex = prev[currentIndex];
        }

        // Вывод результата в требуемом формате
        System.out.println(len);
        for (int i = 0; i < len; i++) {
            System.out.print((resultIndices[i] + 1) + " ");
        }
        System.out.println();

        return len;

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/yazhou/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}