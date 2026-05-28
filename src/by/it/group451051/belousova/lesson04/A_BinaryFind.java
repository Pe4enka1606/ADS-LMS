package by.it.group451051.belousova.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_BinaryFind {

    int[] findIndex(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // Размер отсортированного массива
        int n = scanner.nextInt();
        // Сам отсортированный массив
        int[] a = new int[n];
        for (int i = 1; i <= n; i++) {
            a[i - 1] = scanner.nextInt();
        }

        // Размер массива индексов
        int k = scanner.nextInt();
        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            int value = scanner.nextInt();
            // Бинарный поиск индекса
            result[i] = binarySearch(a, value);
        }

        scanner.close();
        return result;
    }

    // Метод бинарного поиска
    private int binarySearch(int[] arr, int value) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == value) {
                // Нашли элемент, возвращаем индекс + 1 (так как в задаче индексация с 1)
                return mid + 1;
            } else if (arr[mid] < value) {
                // Искомый элемент в правой половине
                left = mid + 1;
            } else {
                // Искомый элемент в левой половине
                right = mid - 1;
            }
        }

        // Элемент не найден
        return -1;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        int[] result = instance.findIndex(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}