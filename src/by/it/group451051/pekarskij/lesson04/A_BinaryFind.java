package by.it.group451051.pekarskij.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class A_BinaryFind {
    int[] findIndex(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем размер отсортированного массива
    int n = scanner.nextInt();

    // читаем сам массив
    int[] a = new int[n];
    for (int i = 1; i <= n; i++) {
        a[i - 1] = scanner.nextInt();
    }

    // читаем количество запросов
    int k = scanner.nextInt();
    int[] result = new int[k];

    // обрабатываем каждый запрос
    for (int i = 0; i < k; i++) {
        int value = scanner.nextInt();

        // бинарный поиск
        int left = 0;
        int right = n - 1;
        int pos = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (a[mid] == value) {
                pos = mid + 1;   // перевод в 1-based индекс
                break;
            }

            if (a[mid] < value) {
                left = mid + 1;  // ищем справа
            } else {
                right = mid - 1; // ищем слева
            }
        }

        // записываем результат поиска
        result[i] = pos;
    }

    return result;
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson04/dataA.txt");
        A_BinaryFind instance = new A_BinaryFind();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.findIndex(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
