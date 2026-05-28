package by.it.group451051.shiman.lesson04;

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

        // Используем модифицированную сортировку слиянием для подсчета инверсий
        int[] temp = new int[n]; // Временный массив для слияния
        result = mergeSortAndCountInversions(a, temp, 0, n - 1); // Запускаем рекурсивный подсчет инверсий

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return result;
    }

    // Рекурсивная функция для сортировки слиянием с подсчетом инверсий
    private int mergeSortAndCountInversions(int[] a, int[] temp, int left, int right) {
        int inversionCount = 0; // Счетчик инверсий для текущего подмассива

        if (left < right) { // Если подмассив содержит более одного элемента
            int mid = left + (right - left) / 2; // Находим середину

            // Рекурсивно считаем инверсии в левой и правой половинах
            inversionCount += mergeSortAndCountInversions(a, temp, left, mid);
            inversionCount += mergeSortAndCountInversions(a, temp, mid + 1, right);

            // Сливаем половины и считаем инверсии между ними
            inversionCount += mergeAndCountInversions(a, temp, left, mid, right);
        }

        return inversionCount; // Возвращаем общее количество инверсий
    }

    // Функция для слияния двух отсортированных половин с подсчетом инверсий
    private int mergeAndCountInversions(int[] a, int[] temp, int left, int mid, int right) {
        int inversionCount = 0; // Счетчик инверсий при слиянии

        // Копируем элементы в временный массив
        for (int i = left; i <= right; i++) {
            temp[i] = a[i];
        }

        int i = left; // Указатель для левого подмассива
        int j = mid + 1; // Указатель для правого подмассива
        int k = left; // Указатель для результирующего массива

        // Слияние с подсчетом инверсий
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                // Если элемент из левого подмассива меньше или равен элементу из правого,
                // инверсий нет, просто копируем элемент
                a[k] = temp[i];
                i++;
            } else {
                // Если элемент из правого подмассива меньше элемента из левого,
                // то все оставшиеся элементы в левом подмассиве (от i до mid)
                // образуют инверсии с текущим элементом правого подмассива
                a[k] = temp[j];
                inversionCount += (mid - i + 1); // Важно: добавляем количество инверсий
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы из левого подмассива (если есть)
        while (i <= mid) {
            a[k] = temp[i];
            i++;
            k++;
        }

        // Правый подмассив не нужно копировать отдельно,
        // так как его элементы уже на своих местах

        return inversionCount; // Возвращаем количество инверсий, найденных при слиянии
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}