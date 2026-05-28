package by.it.group451051.markhel.lesson04;

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

        // Подсчет инверсий через модифицированную сортировку слиянием
        int[] temp = new int[n];
        return mergeSortAndCount(a, temp, 0, n - 1);
    }

    private int mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        int count = 0;
        if (left < right) {
            int mid = (left + right) / 2;

            // Рекурсивно считаем инверсии в левой и правой половинах
            count += mergeSortAndCount(arr, temp, left, mid);
            count += mergeSortAndCount(arr, temp, mid + 1, right);

            // Считаем инверсии при слиянии
            count += mergeAndCount(arr, temp, left, mid, right);
        }
        return count;
    }

    private int mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        // Копируем подмассив во временный массив
        for (int i = left; i <= right; i++) {
            temp[i] = arr[i];
        }

        int i = left;
        int j = mid + 1;
        int k = left;
        int count = 0;

        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k] = temp[i];
                i++;
            } else {
                arr[k] = temp[j];
                j++;
                // Все оставшиеся элементы в левой половине (i...mid) образуют инверсии
                // с текущим элементом правой половины
                count += (mid - i + 1);
            }
            k++;
        }

        // Копируем оставшиеся элементы
        while (i <= mid) {
            arr[k] = temp[i];
            i++;
            k++;
        }

        while (j <= right) {
            arr[k] = temp[j];
            j++;
            k++;
        }

        return count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        int result = instance.calc(stream);
        System.out.print(result);
    }
}
