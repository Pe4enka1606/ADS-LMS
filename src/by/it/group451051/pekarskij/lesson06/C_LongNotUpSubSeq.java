package by.it.group451051.pekarskij.lesson06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_LongNotUpSubSeq {

    int getNotUpSeqSize(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем длину
    int n = scanner.nextInt();
    int[] a = new int[n];

    for (int i = 0; i < n; i++) {
        a[i] = scanner.nextInt();
    }

    // tails[k] — минимальный последний элемент подпоследовательности длины k
    int[] tails = new int[n];
    // pos[k] — индекс элемента, который завершает подпоследовательность длины k
    int[] pos = new int[n];
    // prev[i] — предыдущий индекс в подпоследовательности
    int[] prev = new int[n];

    int length = 0;

    for (int i = 0; i < n; i++) {

        // бинарный поиск по tails для невозрастающей последовательности
        int left = 0;
        int right = length;

        while (left < right) {
            int mid = (left + right) / 2;

            // условие невозрастания: tails[mid] >= a[i]
            if (tails[mid] >= a[i]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        // left — длина новой подпоследовательности
        tails[left] = a[i];
        pos[left] = i;

        // связываем с предыдущим элементом
        prev[i] = (left > 0) ? pos[left - 1] : -1;

        if (left == length) {
            length++;
        }
    }

    // восстановление ответа
    int[] answer = new int[length];
    int cur = pos[length - 1];

    for (int i = length - 1; i >= 0; i--) {
        answer[i] = cur + 1; // индексация с 1
        cur = prev[cur];
    }

    // вывод результата
    System.out.println(length);
    for (int i = 0; i < length; i++) {
        System.out.print(answer[i] + " ");
    }

    return length;
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson06/dataC.txt");
        C_LongNotUpSubSeq instance = new C_LongNotUpSubSeq();
        int result = instance.getNotUpSeqSize(stream);
        System.out.print(result);
    }

}