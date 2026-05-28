package by.it.group451051.pekarskij.lesson04;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class B_MergeSort {

    int[] getMergeSort(InputStream stream) throws FileNotFoundException {
    Scanner scanner = new Scanner(stream);

    // читаем размер массива
    int n = scanner.nextInt();

    // читаем массив
    int[] a = new int[n];
    for (int i = 0; i < n; i++) {
        a[i] = scanner.nextInt();
    }

    // запускаем сортировку слиянием
    mergeSort(a, 0, n - 1);

    return a;
}

// рекурсивная сортировка
private void mergeSort(int[] a, int left, int right) {
    if (left >= right) return;

    int mid = (left + right) / 2;

    // сортируем левую половину
    mergeSort(a, left, mid);

    // сортируем правую половину
    mergeSort(a, mid + 1, right);

    // сливаем две отсортированные части
    merge(a, left, mid, right);
}

// слияние двух отсортированных подмассивов
private void merge(int[] a, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];

    int i = left;      // указатель левой части
    int j = mid + 1;   // указатель правой части
    int k = 0;         // указатель временного массива

    // слияние двух отсортированных частей
    while (i <= mid && j <= right) {
        if (a[i] <= a[j]) {
            temp[k++] = a[i++];
        } else {
            temp[k++] = a[j++];
        }
    }

    // добавляем оставшиеся элементы левой части
    while (i <= mid) {
        temp[k++] = a[i++];
    }

    // добавляем оставшиеся элементы правой части
    while (j <= right) {
        temp[k++] = a[j++];
    }

    // копируем результат обратно в исходный массив
    for (int t = 0; t < temp.length; t++) {
        a[left + t] = temp[t];
    }
}

    public static void main(String[] args) throws FileNotFoundException {
        String root = System.getProperty("user.dir") + "/src/";
        InputStream stream = new FileInputStream(root + "by/it/group451051/pekarskij/lesson04/dataB.txt");
        B_MergeSort instance = new B_MergeSort();
        //long startTime = System.currentTimeMillis();
        int[] result=instance.getMergeSort(stream);
        //long finishTime = System.currentTimeMillis();
        for (int index:result){
            System.out.print(index+" ");
        }
    }


}
