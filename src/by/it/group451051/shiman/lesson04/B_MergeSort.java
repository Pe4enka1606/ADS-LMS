package by.it.group451051.shiman.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Реализуйте сортировку слиянием для одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо отсортировать полученный массив.

Sample Input:
5
2 3 9 2 9
Sample Output:
2 2 3 9 9
*/
public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!

        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
            System.out.println(a[i]);
        }

        // Реализация сортировки слиянием
        int[] temp = new int[n]; // Временный массив для слияния
        mergeSort(a, temp, 0, n - 1); // Вызываем рекурсивную функцию сортировки слиянием

        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
        return a;
    }

    // Рекурсивная функция сортировки слиянием
    private void mergeSort(int[] a, int[] temp, int left, int right) {
        if (left < right) { // Если подмассив содержит более одного элемента
            int mid = left + (right - left) / 2; // Находим середину подмассива

            // Рекурсивно сортируем левую и правую половины
            mergeSort(a, temp, left, mid);
            mergeSort(a, temp, mid + 1, right);

            // Сливаем отсортированные половины
            merge(a, temp, left, mid, right);
        }
    }

    // Функция слияния двух отсортированных подмассивов
    private void merge(int[] a, int[] temp, int left, int mid, int right) {
        // Копируем элементы из исходного массива во временный
        for (int i = left; i <= right; i++) {
            temp[i] = a[i];
        }

        int i = left; // Указатель для левого подмассива (от left до mid)
        int j = mid + 1; // Указатель для правого подмассива (от mid+1 до right)
        int k = left; // Указатель для результирующего массива

        // Слияние: выбираем меньший элемент из двух подмассивов
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) { // Если элемент из левого подмассива меньше или равен
                a[k] = temp[i]; // Копируем его в результирующий массив
                i++; // Сдвигаем указатель левого подмассива
            } else { // Если элемент из правого подмассива меньше
                a[k] = temp[j]; // Копируем его в результирующий массив
                j++; // Сдвигаем указатель правого подмассива
            }
            k++; // Сдвигаем указатель результирующего массива
        }

        // Копируем оставшиеся элементы из левого подмассива (если они есть)
        while (i <= mid) {
            a[k] = temp[i];
            i++;
            k++;
        }

        // Правый подмассив не нужно копировать отдельно, так как его элементы
        // уже на своих местах в исходном массиве (если копировать из временного массива)
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result = instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index : result) {
            System.out.print(index + " ");
        }
    }

}