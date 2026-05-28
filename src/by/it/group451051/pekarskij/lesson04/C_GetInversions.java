package by.it.group451051.pekarskij.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class C_GetInversions {

    int calc(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем размер массива
    int n = scanner.nextInt();

    // читаем массив
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
        a[i] = scanner.nextInt();
    }

    // запускаем подсчёт инверсий
    return mergeSortCount(a, 0, n - 1);
}

// рекурсивное разбиение массива
private int mergeSortCount(int[] a, int left, int right) {
    if (left >= right) return 0;

    int mid = (left + right) / 2;

    // инверсии в левой части
    int invLeft = mergeSortCount(a, left, mid);

    // инверсии в правой части
    int invRight = mergeSortCount(a, mid + 1, right);

    // инверсии между частями
    int invMerge = merge(a, left, mid, right);

    return invLeft + invRight + invMerge;
}

// слияние двух отсортированных частей и подсчёт инверсий
private int merge(int[] a, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];

    int i = left;      // левая часть
    int j = mid + 1;   // правая часть
    int k = 0;         // временный массив
    int inv = 0;

    // слияние с подсчётом инверсий
    while (i <= mid && j <= right) {
        if (a[i] <= a[j]) {
            temp[k++] = a[i++];
        } else {
            temp[k++] = a[j++];
            inv += (mid - i + 1); // все оставшиеся в левой части больше a[j]
        }
    }

    // остатки левой части
    while (i <= mid) {
        temp[k++] = a[i++];
    }

    // остатки правой части
    while (j <= right) {
        temp[k++] = a[j++];
    }

    // копируем обратно
    for (int t = 0; t < temp.length; t++) {
        a[left + t] = temp[t];
    }

    return inv;
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson04/dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }
}
