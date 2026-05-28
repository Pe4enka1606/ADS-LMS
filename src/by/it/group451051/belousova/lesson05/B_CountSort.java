package by.it.group451051.belousova.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_CountSort {

    int[] countSort(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // размер массива
        int n = scanner.nextInt();
        int[] points = new int[n];

        // читаем числа
        for (int i = 0; i < n; i++) {
            points[i] = scanner.nextInt();
        }

        // По условию: натуральные числа, не превышающие 10
        // Натуральные числа начинаются с 1, но для надежности учитываем 0

        // Создаем массив для подсчета частот (0-10)
        int[] count = new int[11]; // индексы 0-10

        // Подсчитываем частоты каждого числа
        for (int i = 0; i < n; i++) {
            int num = points[i];
            // Проверяем, что число в допустимом диапазоне
            if (num < 0 || num > 10) {
                throw new IllegalArgumentException("Число " + num + " выходит за допустимый диапазон 0-10");
            }
            count[num]++;
        }

        // Восстанавливаем отсортированный массив
        int index = 0;
        for (int num = 0; num <= 10; num++) {
            // Добавляем число num столько раз, сколько оно встречалось
            for (int j = 0; j < count[num]; j++) {
                points[index] = num;
                index++;
            }
        }

        scanner.close();
        return points;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result = instance.countSort(stream);
        for (int index : result) {
            System.out.print(index + " ");
        }
    }
}