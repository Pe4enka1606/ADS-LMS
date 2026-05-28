package by.it.group451051.yazhou.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int result = 0;
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!
        // Делаем копию массива, чтобы не изменять оригинал
        int[] arr = a.clone();
        int[] temp = new int[n];

        // Вложенный класс для рекурсивного подсчета инверсий
        class InversionCounter {
            long countInversions(int[] arr, int[] temp, int left, int right) {
                long invCount = 0;
                if (left < right) {
                    int mid = left + (right - left) / 2;

                    // Считаем инверсии в левой половине
                    invCount += countInversions(arr, temp, left, mid);

                    // Считаем инверсии в правой половине
                    invCount += countInversions(arr, temp, mid + 1, right);

                    // Считаем инверсии при слиянии
                    invCount += mergeAndCount(arr, temp, left, mid, right);
                }
                return invCount;
            }

            long mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
                int i = left;
                int j = mid + 1;
                int k = left;
                long invCount = 0;

                while (i <= mid && j <= right) {
                    if (arr[i] <= arr[j]) {
                        temp[k++] = arr[i++];
                    } else {
                        temp[k++] = arr[j++];
                        invCount += (mid - i + 1);
                    }
                }

                while (i <= mid) {
                    temp[k++] = arr[i++];
                }

                while (j <= right) {
                    temp[k++] = arr[j++];
                }

                for (i = left; i <= right; i++) {
                    arr[i] = temp[i];
                }

                return invCount;
            }
        }

        InversionCounter counter = new InversionCounter();
        result = (int) counter.countInversions(arr, temp, 0, n - 1);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/yazhou/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
