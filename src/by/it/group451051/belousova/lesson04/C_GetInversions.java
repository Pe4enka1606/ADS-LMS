package by.it.group451051.belousova.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // Размер массива
        int n = scanner.nextInt();
        // Сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        // Вызов метода для подсчета инверсий
        long inversions = countInversions(a, 0, n - 1);

        scanner.close();
        return (int) inversions;
    }

    // Рекурсивный метод для подсчета инверсий
    private long countInversions(int[] array, int left, int right) {
        long inversions = 0;

        if (left < right) {
            int mid = left + (right - left) / 2;

            // Рекурсивно считаем инверсии в левой и правой половинах
            inversions += countInversions(array, left, mid);
            inversions += countInversions(array, mid + 1, right);

            // Считаем инверсии при слиянии половин
            inversions += mergeAndCount(array, left, mid, right);
        }

        return inversions;
    }

    // Метод для слияния и подсчета инверсий
    private long mergeAndCount(int[] array, int left, int mid, int right) {
        // Размеры временных массивов
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Создаем временные массивы
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Копируем данные во временные массивы
        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        // Индексы для слияния и счетчик инверсий
        int i = 0, j = 0, k = left;
        long inversions = 0;

        // Слияние с подсчетом инверсий
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                // Все оставшиеся элементы в левой половине больше rightArray[j]
                inversions += (n1 - i);
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы
        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }

        return inversions;
    }

    // Альтернативный метод с одним временным массивом (оптимизированный)
    private long mergeAndCountOptimized(int[] array, int[] temp, int left, int mid, int right) {
        // Копируем подмассив во временный массив
        for (int i = left; i <= right; i++) {
            temp[i] = array[i];
        }

        int i = left;      // Индекс для левой половины
        int j = mid + 1;   // Индекс для правой половины
        int k = left;      // Индекс для записи в исходный массив
        long inversions = 0;

        // Слияние с подсчетом инверсий
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                array[k] = temp[i];
                i++;
            } else {
                array[k] = temp[j];
                // Элемент temp[j] меньше всех оставшихся в левой половине
                inversions += (mid - i + 1);
                j++;
            }
            k++;
        }

        // Копируем оставшиеся элементы левой половины
        while (i <= mid) {
            array[k] = temp[i];
            i++;
            k++;
        }

        // Правые элементы уже на своих местах в temp, но нам нужно скопировать только левые

        return inversions;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}