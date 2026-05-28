package by.it.group451051.belousova.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
        Scanner scanner = new Scanner(stream);

        // общая длина последовательности
        int n = scanner.nextInt();
        int[] m = new int[n];

        // читаем всю последовательность
        for (int i = 0; i < n; i++) {
            m[i] = scanner.nextInt();
        }

        // Алгоритм за O(n log n) с использованием двоичного поиска
        int[] tail = new int[n]; // tail[i] - наибольший возможный последний элемент ННП длины i+1
        int[] prev = new int[n]; // массив для восстановления индексов
        int[] pos = new int[n];  // позиции в массиве tail

        int length = 0; // текущая длина найденной ННП

        for (int i = 0; i < n; i++) {
            // Бинарный поиск позиции для m[i] в массиве tail
            int left = 0;
            int right = length;

            while (left < right) {
                int mid = left + (right - left) / 2;
                // Для невозрастающей подпоследовательности нам нужно m[i] <= tail[mid]
                if (tail[mid] >= m[i]) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            // Обновляем tail и сохраняем позицию
            tail[left] = m[i];
            pos[left] = i;

            // Запоминаем предыдущий элемент
            prev[i] = (left > 0) ? pos[left - 1] : -1;

            // Если мы расширили ННП
            if (left == length) {
                length++;
            }
        }

        // Восстанавливаем индексы подпоследовательности
        List<Integer> indices = new ArrayList<>();
        int current = pos[length - 1];

        while (current != -1) {
            indices.add(current + 1); // +1 потому что в выводе индексы с 1
            current = prev[current];
        }

        // Разворачиваем список, так как мы восстановили его с конца
        Collections.reverse(indices);

        // Выводим длину
        System.out.println(length);

        // Выводим индексы
        for (int i = 0; i < indices.size(); i++) {
            System.out.print(indices.get(i));
            if (i < indices.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
        return length;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/a_khmelev/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
    }
}