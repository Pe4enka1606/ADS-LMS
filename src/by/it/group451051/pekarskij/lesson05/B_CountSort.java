package by.it.group451051.pekarskij.lesson05;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_CountSort {

    int[] countSort(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем размер массива
    int n = scanner.nextInt();
    int[] points = new int[n];

    // читаем числа
    for (int i = 0; i < n; i++) {
        points[i] = scanner.nextInt();
    }

    // массив частот (значения от 0 до 10)
    int[] count = new int[11];

    // считаем количество каждого числа
    for (int x : points) {
        count[x]++;
    }

    // восстанавливаем отсортированный массив
    int idx = 0;
    for (int value = 0; value <= 10; value++) {
        for (int c = 0; c < count[value]; c++) {
            points[idx++] = value;
        }
    }

    return points;
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson05/dataB.txt");
        B_CountSort instance = new B_CountSort();
        int[] result=instance.countSort(stream);
        for (int index:result){
            System.out.print(index+" ");
        }
    }

}
